package com.todo.user;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import java.util.Date;

import static io.jsonwebtoken.security.Keys.secretKeyFor;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "mySecretKey";  // Ensure it's static final
    private static final long EXPIRATION_TIME = 1000 * 60 * 15;  // 15 minutes

    public String generateToken(String username) {
        try {
            System.out.println("🛠️ Generating token for user: " + username);

            String token = Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(secretKeyFor(SignatureAlgorithm.HS256))
                    .compact();

            System.out.println("✅ Token successfully generated: " + token);
            return token;
        } catch (Exception e) {
            System.out.println("❌ Error generating token: " + e.getMessage());
            return null;
        }
    }

    public String extractUsername(String token) {
        try {
            System.out.println("🔍 Extracting username from token...");
            String username = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            System.out.println("✅ Extracted username: " + username);
            return username;
        } catch (Exception e) {
            System.out.println("❌ Error extracting username: " + e.getMessage());
            return null;
        }
    }

    public boolean validateToken(String token, String username) {
        try {
            System.out.println("🔎 Validating token for user: " + username);
            boolean isValid = extractUsername(token).equals(username) && !isTokenExpired(token);
            System.out.println("✅ Token is " + (isValid ? "valid" : "invalid"));
            return isValid;
        } catch (Exception e) {
            System.out.println("❌ Error validating token: " + e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            boolean expired = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .before(new Date());
            System.out.println("📅 Token expired: " + expired);
            return expired;
        } catch (Exception e) {
            System.out.println("❌ Error checking token expiration: " + e.getMessage());
            return true;  // Treat as expired if an error occurs
        }
    }

    public boolean validateToken(String token) {
        try {
            System.out.println("🔍 Validating token structure...");
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            System.out.println("✅ Token is structurally valid");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Invalid token: " + e.getMessage());
            return false;
        }
    }
}


