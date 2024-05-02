package pl.otwartemigawki.OtwarteMigawkiApp.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWithDetailsDTO {
    private Integer id;
    private String email;
    private String password = null;
    private String salt = null;
    private Integer idRoleId; // Assuming you want to include the role ID
    private Boolean isTmp;
    private String name;
    private String surname;
    private String phone;
}
