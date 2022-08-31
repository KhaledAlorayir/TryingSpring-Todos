package com.example.todo.security.Auth;

import com.example.todo.exception.InvalidCredException;
import com.example.todo.model.DB.AppUser;
import com.example.todo.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetailsService {
    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser u = userRepo.findByEmail(email).orElseThrow(() -> new InvalidCredException());
        ArrayList<SimpleGrantedAuthority> ar = new ArrayList<>();
        ar.add(new SimpleGrantedAuthority("user"));
        return new User(u.getEmail(),u.getPassword(),ar);
    }
}
