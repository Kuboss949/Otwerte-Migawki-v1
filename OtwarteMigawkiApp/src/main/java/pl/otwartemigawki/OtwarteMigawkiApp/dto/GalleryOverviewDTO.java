package pl.otwartemigawki.OtwarteMigawkiApp.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Data
@Getter
@Setter
public class GalleryOverviewDTO {
    private Integer galleryId;
    private String galleryName;
    private Instant date;
    private String coverPhoto;
}
