package pl.otwartemigawki.OtwarteMigawkiApp.controller;

import exceptions.RegistrationException;
import exceptions.UserNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.ApiResponseDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.AuthResponseDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.LoginRequestDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.UserRequestDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "https://localhost:3000")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO> register(@RequestBody UserRequestDTO request){
        try {
            authService.register(request);
            return ResponseEntity.ok(new ApiResponseDTO("Rejestracja zakończona sukcesem!", true));
        } catch(RegistrationException e){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO(e.getMessage(), false));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO> login(@RequestBody LoginRequestDTO request, HttpServletResponse response){
        try {
            AuthResponseDTO token = authService.authenticate(request);
            Cookie cookie = new Cookie("jwtToken", token.getToken());
            cookie.setMaxAge(86400);
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResponseEntity.ok(new ApiResponseDTO("Logowanie zakończone sukcesem", true));
        }catch (UserNotFoundException e) {
            return ResponseEntity.ok(new ApiResponseDTO("Niepoprawne dane logowania!", false));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponseDTO("Błąd serwera, prosimy spróbować ponownie za chwilę", false));
        }
    }

    @GetMapping("/is-authenticated")
    public ResponseEntity<Boolean> isAuthenticated(@CookieValue(name = "jwtToken", defaultValue = "defaultValue") String cookieValue){
        Boolean isAuthenticated = authService.isTokenValid(cookieValue);
        return ResponseEntity.ok(isAuthenticated);
    }

    @GetMapping("/is-admin")
    public ResponseEntity<Boolean> isAdmin(@CookieValue(name = "jwtToken", defaultValue = "defaultValue") String cookieValue){
        return ResponseEntity.ok(authService.isAdminToken(cookieValue));
    }
    @GetMapping("/get-role")
    public ResponseEntity<String> getUserRole(@CookieValue(name = "jwtToken", defaultValue = "defaultValue") String cookieValue){
        return ResponseEntity.ok(authService.getRole(cookieValue));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDTO> logoutUser(HttpServletResponse response){
        try{
            Cookie cookie = new Cookie("jwtToken", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResponseEntity.ok( new ApiResponseDTO("Wylogowano pomyślnie", true));
        } catch (Exception e){
            return ResponseEntity.ok( new ApiResponseDTO("Coś poszło nie tak", false));
        }
    }

}
