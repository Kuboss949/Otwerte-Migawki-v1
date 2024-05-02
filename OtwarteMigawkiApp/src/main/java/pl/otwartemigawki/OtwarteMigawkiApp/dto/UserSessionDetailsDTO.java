package pl.otwartemigawki.OtwarteMigawkiApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class UserSessionDetailsDTO {
    private String name;
    private String surname;
    private String phone;
    private String email;
    private Integer id;
    private String sessionTypeName;
    private Instant date;
}
