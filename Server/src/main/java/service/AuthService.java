package service;

import configuration.LoaderConfigurator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import model.Token;
import model.User;
import repository.TokenRepository;
import repository.UserRepository;

import java.security.Key;
import java.util.Date;
import java.util.List;

public class AuthService {
    private static final String SECRET_KEY = LoaderConfigurator.getSecretKey();
    private static final Key signingKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    private static final TokenRepository tokenRepository = new TokenRepository();
    private static final UserRepository userRepository = new UserRepository();

    // Access Token
    public static String generateAccessToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) // 15 minutes 1440
                .signWith(signingKey)
                .compact();
    }

    // Refresh Token
    public static String generateRefreshToken(String username) {
        long refreshTokenExpirationTime = 30L * 24L * 60L * 60L * 1000L; // 30 days
        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationTime))
                .signWith(signingKey)
                .compact();

        // Save refresh token in database
        saveRefreshToken(username, refreshToken);

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

    public static Claims verifyToken(String accessToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
                throw new JwtException("Access token is expired");
        } catch (JwtException e) {
            throw new JwtException("Invalid token");
        }
    }

    public static boolean verifyRefreshToken(String refreshToken) {
        if (tokenRepository.isRefreshTokenExists(refreshToken)) {
            Claims claims = null;

            try {
                claims = Jwts.parser()
                        .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                        .parseClaimsJws(refreshToken)
                        .getBody();
                Date expiration = claims.getExpiration();
                Date now = new Date();
                return !expiration.before(now); // Return true if refreshToken unexpired, else return false
            } catch (ExpiredJwtException e) {
                // If the refresh token has expired, remove it from the database and return false
                String subject = e.getClaims().getSubject();
                deleteRefreshToken(subject);
                return false;
            } catch (JwtException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String getUsernameFromFresherToken(String refreshToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .parseClaimsJws(refreshToken)
                .getBody();
        return claims.getSubject();
    }

    public static User getUserIdAndRoleFromClaimAccessToken(Claims claims) {
        String username = claims.getSubject();
        return userRepository.getUserByUsername(username);
    }

}
