package com.example.todo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.todo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor

public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Value("${JWT_SECRET}")
    private String SECRET;



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
        UserDetails ud = (UserDetails) authResult.getPrincipal();
        String token = JWT.create()
                .withSubject(userService.getUserID(ud.getUsername()).toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + 8 * 60 * 60 * 1000))
                .sign(Algorithm.HMAC256(SECRET));

        Map<String,String> tokenRes = new HashMap<>();
        tokenRes.put("token",token);
        response.setContentType(APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(),tokenRes);
    }
}
