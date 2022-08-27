package com.example.todo.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    @NotBlank(message = "message is requierd!")
    private String message;
    private boolean completed = false;
    @CreationTimestamp
    private Date createdAt;

    public void setMessage(String message) {
        this.message = message.trim();
    }
}
