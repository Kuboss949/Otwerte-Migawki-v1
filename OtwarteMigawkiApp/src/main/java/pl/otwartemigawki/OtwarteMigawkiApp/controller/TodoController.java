package pl.otwartemigawki.OtwarteMigawkiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.otwartemigawki.OtwarteMigawkiApp.model.ApiResponse;
import pl.otwartemigawki.OtwarteMigawkiApp.model.Todo;
import pl.otwartemigawki.OtwarteMigawkiApp.service.TodoService;

import java.util.List;

@RestController
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping("/api")
    public ApiResponse homeController(){
        ApiResponse res = new ApiResponse();
        res.setMessage("todo deleted successfully");
        return res;
    }

    @GetMapping("/api/todos")
    public List<Todo> getAllTodos(){
        return todoService.getAllTodos();
    }

    @PostMapping("/api/todos")
    public Todo createTodo(@RequestBody Todo todo){
        return todoService.createTodo(todo);
    }

    @DeleteMapping("/api/todos/{id}")
    public ApiResponse deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
        ApiResponse res = new ApiResponse();
        res.setMessage("todo deleted successfully");
        return res;
    }


}
