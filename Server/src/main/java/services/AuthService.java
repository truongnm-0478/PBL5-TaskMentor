package services;

import config.ConfigLoader;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import models.User;
import redis.clients.jedis.Jedis;

import java.util.Date;

public class AuthService {
    private static final String REFRESH_TOKEN_KEY_PREFIX = ConfigLoader.getRefreshTokenKeyPrefix();

    // Access Token
    public static String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) // 15p
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256), SignatureAlgorithm.HS256)
                .compact();
    }

    // Refresh Token
    public static String generateRefreshToken(User user) {
        long refreshTokenExpirationTime = 30L * 24L * 60L * 60L * 1000L; // 30 day
        String refreshToken = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationTime))
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256), SignatureAlgorithm.HS256)
                .compact();

        // Save refresh token in Redis
        Jedis jedis = new Jedis(ConfigLoader.getRedisHost());
        jedis.set(REFRESH_TOKEN_KEY_PREFIX + user.getUsername(), refreshToken);
        jedis.expire(REFRESH_TOKEN_KEY_PREFIX + user.getUsername(), (int) (refreshTokenExpirationTime / 1000));
        jedis.close();

        return refreshToken;
    }

    // check access token
    public static Claims verifyToken(String token) {
        return Jwts.parser().setSigningKey(Keys.secretKeyFor(SignatureAlgorithm.HS256)).parseClaimsJws(token).getBody();
    }

    // check refresh token
    public static boolean isValidRefreshToken(String refreshToken, String username) {
        Jedis jedis = new Jedis(ConfigLoader.getRedisHost());
        String storedRefreshToken = jedis.get(REFRESH_TOKEN_KEY_PREFIX + username);
        jedis.close();

        return storedRefreshToken != null && refreshToken.equals(storedRefreshToken);
    }
}
