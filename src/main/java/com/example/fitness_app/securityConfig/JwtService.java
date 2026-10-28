package com.example.fitness_app.securityConfig;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;


    // FOR TOKEN GENERATE
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        claims.put("roles", roles);
        return createToken(claims, userDetails.getUsername());
    }

    public String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .subject(email)
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key())
                .compact();
    }

    // FOR KEY GENERATE METHOD
    private Key key() {
        byte[] bytesKey = this.secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(bytesKey);
    }

    // FOR EXTRACT ALL CLAIMS FROM TOKENS
    public Claims extractALLClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // GET USERNAME FROM PAYLOAD
    public String getEmailFromToken(String token) {
        return extractALLClaims(token).getSubject();
    }

    // Get ALL ROLES FROM TOKENS
    public List<String> getRolesFromToken(String token) {
        return extractALLClaims(token).get("roles", List.class);
    }

    // FOR EXPIRATION CHECK OF THE KEYS
    public boolean isTokenExpired(String token) {
        return extractALLClaims(token).getExpiration().before(new Date());
    }

    // CHECK VALIDATION USING TOKEN SUBJECT AND USER DETAILS SUBJECT
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String sub =  getEmailFromToken(token);
        if (sub.equals(userDetails.getUsername()) && !isTokenExpired(token)) {
            return true;
        }
        return false;
    }


}
