package com.example.todo.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class Helpers {

    public static Long getAuthedID(){
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

}
