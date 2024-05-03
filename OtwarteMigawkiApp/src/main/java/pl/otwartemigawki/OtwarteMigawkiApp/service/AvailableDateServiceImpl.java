package pl.otwartemigawki.OtwarteMigawkiApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.AvailableDateDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.AvailableDate;
import pl.otwartemigawki.OtwarteMigawkiApp.model.SessionType;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.AvailableDateRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.SessionTypeRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvailableDateServiceImpl implements AvailableDateService {
    private final AvailableDateRepository availableDateRepository;
    @Autowired
    public AvailableDateServiceImpl(SessionTypeRepository sessionTypeRepository,
                                    AvailableDateRepository availableDateRepository) {
        this.availableDateRepository = availableDateRepository;
    }

    @Override
    public AvailableDate saveAvailableDate(AvailableDate availableDate) {
        return availableDateRepository.save(availableDate);
    }

    @Override
    public AvailableDate getAvailableDateByDate(LocalDate date) {
        return availableDateRepository.findAvailableDateByDate(date);
    }

    @Override
    public AvailableDate getOrCreateAvailableDate(SessionType sessionType, AvailableDateDTO availableDateDTO) {
        LocalDate date = availableDateDTO.getDate();
        AvailableDate availableDate = availableDateRepository.findByDateAndIdSessionType(date, sessionType);
        if (availableDate == null) {
            availableDate = new AvailableDate();
            availableDate.setDate(date);
            availableDate.setIdSessionType(sessionType);
            availableDate = availableDateRepository.save(availableDate);
        }
        return availableDate;
    }
}
