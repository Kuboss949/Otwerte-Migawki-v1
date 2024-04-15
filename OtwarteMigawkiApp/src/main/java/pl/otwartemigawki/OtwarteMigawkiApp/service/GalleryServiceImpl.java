package pl.otwartemigawki.OtwarteMigawkiApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.otwartemigawki.OtwarteMigawkiApp.model.Gallery;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.GalleryRepository;

import java.util.List;

public class GalleryServiceImpl implements GalleryService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private GalleryRepository galleryRepository;
    @Override
    public List<Gallery> getAllGalleries() {
        return null;
    }

    @Override
    public Gallery createGallery(Gallery Gallery) {
        return null;
    }

    @Override
    public void deleteGallery(Integer id) {

    }
}
