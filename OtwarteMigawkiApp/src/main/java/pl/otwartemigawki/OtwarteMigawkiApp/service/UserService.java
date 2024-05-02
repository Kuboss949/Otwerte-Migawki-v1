package pl.otwartemigawki.OtwarteMigawkiApp.service;

import pl.otwartemigawki.OtwarteMigawkiApp.dto.UserSessionDetailsDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserDetail;

import java.util.List;

public interface UserService {

    User getUserById(Long id);
    List<User> getAllUsers();
    List<UserSessionDetailsDTO> getUsersWithUnassignedGallery();
    void saveOrUpdateUser(User user);
    void saveOrUpdateUserDetail(UserDetail userDetail);
    void deleteUser(Long id);
}
