package com.equipo5.feelflowapp.service.auth.token.impl;

import io.jsonwebtoken.Claims;

public interface TokenService {
    Claims getAllClaimsFromToken(String token);
}
