package pl.otwartemigawki.OtwarteMigawkiApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDTO {

    private String message;
    private boolean success;
}
