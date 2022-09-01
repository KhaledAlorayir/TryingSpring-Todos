package com.example.todo.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TodoResDTO {
    private String tid;
    private String message;
    private boolean completed;
    private Date createdAt;
    private String owner_id;
}
