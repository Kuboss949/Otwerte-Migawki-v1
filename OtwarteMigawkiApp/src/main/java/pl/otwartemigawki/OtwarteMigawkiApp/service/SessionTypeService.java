package pl.otwartemigawki.OtwarteMigawkiApp.service;

import pl.otwartemigawki.OtwarteMigawkiApp.dto.AddSessionTypeRequestDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.SessionTypeDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.SessionTypeDatesDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.SessionType;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserSession;

import java.io.IOException;
import java.util.List;

public interface SessionTypeService {
    List<SessionTypeDTO> getAllSessionTypes();
    List<SessionTypeDTO> getAllEnabledSessionTypes();
    void addSessionType(AddSessionTypeRequestDTO request) throws IOException;
    void deleteSessionType(Long sessionTypeId);
    SessionType getSessionTypeByName(String sessionTypeName);
    List<SessionTypeDatesDTO> getAllSessionTypesWithDates();
    void toggleDisableSessionState(Integer id);
}
