package pl.otwartemigawki.OtwarteMigawkiApp.dto;

public class LoginResponseDTO {
    private String token;
    private String username;

    public LoginResponseDTO(String token, String username) {
        this.token = token;
        this.username = username;
    }
}
