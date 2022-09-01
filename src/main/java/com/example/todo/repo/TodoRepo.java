package com.example.todo.repo;

import com.example.todo.model.DB.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoRepo extends JpaRepository<Todo,Long> {

    Optional<Todo> findByTid(String tid);
}
