package pl.otwartemigawki.OtwarteMigawkiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.model.SessionType;

import java.util.List;

public interface SessionTypeRepository extends JpaRepository<SessionType, Long> {
    SessionType findBySessionTypeName(String sessionTypeName);
    List<SessionType> findByDisabledFalse();
}
