package pl.otwartemigawki.OtwarteMigawkiApp.util;

import pl.otwartemigawki.OtwarteMigawkiApp.dto.AvailableDateDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.SessionTypeDatesDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.AvailableDate;
import pl.otwartemigawki.OtwarteMigawkiApp.model.SessionType;
import pl.otwartemigawki.OtwarteMigawkiApp.model.Time;

import java.util.ArrayList;
import java.util.List;

public class SessionTypeDatesMapper {
    public static SessionTypeDatesDTO toDto(SessionType sessionType) {
        SessionTypeDatesDTO SessionTypeDatesDTO = new SessionTypeDatesDTO();
        SessionTypeDatesDTO.setSessionTypeName(sessionType.getSessionTypeName());

        List<AvailableDateDTO> availableDateDTOs = new ArrayList<>();
        for (AvailableDate availableDate : sessionType.getAvailableDates()) {
            AvailableDateDTO availableDateDTO = toDto(availableDate);
            availableDateDTOs.add(availableDateDTO);
        }
        SessionTypeDatesDTO.setAvailableDates(availableDateDTOs);

        return SessionTypeDatesDTO;
    }

    public static AvailableDateDTO toDto(AvailableDate availableDate) {
        AvailableDateDTO availableDateDTO = new AvailableDateDTO();
        availableDateDTO.setDate(availableDate.getDate());

        List<String> times = new ArrayList<>();
        for (Time time : availableDate.getTimes()) {
            times.add(String.valueOf(time.getHour())); // Use plain numbers for hours
        }
        availableDateDTO.setTimes(times);

        return availableDateDTO;
    }
}
