package com.equipo5.feelflowapp.service.auth.token.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import static com.equipo5.feelflowapp.constants.validation.security.TokenJwtConfig.SECRET_KEY;

@Service
public class TokenServiceImpl implements TokenService{
    @Override
    public Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token.replaceAll("Bearer ","").trim())
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
}
