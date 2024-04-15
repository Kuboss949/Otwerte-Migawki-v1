package pl.otwartemigawki.OtwarteMigawkiApp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateRequest {
    private String name;
    private String surname;
    private String email;
    private String phone;
}
