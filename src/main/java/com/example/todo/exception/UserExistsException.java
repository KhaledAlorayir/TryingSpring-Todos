package com.example.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserExistsException extends RuntimeException {
    public UserExistsException(String email){
        super(String.format("this email: '%s' is already used!",email));
    }
}
