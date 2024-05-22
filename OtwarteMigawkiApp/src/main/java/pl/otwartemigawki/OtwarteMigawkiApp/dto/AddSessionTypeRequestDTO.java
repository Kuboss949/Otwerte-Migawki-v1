package pl.otwartemigawki.OtwarteMigawkiApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Data
@Getter
@Setter
@AllArgsConstructor
public class AddSessionTypeRequestDTO {
    private String sessionTypeName;
    private String description;
    private Integer price;
    private MultipartFile photo;
}
