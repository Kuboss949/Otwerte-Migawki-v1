package pl.otwartemigawki.OtwarteMigawkiApp.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.UserRequestDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.UserWithDetailsDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.Role;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserDetailData;
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

    public static UserDetailData createUserDetailFromDTO(UserRequestDTO userRequestDTO){
        UserDetailData userDetailData = new UserDetailData();
        userDetailData.setName(userRequestDTO.getName());
        userDetailData.setSurname(userRequestDTO.getSurname());
        userDetailData.setPhone(userRequestDTO.getPhone());
        return userDetailData;
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
        dto.setName(user.getUserDetailData().getName());
        dto.setSurname(user.getUserDetailData().getSurname());
        dto.setPhone(user.getUserDetailData().getPhone());
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
        user.getUserDetailData().setName(userRequest.getName());
        user.getUserDetailData().setSurname(userRequest.getSurname());
        user.getUserDetailData().setPhone(userRequest.getPhone());
    }

    private static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
