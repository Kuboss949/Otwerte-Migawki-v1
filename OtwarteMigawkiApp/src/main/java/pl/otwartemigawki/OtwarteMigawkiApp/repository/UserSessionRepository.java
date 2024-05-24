package pl.otwartemigawki.OtwarteMigawkiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.otwartemigawki.OtwarteMigawkiApp.model.AvailableDate;
import pl.otwartemigawki.OtwarteMigawkiApp.model.Gallery;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserSession;

import java.time.LocalDate;
import java.util.List;

public interface UserSessionRepository extends JpaRepository<UserSession, Integer> {
    List<UserSession>findUserSessionsByIdUser(User user);

    @Query("SELECT us FROM UserSession us WHERE us.date >= CURRENT_DATE")
    List<UserSession> findAllUpcomingSessions();

    @Query("SELECT us FROM UserSession us WHERE us.idGallery IS NULL")
    List<UserSession> findAllSessionsWithoutGalleries();

    @Query("SELECT us FROM UserSession us JOIN  us.idUser u where u.id = :userId and us.date >= CURRENT_DATE")
    List<UserSession> findAllUpcomingSessionsForUserById(@Param("userId") Integer id);

}