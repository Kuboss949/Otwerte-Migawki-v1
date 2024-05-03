package pl.otwartemigawki.OtwarteMigawkiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.model.AvailableDate;
import pl.otwartemigawki.OtwarteMigawkiApp.model.SessionType;

import java.time.LocalDate;

public interface AvailableDateRepository extends JpaRepository<AvailableDate, Integer> {
AvailableDate findAvailableDateByDate(LocalDate date);
    AvailableDate findByDateAndIdSessionType(LocalDate date, SessionType idSessionType);
}