package pl.otwartemigawki.OtwarteMigawkiApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class UpcomingSessionDTO {
    private Integer id;
    private String sessionTypeName;
    private String clientName;
    private String clientSurname;
    private String clientPhone;
    private Instant date;
}
