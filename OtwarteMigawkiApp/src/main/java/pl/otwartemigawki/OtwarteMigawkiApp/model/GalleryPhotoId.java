package pl.otwartemigawki.OtwarteMigawkiApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class GalleryPhotoId implements Serializable {
    private static final long serialVersionUID = -6814860248669489618L;
    @Column(name = "id_gallery", nullable = false)
    private Integer idGallery;

    @Column(name = "id_photo", nullable = false)
    private Integer idPhoto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GalleryPhotoId entity = (GalleryPhotoId) o;
        return Objects.equals(this.idGallery, entity.idGallery) &&
                Objects.equals(this.idPhoto, entity.idPhoto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGallery, idPhoto);
    }

}