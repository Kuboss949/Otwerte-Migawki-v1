package pl.otwartemigawki.OtwarteMigawkiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.model.Gallery;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
}
