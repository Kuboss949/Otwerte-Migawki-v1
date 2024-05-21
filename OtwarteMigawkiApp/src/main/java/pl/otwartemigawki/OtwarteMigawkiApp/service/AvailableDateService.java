package pl.otwartemigawki.OtwarteMigawkiApp.service;

import org.springframework.cglib.core.Local;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.AvailableDateDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.AvailableDate;
import pl.otwartemigawki.OtwarteMigawkiApp.model.SessionType;

import java.time.LocalDate;

public interface AvailableDateService {
    AvailableDate saveAvailableDate(AvailableDate availableDate);
    AvailableDate getAvailableDateByDate(LocalDate date);

    public AvailableDate createAvailableDate(SessionType sessionType, AvailableDateDTO availableDateDTO);
    AvailableDate getAvailableDateByDateAndTime(LocalDate date, Integer time);
    void deleteAvailableDate(AvailableDate availableDate);

}
