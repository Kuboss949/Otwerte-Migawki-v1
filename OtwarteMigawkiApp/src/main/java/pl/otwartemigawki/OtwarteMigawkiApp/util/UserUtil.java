package pl.otwartemigawki.OtwarteMigawkiApp.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.UserRequestDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.UserWithDetailsDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.Role;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserDetail;
import pl.otwartemigawki.OtwarteMigawkiApp.service.RoleService;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;


public class UserUtil {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    static public User createUserFromDTO(UserRequestDTO userRequestDTO, RoleService roleService){
        User user = new User();
        String salt = generateSalt();
        String hashedPassword = passwordEncoder.encode(userRequestDTO.getPassword() + salt);

        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(hashedPassword);
        user.setSalt(salt);
        user.setIsTmp(userRequestDTO.isTmp());

        Role role = roleService.getRoleByName("user");
        user.setIdRole(role);

        return user;
    }

    public static UserDetail createUserDetailFromDTO(UserRequestDTO userRequestDTO){
        UserDetail userDetail = new UserDetail();
        userDetail.setName(userRequestDTO.getName());
        userDetail.setSurname(userRequestDTO.getSurname());
        userDetail.setPhone(userRequestDTO.getPhone());
        return userDetail;
    }

    public static UserWithDetailsDTO convertToDTO(User user, boolean withPassword) {
        UserWithDetailsDTO dto = new UserWithDetailsDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        if(withPassword){
            dto.setPassword(user.getPassword());
            dto.setSalt(user.getSalt());
        }
        dto.setIdRoleId(user.getIdRole() != null ? user.getIdRole().getId() : null);
        dto.setIsTmp(user.getIsTmp());
        dto.setName(user.getUserDetail().getName());
        dto.setSurname(user.getUserDetail().getSurname());
        dto.setPhone(user.getUserDetail().getPhone());
        return dto;
    }

    public static void updateUserFromDTO(User user, UserRequestDTO userRequest){
        if(!Objects.equals(userRequest.getPassword(), "")){
            String salt = generateSalt();
            String hashedPassword = passwordEncoder.encode(userRequest.getPassword() + salt);
            user.setPassword(hashedPassword);
            user.setSalt(salt);
        }
        user.setEmail(userRequest.getEmail());
        user.getUserDetail().setName(userRequest.getName());
        user.getUserDetail().setSurname(userRequest.getSurname());
        user.getUserDetail().setPhone(userRequest.getPhone());
    }

    private static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
