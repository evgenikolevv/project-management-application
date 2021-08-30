package com.evgeni.service;

import com.evgeni.service.interfaces.TokenService;
import com.evgeni.userdetails.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@Slf4j
public class JwtTokenServiceImpl implements TokenService {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    private static final String AUTHORITY = "authority";

    @Override
    public String generateToken(Authentication user) {
        Instant expirationTime = Instant.now().plus(1, ChronoUnit.HOURS);
        Date expirationDate = Date.from(expirationTime);

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());

        UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();

        String compactTokenString = Jwts.builder()
                .setSubject(userDetails.getId().toString())
                .claim(AUTHORITY, userDetails.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return compactTokenString;
    }

    @Override
    public Boolean validateToken(String token) {
        byte[] secretBytes = JWT_SECRET.getBytes();

        try {
            Jws<Claims> jwsClaims = Jwts.parserBuilder()
                    .setSigningKey(secretBytes)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            log.error("Invalid token.");
            return false;
        }
    }

    @Override
    public Long getUserId(String token) {
        Claims body = Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(body.getSubject());
    }

    @Override
    public List<String> getUserRole(String token) {
        Claims body = Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return List.of(((List<LinkedHashMap<String, String>>) body.get("authority")).get(0).get(AUTHORITY));
    }
}
