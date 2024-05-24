package pl.otwartemigawki.OtwarteMigawkiApp.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Data
@Getter
@Setter
public class AddGalleryRequestDTO {
    private Integer sessionId;
    private String galleryName;
    private List<MultipartFile> files;
}
