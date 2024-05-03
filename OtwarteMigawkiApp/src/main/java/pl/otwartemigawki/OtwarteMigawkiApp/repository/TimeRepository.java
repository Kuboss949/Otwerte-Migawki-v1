package pl.otwartemigawki.OtwarteMigawkiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.otwartemigawki.OtwarteMigawkiApp.model.AvailableDate;
import pl.otwartemigawki.OtwarteMigawkiApp.model.Time;

public interface TimeRepository extends JpaRepository<Time, Integer> {
    @Query("SELECT t FROM Time t WHERE t.hour = :hour AND t.idAvailableDate.id = :idAvailableDate")
    Time findByHourAndIdAvailableDate(@Param("hour") Integer hour, @Param("idAvailableDate") Integer idAvailableDate);
}