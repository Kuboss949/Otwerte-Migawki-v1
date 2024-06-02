package pl.otwartemigawki.OtwarteMigawkiApp.service;

import exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.TemporaryUserRequestDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.UserSessionDetailsDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.UserWithDetailsDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserDetailData;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.UserDetailRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.UserRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.util.UserUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

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
    public List<UserWithDetailsDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map((User user) -> UserUtil.convertToDTO(user, true))
                .collect(Collectors.toList());
    }

    @Override
    public void saveOrUpdateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void saveOrUpdateUserDetail(UserDetailData userDetailData) {
        userDetailRepository.save(userDetailData);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found!"));
    }

    @Override
    public User createTemporaryUser(TemporaryUserRequestDTO request) {
        User user = new User();
        user.setIsTmp(true);

        UserDetailData userDetailData = new UserDetailData();
        userDetailData.setIdUser(user);
        userDetailData.setName(request.getName());
        userDetailData.setSurname(request.getSurname());
        userDetailData.setPhone(request.getPhone());

        this.saveOrUpdateUser(user);
        this.saveOrUpdateUserDetail(userDetailData);

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()->new UserNotFoundException("User not found!"));
    }
}
