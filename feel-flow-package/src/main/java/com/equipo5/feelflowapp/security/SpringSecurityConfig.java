package com.equipo5.feelflowapp.security;

import com.equipo5.feelflowapp.security.filters.JwtAutheticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

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

        //Filtro de autenticacion --> Busqueda con JPA
        httpSecurity.addFilter(new JwtAutheticationFilter(this.authenticationConfiguration.getAuthenticationManager()));

        httpSecurity.authorizeHttpRequests(
                        auth -> auth.requestMatchers("/api/v1/**").permitAll()
                                .anyRequest()
                                .authenticated()
                ).csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }
}
