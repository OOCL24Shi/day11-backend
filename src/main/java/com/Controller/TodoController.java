package com.Controller;
import com.model.TodoItem
;import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/todo")
@CrossOrigin(origins = "http://localhost:3000")
public class TodoController {

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity <List<TodoItem>> getTodo () {

        TodoItem todoItem = new TodoItem(99,"hello word",false);
        return ResponseEntity.ok(List.of(todoItem));
    }

}