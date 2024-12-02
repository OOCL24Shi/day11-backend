package com.Service;

import com.Repository.TodoRepository;
import com.model.TodoItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }


    public List<TodoItem> getAllTodo(){

        return todoRepository.findAll();
    }

    public TodoItem create(TodoItem todoItem) {
        return todoRepository.save(todoItem);
    }
}
