package pl.otwartemigawki.OtwarteMigawkiApp.service;

import pl.otwartemigawki.OtwarteMigawkiApp.model.AvailableDate;
import pl.otwartemigawki.OtwarteMigawkiApp.model.Time;

import java.time.LocalDate;

public interface TimeService {
    void saveTimeIfNotExists(AvailableDate availableDate, int hour);

    void deleteTime(Time time);
}
