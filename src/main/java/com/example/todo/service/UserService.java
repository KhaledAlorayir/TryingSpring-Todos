package com.example.todo.service;

import com.example.todo.exception.UserExistsException;
import com.example.todo.model.DB.AppUser;
import com.example.todo.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private UserRepo userRepo;
    private PasswordEncoder encoder;

    public void Signup(AppUser user) {
        userRepo.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new UserExistsException(u.getEmail());
        });
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public Long getUserID(String email){
        AppUser u = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user found with this email"));
        return u.getId();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser u = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("invalid credentials"));
        ArrayList<SimpleGrantedAuthority> ar = new ArrayList<>();
        ar.add(new SimpleGrantedAuthority("user"));
        return new User(u.getEmail(),u.getPassword(),ar);
    }



}
