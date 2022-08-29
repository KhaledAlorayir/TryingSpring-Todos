package com.example.todo.model.DB;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "user")
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false ,unique = true)
    @NotBlank(message = "Email is required!")
    @Email(message = "Email should be valid!")
    private String email;
    @Column(nullable = false)
    @Length(min = 8 , message = "password should be at lest 8 chars")
    private String password;
    @CreationTimestamp
    private Date createdAt;

    public void setEmail(String email) {
        this.email = email.trim();
    }

    public void setPassword(String password) {
        this.password = password.trim();
    }
}
