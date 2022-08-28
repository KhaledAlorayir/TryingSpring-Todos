package com.example.todo.routes;

import com.example.todo.model.Message;
import com.example.todo.model.DB.Todo;
import com.example.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/todo")
@AllArgsConstructor
public class TodoRoutes {

    private TodoService todoService;


    @PostMapping
    public ResponseEntity<Todo> create(@Valid @RequestBody Todo t){
        return new ResponseEntity(todoService.CreateTodo(t), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAll(){
        return ResponseEntity.ok(todoService.getTodos());
    }

    @PatchMapping("{id}")
    public ResponseEntity<Todo> Toggle(@PathVariable("id") Long id) {
        return ResponseEntity.ok(todoService.ToggleTodoStatus(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Message> Delete(@PathVariable("id") Long id){
        todoService.DeleteTodo(id);
        return ResponseEntity.ok(new Message(String.format("Todo with id: %d has been deleted!",id)));
    }

}
