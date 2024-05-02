package pl.otwartemigawki.OtwarteMigawkiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.UserSessionDetailsDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT NEW pl.otwartemigawki.OtwarteMigawkiApp.dto.UserSessionDetailsDTO(" +
            "u.userDetail.name, " +
            "u.userDetail.surname, " +
            "u.userDetail.phone, " +
            "u.email, " +
            "us.id, " +
            "st.sessionTypeName, " +
            "us.date) " +
            "FROM UserSession us " +
            "JOIN us.idUser u " +
            "JOIN us.idSessionType st " +
            "WHERE us.galleries IS EMPTY")
    List<UserSessionDetailsDTO> findSessionsWithoutGalleries();
}
