package pl.otwartemigawki.OtwarteMigawkiApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.AddSessionTypeRequestDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.SessionTypeDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.SessionTypeDatesDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.SessionType;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.SessionTypeRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.util.SessionMapper;
import pl.otwartemigawki.OtwarteMigawkiApp.util.SessionTypeDatesMapper;
import pl.otwartemigawki.OtwarteMigawkiApp.util.SessionTypeReqToObjMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SessionTypeServiceImpl implements SessionTypeService {

    private final SessionTypeRepository sessionTypeRepository;
    private final GoogleCloudStorageService googleCloudStorageService;

    @Autowired
    public SessionTypeServiceImpl(SessionTypeRepository sessionTypeRepository, GoogleCloudStorageService googleCloudStorageService) {
        this.sessionTypeRepository = sessionTypeRepository;
        this.googleCloudStorageService = googleCloudStorageService;
    }

    @Override
    public List<SessionTypeDTO> getAllSessionTypes() {
        return sessionTypeRepository.findAll().stream().map(SessionMapper::mapToSessionTypeDTO).toList();
    }

    @Override
    public List<SessionTypeDTO> getAllEnabledSessionTypes() {
        return sessionTypeRepository.findByDisabledFalse().stream().map(SessionMapper::mapToSessionTypeDTO).toList();
    }

    @Override
    public void addSessionType(AddSessionTypeRequestDTO request) throws IOException {
        SessionType sessionType = SessionTypeReqToObjMapper.mapFromRequest(request, googleCloudStorageService);
        sessionTypeRepository.save(sessionType);
    }

    @Override
    public void deleteSessionType(Long sessionTypeId) {
        sessionTypeRepository.deleteById(sessionTypeId);
    }

    @Override
    public SessionType getSessionTypeByName(String sessionTypeName) {
        return sessionTypeRepository.findBySessionTypeName(sessionTypeName);
    }

    @Override
    public List<SessionTypeDatesDTO> getAllSessionTypesWithDates() {
        List<SessionType> sessionTypes = sessionTypeRepository.findByDisabledFalse();
        List<SessionTypeDatesDTO> response = new ArrayList<>();
        for (SessionType sessionType : sessionTypes) {
            SessionTypeDatesDTO sessionTypeDatesDTO = SessionTypeDatesMapper.toDto(sessionType);
            response.add(sessionTypeDatesDTO);
        }
        return response;
    }

    @Override
    public void toggleDisableSessionState(Integer id) {
        Optional<SessionType> sessionType = sessionTypeRepository.findById(Long.valueOf(id));
        if(sessionType.isPresent()){
            Boolean oldState = sessionType.get().getDisabled();
            sessionType.get().setDisabled(!oldState);
            sessionTypeRepository.save(sessionType.get());
        }
    }

}
