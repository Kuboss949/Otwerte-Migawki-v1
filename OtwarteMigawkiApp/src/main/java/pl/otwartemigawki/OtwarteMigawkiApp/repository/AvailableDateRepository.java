package pl.otwartemigawki.OtwarteMigawkiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.otwartemigawki.OtwarteMigawkiApp.model.AvailableDate;
import pl.otwartemigawki.OtwarteMigawkiApp.model.SessionType;

import java.time.LocalDate;

public interface AvailableDateRepository extends JpaRepository<AvailableDate, Integer> {
    AvailableDate findByDateAndIdSessionType(LocalDate date, SessionType idSessionType);
}