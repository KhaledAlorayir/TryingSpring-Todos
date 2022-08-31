package com.example.todo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.todo.exception.InvalidCredException;
import com.example.todo.exception.UserExistsException;
import com.example.todo.model.DB.AppUser;
import com.example.todo.model.LoginForm;
import com.example.todo.model.Token;
import com.example.todo.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;

    private final AuthenticationManager authenticationManager;
    @Value("${JWT_SECRET}")
    private String SECRET;



    public void Signup(AppUser user) {
        userRepo.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new UserExistsException(u.getEmail());
        });
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public Token Signin(LoginForm loginForm){
        try{
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getEmail(),loginForm.getPassword()));

            //Auth Success
            AppUser u =  userRepo.findByEmail(loginForm.getEmail()).get();

            Map<String,String> JWTpayload = new HashMap();
            JWTpayload.put("email",u.getEmail());
            JWTpayload.put("uid",u.getId().toString());

            String token = JWT.create()
                    .withPayload(JWTpayload)
                    .withExpiresAt(new Date(System.currentTimeMillis() + 8 * 60 * 60 * 1000))
                    .sign(Algorithm.HMAC256(SECRET));

            return new Token(token);

        }catch (AuthenticationException e) {
            throw new InvalidCredException();
        }

    }


}
