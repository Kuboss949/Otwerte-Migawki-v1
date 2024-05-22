package pl.otwartemigawki.OtwarteMigawkiApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.AddSessionTypeRequestDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.SessionType;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.SessionTypeRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.util.SessionTypeReqToObjMapper;

import java.io.IOException;
import java.util.List;
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
    public List<SessionType> getAllSessionTypes() {
        return sessionTypeRepository.findAll();
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


}
