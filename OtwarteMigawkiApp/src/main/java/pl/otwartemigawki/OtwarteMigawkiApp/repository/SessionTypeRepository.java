package pl.otwartemigawki.OtwarteMigawkiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.model.SessionType;

public interface SessionTypeRepository extends JpaRepository<SessionType, Long> {
    SessionType findBySessionTypeName(String sessionTypeName);
}
