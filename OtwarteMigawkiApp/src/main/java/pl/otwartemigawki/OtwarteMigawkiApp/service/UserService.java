package pl.otwartemigawki.OtwarteMigawkiApp.service;

import pl.otwartemigawki.OtwarteMigawkiApp.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User createUser(User todo);
    void deleteUser(Long id);
}
