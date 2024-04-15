package pl.otwartemigawki.OtwarteMigawkiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.otwartemigawki.OtwarteMigawkiApp.model.ApiResponse;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserUpdateRequest;
import pl.otwartemigawki.OtwarteMigawkiApp.service.UserService;

@RestController
public class UserController {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserService userService;

    @GetMapping("/clients")
    public ApiResponse getAllClients() {
        return null;
    }

    @GetMapping("/clients/{userId}")
    public ApiResponse getClientById(@PathVariable Integer userId) {
        return null;
    }

    @PostMapping("/clients/{userId}/update")
    public ApiResponse updateUser(@PathVariable Integer userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        return null;
    }

    @DeleteMapping("/clients/{userId}")
    public ApiResponse deleteUser(@PathVariable Integer userId) {
        return null;
    }



}
