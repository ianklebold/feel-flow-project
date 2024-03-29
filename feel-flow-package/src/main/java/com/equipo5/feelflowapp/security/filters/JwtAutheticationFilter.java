package com.equipo5.feelflowapp.security.filters;

import com.equipo5.feelflowapp.domain.enumerations.teamRoles.TeamRoles;
import com.equipo5.feelflowapp.domain.users.User;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.*;

import static com.equipo5.feelflowapp.constants.validation.security.TokenJwtConfig.*;

public class JwtAutheticationFilter extends UsernamePasswordAuthenticationFilter {

    private final  AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    public JwtAutheticationFilter(AuthenticationManager authenticationManager,UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        User user = null;
        String username = null;
        String password = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(),User.class);
            username = user.getUsername();
            password = user.getPassword();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername();

        Optional<User> user = userRepository.findByUsername(username);

        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
        boolean isAdmin = roles.stream().anyMatch(role -> role.getAuthority().equals(TeamRoles.ADMIN.name()));
        //Los claims se usan para guardar cualquier tipo de dato en el token
        Claims claims = Jwts.claims();
        claims.put("authorities",new ObjectMapper().writeValueAsString(roles));
        claims.put("isAdmin",isAdmin);
        user.ifPresent(value -> claims.put("id", value.getUuid()));

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .signWith(SECRET_KEY)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) //Una hora de sesion
                .compact();

        response.addHeader(HEADER_AUTHORIZATION,PREFIX_BEARER + token);
        Map<String,Object> body = new HashMap<>();
        body.put("token",token);
        body.put("message",String.format("%s Haz iniciado sesion con exito",username));
        body.put("username",username);
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType(TOKEN_TYPE_AUTHORIZATION);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String,Object> body = new HashMap<>();
        body.put("message","Error en la autenticacion username o password incorrecto");
        body.put("error",failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType(TOKEN_TYPE_AUTHORIZATION);
    }
}
