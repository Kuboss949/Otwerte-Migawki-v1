package pl.otwartemigawki.OtwarteMigawkiApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.UserSessionDetailsDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserDetail;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.UserDetailRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserDetailRepository userDetailRepository) {
        this.userRepository = userRepository;
        this.userDetailRepository = userDetailRepository;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserSessionDetailsDTO> getUsersWithUnassignedGallery() {
        return userRepository.findSessionsWithoutGalleries();
    }

    @Override
    public void saveOrUpdateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void saveOrUpdateUserDetail(UserDetail userDetail) {
        userDetailRepository.save(userDetail);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
