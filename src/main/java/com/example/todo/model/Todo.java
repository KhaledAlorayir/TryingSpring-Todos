package com.example.todo.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String message;
    private boolean completed = false;
    @CreationTimestamp
    private Date createdAt;

    public void setMessage(String message) {
        this.message = message.trim();
    }
}
