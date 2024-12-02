package com.Controller;
import com.Service.TodoService;
import com.model.TodoItem
;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/todo")
@CrossOrigin(origins = "http://localhost:3000")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<TodoItem> getTodo() {
        return todoService.getAllTodo();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoItem addCompany(@RequestBody TodoItem todoItem) {
        return todoService.create(todoItem);
    }


}