package pl.otwartemigawki.OtwarteMigawkiApp.service;

import pl.otwartemigawki.OtwarteMigawkiApp.model.Gallery;

import java.util.List;

public interface GalleryService {

    List<Gallery> getAllGalleries();
    Todo createGallery(Gallery Gallery);
    void deleteGallery(Integer id);
}
