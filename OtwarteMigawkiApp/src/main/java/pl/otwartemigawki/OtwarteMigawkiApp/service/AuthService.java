package pl.otwartemigawki.OtwarteMigawkiApp.service;

import org.springframework.security.authentication.AuthenticationServiceException;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.AuthResponseDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.LoginRequestDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.UserRequestDTO;

public interface AuthService {
    AuthResponseDTO register(UserRequestDTO userRequestDTO);
    AuthResponseDTO authenticate(LoginRequestDTO request);
    boolean isTokenValid(String token);
    boolean isAdminToken(String token);

    String getRole(String cookieValue);
}
