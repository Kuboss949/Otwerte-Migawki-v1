package pl.otwartemigawki.OtwarteMigawkiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.UserSessionDetailsDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT NEW pl.otwartemigawki.OtwarteMigawkiApp.dto.UserSessionDetailsDTO(" +
            "u.userDetailData.name, " +
            "u.userDetailData.surname, " +
            "u.userDetailData.phone, " +
            "u.email, " +
            "us.id, " +
            "st.sessionTypeName, " +
            "us.date) " +
            "FROM UserSession us " +
            "JOIN us.idUser u " +
            "JOIN us.idSessionType st " +
            "WHERE us.idGallery IS NULL")
    List<UserSessionDetailsDTO> findSessionsWithoutGalleries();

    Optional<User> findByEmail(String email);

}
