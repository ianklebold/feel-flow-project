package com.equipo5.feelflowapp.security;

import com.equipo5.feelflowapp.controller.QuestionsAnswersModuleController;
import com.equipo5.feelflowapp.controller.SurveyModuleController;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import com.equipo5.feelflowapp.security.filters.JwtAutheticationFilter;
import com.equipo5.feelflowapp.security.filters.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private UserRepository userRepository;

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
                        auth -> auth
                                .requestMatchers(HttpMethod.POST,"/api/v1/twelve_steps_modules/{idTeam}").hasAnyAuthority("TEAM_LEADER","ADMIN")
                                .requestMatchers("/api/v1/user/**").hasAnyAuthority("TEAM_LEADER","ADMIN","USER_REGULAR")
                                .requestMatchers(HttpMethod.POST,"/api/v1/admin/**").permitAll()
                                .requestMatchers("/api/v1/admin/**").hasAnyAuthority("TEAM_LEADER","ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/api/v1/team/{idTeam}").hasAnyAuthority("TEAM_LEADER","ADMIN")
                                .requestMatchers(HttpMethod.POST,"/api/v1/team/{idTeam}/invite").hasAnyAuthority("TEAM_LEADER","ADMIN")
                                .requestMatchers(HttpMethod.GET,"/api/v1/team/{idTeam}/team-leader").hasAnyAuthority("TEAM_LEADER","ADMIN","USER_REGULAR")
                                .requestMatchers(HttpMethod.GET,"/api/v1/team/{idTeam}").hasAnyAuthority("TEAM_LEADER","ADMIN","USER_REGULAR")
                                .requestMatchers(HttpMethod.GET,"/api/v1/team").hasAnyAuthority("TEAM_LEADER","ADMIN","USER_REGULAR")
                                .requestMatchers(HttpMethod.POST,"/api/v1/team").hasAnyAuthority("TEAM_LEADER","ADMIN","USER_REGULAR")
                                .requestMatchers(HttpMethod.GET,SurveyModuleController.SURVEY_PATH).hasAnyAuthority("USER_REGULAR")
                                .requestMatchers(HttpMethod.POST,SurveyModuleController.SURVEY_PATH.concat("/twelve_steps_module")).hasAnyAuthority("USER_REGULAR")
                                .requestMatchers(HttpMethod.GET, QuestionsAnswersModuleController.QUESTION_AND_ANSWERS_PATH+QuestionsAnswersModuleController.ANSWERS_MODULE).hasAnyAuthority("USER_REGULAR")
                                .requestMatchers(HttpMethod.GET,QuestionsAnswersModuleController.QUESTION_AND_ANSWERS_PATH+QuestionsAnswersModuleController.QUESTIONS_MODULE).hasAnyAuthority("USER_REGULAR")
                                .requestMatchers("/api/v1/regular_user/**").permitAll()
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                                .anyRequest()
                                .authenticated()
                ).csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .addFilter(new JwtAutheticationFilter(this.authenticationConfiguration.getAuthenticationManager(),userRepository))
                .addFilter(new JwtValidationFilter(this.authenticationConfiguration.getAuthenticationManager()));
        return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(
                Arrays.asList("http://127.0.0.1:8000", "http://127.0.0.1:5500", "http://127.0.0.1:3000","http://localhost:5173")
        );
        config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        config.setAllowCredentials(true);


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
        return source;
    }



    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
                new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
