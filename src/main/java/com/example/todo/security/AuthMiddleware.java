package com.example.todo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.todo.model.DB.AppUser;
import com.example.todo.repo.UserRepo;
import com.example.todo.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class AuthMiddleware extends BasicAuthenticationFilter {

    private final String prefix = "Bearer ";
    private UserRepo userRepo;
    private UserService userService;
    private String SECRET;




    public AuthMiddleware(AuthenticationManager authenticationManager, UserRepo userRepo, UserService userService,String SECRET){
        super(authenticationManager);
        this.userService = userService;
        this.userRepo = userRepo;
        this.SECRET=SECRET;
    }




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken auth = getAuth(request);
        if(auth == null){
            chain.doFilter(request,response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(auth);
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuth(HttpServletRequest req){
        String token = req.getHeader(HttpHeaders.AUTHORIZATION);
        if(token == null || !token.startsWith(prefix)){
            return null;
        }
        String uid = JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token.replace(prefix,""))
                .getSubject();
        if(uid == null){
            return null;
        }


        AppUser u = userRepo.findById(Long.parseLong(uid)).get();
        UserDetails ud = userService.loadUserByUsername(u.getEmail());

        return new UsernamePasswordAuthenticationToken(ud.getUsername(),null,ud.getAuthorities());
    }

}
