package com.example.todo.model.DB;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    @NotBlank(message = "message is requierd!")
    @Size(max = 100, message = "message is too long!")
    private String message;
    private boolean completed = false;
    @CreationTimestamp
    private Date createdAt;

    public void setMessage(String message) {
        this.message = message.trim();
    }
}
