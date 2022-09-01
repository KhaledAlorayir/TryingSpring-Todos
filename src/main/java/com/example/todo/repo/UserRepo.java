package com.example.todo.repo;

import com.example.todo.model.DB.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<AppUser,Long> {

    Optional<AppUser> findByEmail(String email);
    long count();
}
