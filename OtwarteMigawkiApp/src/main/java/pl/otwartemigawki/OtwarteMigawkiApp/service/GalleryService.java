package pl.otwartemigawki.OtwarteMigawkiApp.service;

import pl.otwartemigawki.OtwarteMigawkiApp.dto.AddGalleryRequestDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.GalleryOverviewDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.Gallery;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserSession;

import java.io.IOException;
import java.util.List;

public interface GalleryService {

    List<GalleryOverviewDTO> getAllUserGalleries(Integer userId);
    void createGallery(AddGalleryRequestDTO request, UserSession userSession) throws IOException;
}
