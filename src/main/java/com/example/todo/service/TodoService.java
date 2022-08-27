package com.example.todo.service;

import com.example.todo.exception.NotFoundException;
import com.example.todo.model.Todo;
import com.example.todo.repo.TodoRepo;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;

@Service
public class TodoService {
    private TodoRepo todoRepo;
    private Validator validator;

    public TodoService(TodoRepo todoRepo, Validator validator) {
        this.todoRepo = todoRepo;
        this.validator = validator;
    }

    public Todo CreateTodo(Todo t){

        return todoRepo.save(t);
    }

    public List<Todo> getTodos(){
        return todoRepo.findAll();
    }

    public Todo ToggleTodoStatus(long id){

      Todo todo = todoRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
      todo.setCompleted(!todo.isCompleted());
      todoRepo.save(todo);
      return todo;
    }

    public void DeleteTodo(long id){
        Todo todo = todoRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
        todoRepo.deleteById(id);

    }

}
