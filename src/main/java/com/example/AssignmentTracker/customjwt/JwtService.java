package com.example.AssignmentTracker.customjwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey secretkey;
    private final Long  expirationMs;
    public JwtService(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration-ms}")Long expiration){
        this.secretkey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expiration;
    }

    public String generateToken(String email,String role)
    {
        Date now = new Date();
        return Jwts.builder().
                subject(email)
                .claim("role",role)
                .issuedAt(now)
                .signWith(secretkey,Jwts.SIG.HS256)
                .compact();

    }

    public String extractToken(String token)
    {
        return Jwts.parser()
                .verifyWith(secretkey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

    }


}
