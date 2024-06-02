package pl.otwartemigawki.OtwarteMigawkiApp.service;

import org.springframework.web.bind.annotation.RequestBody;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.TemporaryUserRequestDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.UserSessionDetailsDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.UserWithDetailsDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserDetailData;

import java.util.List;

public interface UserService {

    User getUserById(Long id);
    List<UserWithDetailsDTO> getAllUsers();
    void saveOrUpdateUser(User user);
    void saveOrUpdateUserDetail(UserDetailData userDetailData);
    void deleteUser(Long id);
    User getUserByEmail(String extractUserName);
    User createTemporaryUser(TemporaryUserRequestDTO request);
}
