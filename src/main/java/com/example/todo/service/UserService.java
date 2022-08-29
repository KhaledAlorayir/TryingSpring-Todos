package com.example.todo.service;

import com.example.todo.exception.UserExistsException;
import com.example.todo.model.DB.AppUser;
import com.example.todo.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepo userRepo;
    private PasswordEncoder encoder;

    public void Signup(AppUser user) {
        userRepo.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new UserExistsException(u.getEmail());
        });
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }

}
