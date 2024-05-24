package pl.otwartemigawki.OtwarteMigawkiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.otwartemigawki.OtwarteMigawkiApp.model.Gallery;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserSession;

import java.time.LocalDate;
import java.util.List;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    @Query("SELECT gallery FROM Gallery gallery JOIN  gallery.userSession us JOIN us.idUser u where u.id = :userId")
    List<Gallery> findAllGalleriesByUserId(@Param("userId") Integer id);

}
