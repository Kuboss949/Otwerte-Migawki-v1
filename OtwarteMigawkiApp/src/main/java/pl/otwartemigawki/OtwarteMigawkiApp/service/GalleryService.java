package pl.otwartemigawki.OtwarteMigawkiApp.service;

import pl.otwartemigawki.OtwarteMigawkiApp.model.Gallery;

import java.util.List;

public interface GalleryService {

    List<Gallery> getAllUserGalleries(Integer userId);
    void createGallery(Gallery Gallery);
}
