package services;

import config.ConfigLoader;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import models.Token;
import models.User;
import repositories.TokenRepository;
import repositories.UserRepository;

import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Date;
import java.util.List;

public class AuthService {
    private static final String SECRET_KEY = ConfigLoader.getSecretKey();
    private static final Key signingKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    private static final TokenRepository tokenRepository = new TokenRepository();
    private static final UserRepository userRepository = new UserRepository();

    // Access Token
    public static String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) // 15 minutes
                .signWith(signingKey)
                .compact();
    }

    // Refresh Token
    public static String generateRefreshToken(User user) {
        long refreshTokenExpirationTime = 30L * 24L * 60L * 60L * 1000L; // 30 days
        String refreshToken = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationTime))
                .signWith(signingKey)
                .compact();

        // Save refresh token in database
        saveRefreshToken(user.getUsername(), refreshToken);

        return refreshToken;
    }

    // Save Refresh Token in Database
    private static void saveRefreshToken(String username, String refreshToken) {
        Token token = new Token();
        token.setToken(refreshToken);
        token.setInsertTime(new java.sql.Timestamp(new Date().getTime()));
        User user = userRepository.getUserByUsername(username);
        if (user != null) {
            token.setUser(user);
            tokenRepository.saveToken(token);
        } else {
            throw new RuntimeException("User is not found");
        }
    }

    // Remove refresh token from Database
    public static void deleteRefreshToken(String username) {
        User user = userRepository.getUserByUsername(username);
        if (user != null) {
            List<Token> tokens = tokenRepository.getTokensByUserId(user.getId());
            for (Token token : tokens) {
                tokenRepository.deleteToken(token);
            }
        } else {
            throw new RuntimeException("User is not found");
        }
    }

    public static Claims verifyToken(String accessToken, String refreshToken) {
        try {
            return Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            // If the access token expires, check to refresh the token
            if (refreshToken != null && isRefreshTokenValid(refreshToken)) {
                return generateAccessTokenFromRefreshToken(refreshToken);
            } else {
                throw new JwtException("Access token is expired and refresh token is invalid or not provided");
            }
        } catch (JwtException e) {
            throw new JwtException("Invalid token");
        }
    }

    private static Claims generateAccessTokenFromRefreshToken(String refreshToken) {
        Claims claims = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).parseClaimsJws(refreshToken).getBody();
        // Lấy thông tin người dùng từ refresh token
        String username = claims.getSubject();
        User user = userRepository.getUserByUsername(username);
        if (user != null) {
            // Create access token
            return Jwts.claims().setSubject(username); // Return new claims and subject is username
        } else {
            throw new RuntimeException("User not found");
        }
    }

    private static boolean isRefreshTokenValid(String refreshToken) {
        if (tokenRepository.isRefreshTokenExists(refreshToken)) {
            Claims claims = null;

            try {
                claims = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).parseClaimsJws(refreshToken).getBody();
                Date expiration = claims.getExpiration();
                Date now = new Date();
                return !expiration.before(now); // Return true if refreshToken unexpired, else return false
            } catch (ExpiredJwtException e) {
                // If the refresh token has expired, remove it from the database and return false
                if (claims != null) {
                    deleteRefreshToken(claims.getSubject()); // Delete the refresh token from the database
                }
                return false;
            } catch (JwtException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String generateAccessTokenFromClaims(Claims claims) {
        Date expiration = claims.getExpiration();
        Date now = new Date();

        if (expiration != null && expiration.after(now)) {
            return null;
        }

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) // 15p
                .signWith(signingKey)
                .compact();
    }

}
