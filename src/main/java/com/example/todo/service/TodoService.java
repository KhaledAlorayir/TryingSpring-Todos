package com.example.todo.service;

import com.example.todo.exception.NotFoundException;
import com.example.todo.model.DB.Todo;
import com.example.todo.repo.TodoRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {
    private TodoRepo todoRepo;


    public Todo CreateTodo(Todo t){
        return todoRepo.save(t);
    }

    public List<Todo> getTodos(){
        return todoRepo.findAll();
    }

    public Todo ToggleTodoStatus(long id){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Todo todo = todoRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
      todo.setCompleted(!todo.isCompleted());
      todoRepo.save(todo);
      return todo;
    }

    public void DeleteTodo(long id){
        todoRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
        todoRepo.deleteById(id);
    }

}
