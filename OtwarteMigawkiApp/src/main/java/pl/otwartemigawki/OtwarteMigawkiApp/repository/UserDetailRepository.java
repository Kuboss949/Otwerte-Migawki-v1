package pl.otwartemigawki.OtwarteMigawkiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserDetailData;

public interface UserDetailRepository extends JpaRepository<UserDetailData, Integer> {
}