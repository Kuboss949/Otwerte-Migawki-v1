package pl.otwartemigawki.OtwarteMigawkiApp.dto;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SessionTypeDatesDTO {
    private String sessionTypeName;
    private List<AvailableDateDTO> availableDates;
}
