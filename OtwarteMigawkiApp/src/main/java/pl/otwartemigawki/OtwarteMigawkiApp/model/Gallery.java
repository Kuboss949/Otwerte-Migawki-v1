package pl.otwartemigawki.OtwarteMigawkiApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "gallery")
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gallery", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_session")
    private UserSession idSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_gallery_type")
    private GalleryType idGalleryType;

    @Column(name = "gallery_name", length = 100)
    private String galleryName;

    @OneToMany(mappedBy = "gallery")
    private Set<GalleryPhoto> galleryPhotos = new LinkedHashSet<>();

}