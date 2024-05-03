package pl.otwartemigawki.OtwarteMigawkiApp.util;

import pl.otwartemigawki.OtwarteMigawkiApp.dto.SessionTypeDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.SessionType;

public class SessionUtil {
    public static SessionTypeDTO convertToDTO(SessionType type) {
        return new SessionTypeDTO(type.getId(), type.getSessionTypeName(), type.getDescription(), type.getPrice());
    }

    public static SessionType createSessionTypeFromDTO(SessionTypeDTO sessionTypeDTO){
        SessionType type = new SessionType();
        type.setSessionTypeName(sessionTypeDTO.getSessionTypeName());
        type.setPrice(sessionTypeDTO.getPrice());
        type.setDescription(sessionTypeDTO.getDescription());
        return type;
    }
}
