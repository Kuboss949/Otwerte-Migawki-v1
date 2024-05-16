package pl.otwartemigawki.OtwarteMigawkiApp.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class PasswordChangeRequestDTO {
    private String newPassword;
    private String oldPassword;

}
