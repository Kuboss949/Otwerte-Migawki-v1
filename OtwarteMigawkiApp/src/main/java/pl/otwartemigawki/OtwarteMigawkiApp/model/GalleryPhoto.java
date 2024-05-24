package pl.otwartemigawki.OtwarteMigawkiApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pl.otwartemigawki.OtwarteMigawkiApp.model.Gallery;

@Getter
@Setter
@Entity
@Table(name = "gallery_photos")
public class GalleryPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_photo", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_gallery", nullable = false)
    private Gallery idGallery;

    @Column(name = "path")
    private String path;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

}