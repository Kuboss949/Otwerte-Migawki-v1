package pl.otwartemigawki.OtwarteMigawkiApp.controller;

import exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.*;
import pl.otwartemigawki.OtwarteMigawkiApp.model.*;
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

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
    }

    @GetMapping("/all")
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






    @GetMapping("/{userId}")
    public UserWithDetailsDTO getClientById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        return UserUtil.convertToDTO(user, false);
    }

    @PostMapping("/{userId}/update")
    public ResponseEntity<ApiResponseDTO> updateUser(@PathVariable Long userId, @RequestBody UserRequestDTO userRequest) {
        try {
            User user = userService.getUserById(userId);
            UserUtil.updateUserFromDTO(user, userRequest);
            userService.saveOrUpdateUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO("User updated successfully"));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO("Failed to update: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseDTO> deleteUser(@PathVariable Long userId) {
        try{
            userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO("User deleted successfully"));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO("Failed to delete user: " + e.getMessage()));
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
