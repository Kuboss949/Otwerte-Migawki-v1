package pl.otwartemigawki.OtwarteMigawkiApp.controller;

import exceptions.RegistrationException;
import exceptions.UserNotFoundException;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.*;
import pl.otwartemigawki.OtwarteMigawkiApp.model.*;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.UserDetailRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.UserRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.service.JwtService;
import pl.otwartemigawki.OtwarteMigawkiApp.service.RoleService;
import pl.otwartemigawki.OtwarteMigawkiApp.service.UserService;
import pl.otwartemigawki.OtwarteMigawkiApp.util.UserUtil;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "https://localhost:3000")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;

    public UserController(UserService userService, RoleService roleService, JwtService jwtService,
                          UserRepository userRepository,
                          UserDetailRepository userDetailRepository) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.userDetailRepository = userDetailRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<UserWithDetailsDTO>> getAllClients() {
        List<User> users = userService.getAllUsers(); // Assuming you have a service method to fetch all users
        List<UserWithDetailsDTO> dtos = users.stream()
                .map((User user) -> UserUtil.convertToDTO(user, true))
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/unassigned-galleries")
    public ResponseEntity<List<UserSessionDetailsDTO>> getClientsWithUnassignedGallery() {
        List<UserSessionDetailsDTO> users = userService.getUsersWithUnassignedGallery(); // Assuming you have a service method to fetch all users
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/get-username")
    public ResponseEntity<ApiResponseDTO> getAllClients(@CookieValue(name = "jwtToken", defaultValue = "defaultValue") String cookieValue) {
        User user = userService.getUserByEmail(jwtService.extractUserName(cookieValue));
        return ResponseEntity.ok(new ApiResponseDTO(user.getUsername(), true));
    }



    @GetMapping("/get-user-info")
    public ResponseEntity<UserWithDetailsDTO> getClient(@CookieValue(name = "jwtToken", defaultValue = "defaultValue") String cookieValue) {
        User user = userService.getUserByEmail(jwtService.extractUserName(cookieValue));
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return ResponseEntity.ok(UserUtil.convertToDTO(user, false));
    }

    @PostMapping("/update-user-info")
    public ResponseEntity<ApiResponseDTO> updateUser(@CookieValue(name = "jwtToken", defaultValue = "defaultValue") String cookieValue,
                                                     @RequestBody UserRequestDTO userRequest) {
        try {
            User user = userService.getUserByEmail(jwtService.extractUserName(cookieValue));
            UserUtil.checkUpdateRequest(userRequest, userRepository, userDetailRepository);
            UserUtil.updateUserFromDTO(user, userRequest);
            userService.saveOrUpdateUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO("Twoje dane zostały poprawnie zaktualizowane", true));
        }catch(RegistrationException e){
            return ResponseEntity.ok(new ApiResponseDTO(e.getMessage(), false));
        }catch (Exception e){
            return ResponseEntity.ok(new ApiResponseDTO("Błąd serwera, prosimy spróbować później", false));
        }
    }
    @PostMapping("/update-user-password")
    public ResponseEntity<ApiResponseDTO> updateUserPassword(@CookieValue(name = "jwtToken", defaultValue = "defaultValue") String cookieValue,
                                                             @RequestBody PasswordChangeRequestDTO request) {
        try {
            User user = userService.getUserByEmail(jwtService.extractUserName(cookieValue));
            UserUtil.updateUserPasswordFromDTO(user, request);
            userService.saveOrUpdateUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO("Hasło zostało zmienione!", true));
        }catch(RegistrationException e){
            return ResponseEntity.ok(new ApiResponseDTO(e.getMessage(), false));
        }catch (Exception e){
            return ResponseEntity.ok(new ApiResponseDTO("Błąd serwera, prosimy spróbować później", false));
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseDTO> deleteUser(@PathVariable Long userId) {
        try{
            userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO("User deleted successfully", true));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO("Failed to delete user: " + e.getMessage(), false));
        }
    }

    @PostMapping("/temporary")
    public User createTemporaryUser(@RequestBody TemporaryUserRequestDTO request) {

        User user = new User();
        user.setIsTmp(true);

        UserDetailData userDetailData = new UserDetailData();
        userDetailData.setIdUser(user);
        userDetailData.setName(request.getName());
        userDetailData.setSurname(request.getSurname());
        userDetailData.setPhone(request.getPhone());

        userService.saveOrUpdateUser(user);
        userService.saveOrUpdateUserDetail(userDetailData);

        return user;
    }



}
