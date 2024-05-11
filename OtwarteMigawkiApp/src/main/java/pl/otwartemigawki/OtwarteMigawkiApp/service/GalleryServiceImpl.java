package pl.otwartemigawki.OtwarteMigawkiApp.service;

import org.springframework.stereotype.Service;
import pl.otwartemigawki.OtwarteMigawkiApp.model.Gallery;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.GalleryRepository;

import java.util.List;
@Service
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository galleryRepository;

    public GalleryServiceImpl(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }



    @Override
    public List<Gallery> getAllUserGalleries(Integer userId) {
        return null;
    }

    @Override
    public void createGallery(Gallery Gallery) {
        galleryRepository.save(Gallery);
    }

}
