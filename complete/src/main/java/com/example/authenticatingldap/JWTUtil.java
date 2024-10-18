package com.example.authenticatingldap;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JWTUtil {

    // code to generate Token
    public static String generateToken(String subject, String secret_key) {
        byte[] keyBytes = secret_key.getBytes();
        return Jwts.builder()
                .setId("tk9931")
                .setSubject(subject)
                .setIssuer("ABC_Ltd")
                .setAudience("XYZ_Ltd")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)))
                .signWith(SignatureAlgorithm.HS512, keyBytes)
                .compact();
    }

    // code to get Claims
    public static Claims getClaims(String token, String secret_key) {
        byte[] keyBytes = secret_key.getBytes();
        return Jwts.parserBuilder()
                .setSigningKey(keyBytes)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}