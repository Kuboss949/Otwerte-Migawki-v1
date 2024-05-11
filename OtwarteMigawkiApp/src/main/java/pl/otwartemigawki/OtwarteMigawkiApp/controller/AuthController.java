package pl.otwartemigawki.OtwarteMigawkiApp.controller;

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
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody UserRequestDTO request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody LoginRequestDTO request, HttpServletResponse response){
        try {
            AuthResponseDTO token = authService.authenticate(request);
            if (token != null) {
                Cookie cookie = new Cookie("jwtToken", token.getToken());
                cookie.setMaxAge(86400);
                cookie.setSecure(true);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                response.addCookie(cookie);
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
