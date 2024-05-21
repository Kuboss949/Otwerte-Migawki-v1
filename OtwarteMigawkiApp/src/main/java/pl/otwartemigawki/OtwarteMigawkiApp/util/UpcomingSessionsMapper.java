package pl.otwartemigawki.OtwarteMigawkiApp.util;

import pl.otwartemigawki.OtwarteMigawkiApp.dto.UpcomingSessionDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.SessionType;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserDetailData;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserSession;

import java.util.List;

public class UpcomingSessionsMapper {

    public static List<UpcomingSessionDTO> mapUserSessionsToDTOList(List<UserSession> list){
        List <UpcomingSessionDTO> upcoming = list.stream().map(UpcomingSessionsMapper::mapUserSessionToDTO).toList();
        return upcoming;
    }
    private static UpcomingSessionDTO mapUserSessionToDTO(UserSession userSession){
        UserDetailData userDetails = userSession.getIdUser().getUserDetailData();
        SessionType sessionType = userSession.getIdSessionType();
        return new UpcomingSessionDTO(userSession.getId(), sessionType.getSessionTypeName(),
                userDetails.getName(), userDetails.getSurname(),
                userDetails.getPhone(), userSession.getDate());
    }
}
