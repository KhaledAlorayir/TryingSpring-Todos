package com.example.todo.routes;

import com.example.todo.model.DTO.LoginForm;
import com.example.todo.model.DTO.Message;
import com.example.todo.model.DTO.Token;
import com.example.todo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/auth")
@AllArgsConstructor
public class AuthRoutes {

    private AuthService userService;

    @PostMapping(path = "/signup")
    public ResponseEntity<Message> Signup(@Valid @RequestBody LoginForm user) {
        userService.Signup(user);
        return ResponseEntity.ok(new Message("Welcome!"));
    }

    @PostMapping(path = "/signin")
    public ResponseEntity<Token> Signin(@Valid @RequestBody LoginForm loginForm){
        return  ResponseEntity.ok(userService.Signin(loginForm));
    }

}
