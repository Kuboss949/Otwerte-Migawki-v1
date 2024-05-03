package pl.otwartemigawki.OtwarteMigawkiApp.service;

import pl.otwartemigawki.OtwarteMigawkiApp.model.AvailableDate;
import pl.otwartemigawki.OtwarteMigawkiApp.model.Time;

import java.time.LocalDate;

public interface TimeService {
    void saveTime(Time time);
    Time getTimeByHourAndAvailableDate(Integer hour, Integer availableDateId);
    void saveTimeIfNotExists(AvailableDate availableDate, int hour);
}
