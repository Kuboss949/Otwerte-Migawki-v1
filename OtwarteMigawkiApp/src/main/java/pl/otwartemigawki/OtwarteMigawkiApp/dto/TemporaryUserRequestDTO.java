package pl.otwartemigawki.OtwarteMigawkiApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TemporaryUserRequestDTO {
    private String name;
    private String surname;
    private String phone;
}
