package com.example.todo.model.DTO;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {
    @NotBlank(message = "Email is required!")
    @Email(message = "Email should be valid!")
    private String email;
    @Length(min = 8 , message = "password should be at lest 8 chars")
    private String password;
}
