package pl.otwartemigawki.OtwarteMigawkiApp.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
public class AvailableDateDTO {
    private LocalDate date;
    private List<String> times;
}
