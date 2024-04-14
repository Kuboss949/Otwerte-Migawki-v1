package pl.otwartemigawki.OtwarteMigawkiApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.otwartemigawki.OtwarteMigawkiApp.Gallery;

@Getter
@Setter
@Entity
@Table(name = "gallery_photos")
public class GalleryPhoto {
    @EmbeddedId
    private GalleryPhotoId id;

    @MapsId("idGallery")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_gallery", nullable = false)
    private Gallery idGallery;

    @Column(name = "path")
    private String path;

}