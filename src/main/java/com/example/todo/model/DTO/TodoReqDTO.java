package com.example.todo.model.DTO;


import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class TodoReqDTO {
    @Column(nullable = false)
    @NotBlank(message = "message is requierd!")
    @Size(max = 100, message = "message is too long!")
    private String message;
}
