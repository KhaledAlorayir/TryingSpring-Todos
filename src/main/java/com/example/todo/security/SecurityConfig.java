package com.example.todo.security;

import com.example.todo.repo.UserRepo;
import com.example.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private UserRepo userRepo;
    private AuthSuccessHandler authSuccessHandler;

    @Value("${JWT_SECRET}")
    private String SECRET;

    public SecurityConfig(AuthSuccessHandler authSuccessHandler,UserRepo userRepo, UserService userService){
        this.authSuccessHandler = authSuccessHandler;
        this.userRepo = userRepo;
        this.userService = userService;
    }

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
                                .antMatchers(HttpMethod.GET,"/api/todo").permitAll()
                                .antMatchers("/api/todo","/api/todo/**").hasAuthority("user")
                                .anyRequest().permitAll()
                                .and()
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .addFilter(authenticationFilter())
                                .addFilter(new AuthMiddleware(authenticationManager,userRepo,userService,SECRET))
                                .exceptionHandling()
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }

                })
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    AuthAttempt authenticationFilter() throws Exception {
        AuthAttempt filter = new AuthAttempt();
        filter.setAuthenticationSuccessHandler(authSuccessHandler);
        filter.setAuthenticationManager(authenticationManager);
        filter.setFilterProcessesUrl("/api/auth/signin");
        return filter;
    }



}
