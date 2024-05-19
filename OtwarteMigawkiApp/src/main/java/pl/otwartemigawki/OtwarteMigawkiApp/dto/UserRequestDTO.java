package pl.otwartemigawki.OtwarteMigawkiApp.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
    private Integer id = 0;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String phone;
    private boolean isTmp;
}
