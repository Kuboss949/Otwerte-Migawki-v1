package pl.otwartemigawki.OtwarteMigawkiApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.otwartemigawki.OtwarteMigawkiApp.model.SessionType;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.SessionTypeRepository;

import java.util.List;
@Service
public class SessionServiceImpl implements SessionService {

    private final SessionTypeRepository sessionTypeRepository;
    @Autowired
    public SessionServiceImpl(SessionTypeRepository sessionTypeRepository) {
        this.sessionTypeRepository = sessionTypeRepository;
    }

    @Override
    public List<SessionType> getAllSessionTypes() {
        return sessionTypeRepository.findAll();
    }

    @Override
    public void addSessionType(SessionType sessionType) {
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
