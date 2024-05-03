package pl.otwartemigawki.OtwarteMigawkiApp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReservationRequestDTO {
    private Long id;
    private LocalDate date;
    private Integer hour;
    private String sessionTypeName;
    private String name = null;
    private String surname = null;
    private String phone = null;

    public TemporaryUserRequestDTO getTemporaryUserInfo(){
        return new TemporaryUserRequestDTO(this.name, this.surname, this.phone);
    }
    public boolean isRegistered(){
        return id > 0;
    }
}
