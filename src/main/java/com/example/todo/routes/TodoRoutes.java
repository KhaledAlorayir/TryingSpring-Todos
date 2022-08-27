package com.example.todo.routes;

import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/todo")
public class TodoRoutes {

    private TodoService todoService;

    public TodoRoutes(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<Todo> create(@RequestBody Todo t){
        return new ResponseEntity<>(todoService.CreateTodo(t), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAll(){
        return new ResponseEntity<>(todoService.getTodos(),HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Todo> Toggle(@PathVariable("id") Long id) {
        return new ResponseEntity<>(todoService.ToggleTodoStatus(id),HttpStatus.OK);
    }

}
