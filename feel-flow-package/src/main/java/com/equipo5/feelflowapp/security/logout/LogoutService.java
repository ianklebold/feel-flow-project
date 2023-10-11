package com.equipo5.feelflowapp.security.logout;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static com.equipo5.feelflowapp.constants.validation.security.TokenJwtConfig.*;
import static com.equipo5.feelflowapp.constants.validation.security.TokenJwtConfig.TOKEN_TYPE_AUTHORIZATION;

@Service
@RequiredArgsConstructor

public class LogoutService implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        try {
            request.logout();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        response.setHeader(HEADER_AUTHORIZATION, "");
        response.setStatus(200);
        response.setContentType(TOKEN_TYPE_AUTHORIZATION);
    }
}
