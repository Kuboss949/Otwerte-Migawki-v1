package pl.otwartemigawki.OtwarteMigawkiApp.util;

import org.springframework.web.multipart.MultipartFile;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.AddSessionTypeRequestDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.SessionType;
import pl.otwartemigawki.OtwarteMigawkiApp.service.GoogleCloudStorageService;

import java.io.IOException;

public class SessionTypeReqToObjMapper  {
    static public SessionType mapFromRequest(AddSessionTypeRequestDTO request, GoogleCloudStorageService googleCloudStorageService) throws IOException {

        MultipartFile photo = request.getPhoto();
        String photoUrl = googleCloudStorageService.uploadFile(photo);

        SessionType sessionType = new SessionType();
        sessionType.setSessionTypeName(request.getSessionTypeName());
        sessionType.setDescription(request.getDescription());
        sessionType.setPrice(request.getPrice());
        sessionType.setCoverPhotoPath(photoUrl);

        return sessionType;
    }
}
