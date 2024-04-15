package pl.otwartemigawki.OtwarteMigawkiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
