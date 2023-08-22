package com.equipo5.feelflowapp.constants.validation.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class TokenJwtConfig {
    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.ES256);
    public static final  String PREFIX_BEARER = "Bearer ";

    public static final  String HEADER_AUTHORIZATION = "Authorization";

    public static final  String TOKEN_TYPE_AUTHORIZATION = "application/json";

}
