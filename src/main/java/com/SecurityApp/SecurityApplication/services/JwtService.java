package com.SecurityApp.SecurityApplication.services;

import com.SecurityApp.SecurityApplication.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Service
public class JwtService {
    @Value("${jwt.secretKey}")
    private String jwtSecreteKey;
    private SecretKey getSecreteKey(){
        return Keys.hmacShaKeyFor(jwtSecreteKey.getBytes(StandardCharsets.UTF_8));
    }
    public String generateAccessToken(User user){
       return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email",user.getEmail())
//.claim("roles", Set.of("ADMIN","USER")) **it is older way to initialize to roles
               .claim("roles",user.getRoles().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60))
                .signWith(getSecreteKey())
                .compact();
    }

    public String generateRefreshToken(User user){
        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000L *60*60*24*6))
                .signWith(getSecreteKey())
                .compact();
    }

    public Long getUserIdFromToken(String token){
        Claims claims=Jwts.parser()
                .verifyWith(getSecreteKey())
                .build().parseSignedClaims(token).getPayload();

        return Long.valueOf(claims.getSubject());
    }


}
