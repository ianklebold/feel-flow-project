package com.equipo5.feelflowapp.security.filters;

import com.equipo5.feelflowapp.security.constructors.SimpleGrantedAuthorityJsonConstructor;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.*;

import static com.equipo5.feelflowapp.constants.validation.security.TokenJwtConfig.*;

@Slf4j
public class JwtValidationFilter extends BasicAuthenticationFilter {
    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(HEADER_AUTHORIZATION);

        if(header == null || !header.startsWith(PREFIX_BEARER)){
            chain.doFilter(request,response);
            return;
        }

        String token = header.replace(PREFIX_BEARER,"");

        try{
            log.info("Token : " + token);
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()//Se valida la firma del token
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();

            Object authoritiesClaims = claims.get("authorities");

            Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
                    .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonConstructor.class)
                    .readValue(authoritiesClaims.toString().getBytes(),SimpleGrantedAuthority[].class));

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,null,authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request,response);

        }catch (JwtException jwtException){
            Map<String,String> body = new HashMap<>();
            body.put("error",jwtException.getMessage());
            body.put("message","El token JWT no es valido");

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(403);
            response.setContentType(TOKEN_TYPE_AUTHORIZATION);
        }

    }
}
