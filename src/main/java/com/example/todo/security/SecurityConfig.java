package com.example.todo.security;

import com.example.todo.security.Auth.AuthMiddleware;
import com.example.todo.security.Auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpServletResponse;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsImpl userDetails;


    @Value("${JWT_SECRET}")
    private String SECRET;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests((auth) -> {
                    try{
                        auth
                                .antMatchers("/api/todo","/api/todo/**").hasAuthority("user")
                                .anyRequest().permitAll()
                                .and()
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .addFilter(new AuthMiddleware(authenticationManager,userDetails,SECRET))
                                .exceptionHandling()
                                .authenticationEntryPoint( (request, response, authException) ->
                                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"));
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }

                })
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }



}
