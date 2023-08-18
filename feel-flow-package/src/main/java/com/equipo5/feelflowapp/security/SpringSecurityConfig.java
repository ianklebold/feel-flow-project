package com.equipo5.feelflowapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean //El objeto que se devuelve, se lo guarda en el contexto de spring para que otros componentes lo puedan usar
    SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(
                        auth -> auth.requestMatchers("/api/v1/**").permitAll()
                );

        return httpSecurity.build();
    }
}
