package com.example.todo.service;

import com.example.todo.exception.NotAuthorizedException;
import com.example.todo.exception.NotFoundException;
import com.example.todo.model.DB.AppUser;
import com.example.todo.model.DB.Todo;
import com.example.todo.model.DTO.TodoReqDTO;
import com.example.todo.model.DTO.TodoResDTO;
import com.example.todo.repo.TodoRepo;
import com.example.todo.repo.UserRepo;
import com.example.todo.utils.Helpers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {
    private TodoRepo todoRepo;
    private UserRepo userRepo;

    // TODO: 9/1/2022 insert id insted of obj

    public TodoResDTO CreateTodo(TodoReqDTO t){
        AppUser user = userRepo.findById(Helpers.getAuthedID()).get();

        Todo todo = new Todo();
        todo.setMessage(t.getMessage());
        todo.setOwner(user);
        todo.setTid(String.format("T-%d-%d",user.getId(),user.getTodos().size()+1));
        todoRepo.save(todo);

        return new TodoResDTO(todo.getTid(),todo.getMessage(),todo.isCompleted(),todo.getCreatedAt(),todo.getOwner().getUid());

    }

    public List<TodoResDTO> getTodos(){
        AppUser user = userRepo.findById(Helpers.getAuthedID()).get();
        List<TodoResDTO> todos = new ArrayList<>();
        user.getTodos().forEach((todo -> todos.add(new TodoResDTO(todo.getTid(),todo.getMessage(),todo.isCompleted(),todo.getCreatedAt(),todo.getOwner().getUid()))));

        return todos;
    }

    public TodoResDTO ToggleTodoStatus(String id){
        Todo todo = todoRepo.findByTid(id).orElseThrow(() -> new NotFoundException(id));

        if(todo.getOwner().getId() != Helpers.getAuthedID()){
            throw new NotAuthorizedException();
        }

        todo.setCompleted(!todo.isCompleted());
        todoRepo.save(todo);

        return new TodoResDTO(todo.getTid(),todo.getMessage(),todo.isCompleted(),todo.getCreatedAt(),todo.getOwner().getUid());
    }

    public void DeleteTodo(String id){
        Todo todo = todoRepo.findByTid(id).orElseThrow(() -> new NotFoundException(id));

        if(todo.getOwner().getId() != Helpers.getAuthedID()){
            throw new NotAuthorizedException();
        }
        todoRepo.deleteById(todo.getId());
    }

}
