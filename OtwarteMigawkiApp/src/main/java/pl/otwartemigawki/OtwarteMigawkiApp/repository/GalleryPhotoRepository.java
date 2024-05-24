package pl.otwartemigawki.OtwarteMigawkiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.model.GalleryPhoto;

public interface GalleryPhotoRepository extends JpaRepository<GalleryPhoto, Integer> {
}