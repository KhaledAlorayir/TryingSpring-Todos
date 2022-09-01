package com.example.todo.routes;

import com.example.todo.model.DTO.Message;
import com.example.todo.model.DTO.TodoReqDTO;
import com.example.todo.model.DTO.TodoResDTO;
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
    public ResponseEntity<TodoResDTO> create(@Valid @RequestBody TodoReqDTO t){
        return new ResponseEntity(todoService.CreateTodo(t), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TodoResDTO>> getAll(){
        return ResponseEntity.ok(todoService.getTodos());
    }

    @PatchMapping("{id}")
    public ResponseEntity<TodoResDTO> Toggle(@PathVariable("id") String tid) {
        return ResponseEntity.ok(todoService.ToggleTodoStatus(tid));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Message> Delete(@PathVariable("id") String tid){
        todoService.DeleteTodo(tid);
        return ResponseEntity.ok(new Message(String.format("Todo with id: %s has been deleted!",tid)));
    }

}
