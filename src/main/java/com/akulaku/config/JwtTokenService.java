package com.akulaku.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class JwtTokenService {

    @Value("${secretkey}")
    private String SECRET_KEY;


    private Claims getClaims(String token) throws JwtException {

        JwtParser parser = Jwts.parserBuilder().setSigningKey(getSignKey()).build();
        Jws<Claims> signatureAndClaims = parser.parseClaimsJws(token);
        Claims claims = signatureAndClaims.getBody();

        return claims;
    }

    public String getUsername(String token) throws JwtException {

        Claims claims = getClaims(token);

        return claims.getSubject();
    }

    public String generateToken(
            String username,
            Date issueAt,
            Date expireAt) {
        Map<String,?> value = new HashMap<>();
        JwtBuilder builder = Jwts.builder();
        builder = builder
                .setSubject(username)
                .setIssuedAt(issueAt)
                .setExpiration(expireAt)
                .signWith(getSignKey(),SignatureAlgorithm.HS256);
        return builder.compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {

        Claims claims = getClaims(token);
        String user = claims.getSubject();

        return (user.equals(userDetails.getUsername()) & !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        Claims claims = getClaims(token);

        return claims.getExpiration().before(new Date());
    }

    public Date getExpirationToken(String token){
        Claims claims = getClaims(token);
        return claims.getExpiration();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

