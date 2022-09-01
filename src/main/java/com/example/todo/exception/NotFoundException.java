package com.example.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {


    public NotFoundException(String id){
        super(String.format("no Todo found with this ID: %d",id));
    }


}
