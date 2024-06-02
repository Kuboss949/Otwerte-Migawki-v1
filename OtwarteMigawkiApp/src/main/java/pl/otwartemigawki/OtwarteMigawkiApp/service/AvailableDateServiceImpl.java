package pl.otwartemigawki.OtwarteMigawkiApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.AvailableDateDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.AvailableDate;
import pl.otwartemigawki.OtwarteMigawkiApp.model.SessionType;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.AvailableDateRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.SessionTypeRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.TimeRepository;

import java.time.LocalDate;

@Service
public class AvailableDateServiceImpl implements AvailableDateService {
    private final AvailableDateRepository availableDateRepository;
    private final TimeRepository timeRepository;
    @Autowired
    public AvailableDateServiceImpl(SessionTypeRepository sessionTypeRepository,
                                    AvailableDateRepository availableDateRepository, TimeRepository timeRepository) {
        this.availableDateRepository = availableDateRepository;
        this.timeRepository = timeRepository;
    }


    @Override
    public AvailableDate createAvailableDate(SessionType sessionType, AvailableDateDTO availableDateDTO) {
        LocalDate date = availableDateDTO.getDate();
        AvailableDate availableDate = availableDateRepository.findByDateAndIdSessionType(date, sessionType);
        if (availableDate != null) {
            availableDateRepository.delete(availableDate);
        }
        AvailableDate newAvailableDate = new AvailableDate();
        newAvailableDate.setDate(date);
        newAvailableDate.setIdSessionType(sessionType);
        return availableDateRepository.save(newAvailableDate);
    }


    @Override
    public void deleteAvailableDate(AvailableDate availableDate) {
            availableDateRepository.delete(availableDate);
    }
}
