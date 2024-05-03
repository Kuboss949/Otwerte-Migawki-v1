package pl.otwartemigawki.OtwarteMigawkiApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.otwartemigawki.OtwarteMigawkiApp.model.AvailableDate;
import pl.otwartemigawki.OtwarteMigawkiApp.model.Time;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.AvailableDateRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.SessionTypeRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.TimeRepository;

import java.time.LocalDate;

@Service
public class TimeServiceImpl implements TimeService {

    private final TimeRepository timeRepository;

    @Autowired
    public TimeServiceImpl(SessionTypeRepository sessionTypeRepository,
                           AvailableDateRepository availableDateRepository,
                           TimeRepository timeRepository) {

        this.timeRepository = timeRepository;
    }



    @Override
    public void saveTime(Time time) {
        timeRepository.save(time);
    }

    @Override
    public Time getTimeByHourAndAvailableDate(Integer hour, Integer availableDateId) {
        return timeRepository.findByHourAndIdAvailableDate(hour, availableDateId);
    }

    @Override
    public void saveTimeIfNotExists(AvailableDate availableDate, int hour) {
        Time existingTime = timeRepository.findByHourAndIdAvailableDate(hour, availableDate.getId());
        if (existingTime == null) {
            Time timeEntity = new Time();
            timeEntity.setHour(hour);
            timeEntity.setIdAvailableDate(availableDate);
            timeRepository.save(timeEntity);
        }
    }
}
