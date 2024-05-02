package pl.otwartemigawki.OtwarteMigawkiApp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
    private String email;
    private String password;
    private String name;
    private String surname;
    private String phone;
    private boolean isTmp;
}
