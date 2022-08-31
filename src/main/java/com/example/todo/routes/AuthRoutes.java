package com.example.todo.routes;

import com.example.todo.model.DB.AppUser;
import com.example.todo.model.LoginForm;
import com.example.todo.model.Message;
import com.example.todo.model.Token;
import com.example.todo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/auth")
@AllArgsConstructor
public class AuthRoutes {

    private AuthService userService;

    @PostMapping(path = "/signup")
    public ResponseEntity<Message> Signup(@Valid @RequestBody AppUser user) {
        userService.Signup(user);
        return ResponseEntity.ok(new Message("Hello"));
    }

    @PostMapping(path = "/signin")
    public ResponseEntity<Token> Signin(@Valid @RequestBody LoginForm loginForm){
        return  ResponseEntity.ok(userService.Signin(loginForm));
    }

}
