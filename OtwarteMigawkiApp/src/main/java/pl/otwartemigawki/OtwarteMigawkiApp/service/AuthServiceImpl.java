package pl.otwartemigawki.OtwarteMigawkiApp.service;

import exceptions.ApplicationException;
import exceptions.RegistrationException;
import exceptions.UserNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.AuthResponseDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.LoginRequestDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.UserRequestDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserDetailData;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.UserDetailRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.UserRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.util.UserUtil;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;

    public AuthServiceImpl(UserRepository userRepository, UserDetailRepository userDetailRepository, JwtService jwtService, AuthenticationManager authenticationManager, RoleService roleService) {
        this.userRepository = userRepository;
        this.userDetailRepository = userDetailRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.roleService = roleService;
    }

    public void register(UserRequestDTO userRequestDTO){
        try {
            UserUtil.checkRegisterRequest(userRequestDTO, userRepository, userDetailRepository);
            User user = UserUtil.createUserFromDTO(userRequestDTO, roleService);
            UserDetailData userDetailData = UserUtil.createUserDetailFromDTO(userRequestDTO);

            user = userRepository.save(user);
            userDetailData.setIdUser(user);
            userDetailRepository.save(userDetailData);

        } catch(RegistrationException e){
            throw e;
        } catch(Exception e){
            throw new ApplicationException("Internal server error");
        }
    }

    public AuthResponseDTO authenticate(LoginRequestDTO request){
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if(user.isPresent()){
            try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword() + user.get().getSalt()
                    )
            );
            User userObj = user.get();
            String token = jwtService.generateToken(userObj);
            return new AuthResponseDTO(token);
            } catch (AuthenticationException e){
                throw e;
            }
        }
        throw new UserNotFoundException("Niepoprawne dane uwierzytelniające");
    }

    @Override
    public boolean isTokenValid(String token) {
        return Optional.ofNullable(jwtService.extractUserName(token))
                .flatMap(userRepository::findByEmail)
                .map(user -> jwtService.isValid(token, user))
                .orElse(false);
    }

    @Override
    public boolean isAdminToken(String token) {
        return Objects.equals(this.getRole(token), "admin");
    }

    @Override
    public String getRole(String token) {
        try {
            String username = jwtService.extractUserName(token);
            if (username != null) {
                User user = userRepository.findByEmail(username).orElse(null);
                if (user != null) {
                    return user.getIdRole().getRoleName();
                }
            }
            return "visitor";
        } catch (Exception e) {
            return "visitor";
        }
    }


}
