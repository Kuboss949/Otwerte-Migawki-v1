package pl.otwartemigawki.OtwarteMigawkiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserDetailData;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<UserDetailData, Integer> {
    Optional<UserDetailData> findByPhone(String phone);
}