package com.example.todo.service;

import com.example.todo.exception.NotAuthorizedException;
import com.example.todo.exception.NotFoundException;
import com.example.todo.model.DB.AppUser;
import com.example.todo.model.DB.Todo;
import com.example.todo.repo.TodoRepo;
import com.example.todo.repo.UserRepo;
import com.example.todo.utils.Helpers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {
    private TodoRepo todoRepo;
    private UserRepo userRepo;


    public Todo CreateTodo(Todo t){
        AppUser user = userRepo.findById(Helpers.getAuthedID()).get();
        t.setOwner(user);
        return todoRepo.save(t);
    }

    public List<Todo> getTodos(){
        AppUser user = userRepo.findById(Helpers.getAuthedID()).get();
        return user.getTodos();
    }

    public Todo ToggleTodoStatus(long id){
        Todo todo = todoRepo.findById(id).orElseThrow(() -> new NotFoundException(id));

        if(todo.getOwner().getId() != Helpers.getAuthedID()){
            throw new NotAuthorizedException();
        }

        todo.setCompleted(!todo.isCompleted());
        todoRepo.save(todo);
        return todo;
    }

    public void DeleteTodo(long id){
        Todo todo = todoRepo.findById(id).orElseThrow(() -> new NotFoundException(id));

        if(todo.getOwner().getId() != Helpers.getAuthedID()){
            throw new NotAuthorizedException();
        }
        todoRepo.deleteById(id);
    }

}
