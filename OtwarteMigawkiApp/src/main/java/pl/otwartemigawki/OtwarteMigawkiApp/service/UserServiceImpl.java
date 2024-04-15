package pl.otwartemigawki.OtwarteMigawkiApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService{
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User createUser(User todo) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
