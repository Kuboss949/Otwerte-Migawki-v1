package pl.otwartemigawki.OtwarteMigawkiApp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UserSessionDTO {
    private Instant date;
    private String sessionTypeName;
    private String coverImagePath;

}
