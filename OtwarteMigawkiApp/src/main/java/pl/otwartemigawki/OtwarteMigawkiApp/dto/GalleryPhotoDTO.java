package pl.otwartemigawki.OtwarteMigawkiApp.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class GalleryPhotoDTO {
    private Integer id;
    private String src;
    private Integer width;
    private Integer height;
}
