package pl.otwartemigawki.OtwarteMigawkiApp.util;

import pl.otwartemigawki.OtwarteMigawkiApp.dto.UpcomingSessionDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.UserSessionDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.SessionTypeDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.Gallery;
import pl.otwartemigawki.OtwarteMigawkiApp.model.SessionType;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserDetailData;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserSession;

public class SessionMapper {
    public static SessionTypeDTO mapToSessionTypeDTO(SessionType type) {
        return new SessionTypeDTO(type.getId(), type.getSessionTypeName(),
                type.getDescription(), type.getPrice(), type.getCoverPhotoPath(),
                type.getDisabled());
    }

    public static SessionType createSessionTypeFromDTO(SessionTypeDTO sessionTypeDTO){
        SessionType type = new SessionType();
        type.setSessionTypeName(sessionTypeDTO.getSessionTypeName());
        type.setPrice(sessionTypeDTO.getPrice());
        type.setDescription(sessionTypeDTO.getDescription());
        return type;
    }

    public static UserSessionDTO mapToDto(UserSession userSession){
        UserSessionDTO dto = new UserSessionDTO();
        Gallery gallery = userSession.getIdGallery();
        SessionType sessionType = userSession.getIdSessionType();
        dto.setDate(userSession.getDate());
        dto.setSessionTypeName(sessionType.getSessionTypeName());
        String coverPath = gallery != null ? gallery.getGalleryPhotos().iterator().next().getPath() : "";
        dto.setCoverImagePath(coverPath);
        return dto;
    }


    public static UpcomingSessionDTO mapUserSessionToUpcomingSessionDTO(UserSession userSession){
        UserDetailData userDetails = userSession.getIdUser().getUserDetailData();
        SessionType sessionType = userSession.getIdSessionType();
        return new UpcomingSessionDTO(userSession.getId(), sessionType.getSessionTypeName(),
                userDetails.getName(), userDetails.getSurname(),
                userDetails.getPhone(), userSession.getDate());
    }
}
