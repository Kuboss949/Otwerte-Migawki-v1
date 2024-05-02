package pl.otwartemigawki.OtwarteMigawkiApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.otwartemigawki.OtwarteMigawkiApp.model.Gallery;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "gallery_type")
public class GalleryType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gallery_type", nullable = false)
    private Integer id;

    @Column(name = "name_gallery_type", length = 50)
    private String nameGalleryType;

    @OneToMany(mappedBy = "idGalleryType")
    private Set<Gallery> galleries = new LinkedHashSet<>();

}