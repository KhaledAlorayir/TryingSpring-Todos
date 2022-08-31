package com.example.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCredException extends RuntimeException {

    public InvalidCredException(){
        super("invalid credentials");
    }

}
