package pl.otwartemigawki.OtwarteMigawkiApp.controller;

import exceptions.RegistrationException;
import exceptions.UserNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<String> register(@RequestBody UserRequestDTO request){
        try {
            authService.register(request);
            return ResponseEntity.ok("Rejestracja zakończona sukcesem!");
        } catch(RegistrationException e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO request, HttpServletResponse response){
        try {
            AuthResponseDTO token = authService.authenticate(request);
            Cookie cookie = new Cookie("jwtToken", token.getToken());
            cookie.setMaxAge(86400);
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResponseEntity.ok("Logowanie zakończone sukcesem");
        }catch (UserNotFoundException e) {
            return ResponseEntity.ok("Niepoprawne dane logowania!");
        } catch (Exception e) {
            return ResponseEntity.ok("Błąd serwera, prosimy spróbować ponownie za chwilę");
        }
    }

}
