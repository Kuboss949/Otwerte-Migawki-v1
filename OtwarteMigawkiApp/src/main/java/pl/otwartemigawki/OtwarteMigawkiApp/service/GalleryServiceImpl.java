package pl.otwartemigawki.OtwarteMigawkiApp.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.AddGalleryRequestDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.GalleryOverviewDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.Gallery;
import pl.otwartemigawki.OtwarteMigawkiApp.model.GalleryPhoto;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserSession;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.GalleryPhotoRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.GalleryRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.UserSessionRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.util.GalleryMapper;
import pl.otwartemigawki.OtwarteMigawkiApp.util.ImageConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@Service
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository galleryRepository;
    private final UserSessionRepository userSessionRepository;
    private final GalleryPhotoRepository galleryPhotoRepository;
    private final GoogleCloudStorageService storageService;

    public GalleryServiceImpl(GalleryRepository galleryRepository, UserSessionRepository userSessionRepository, GalleryPhotoRepository galleryPhotoRepository, GoogleCloudStorageService storageService) {
        this.galleryRepository = galleryRepository;
        this.userSessionRepository = userSessionRepository;
        this.galleryPhotoRepository = galleryPhotoRepository;
        this.storageService = storageService;
    }

    @Override
    public List<GalleryOverviewDTO> getAllUserGalleries(Integer userId) {
        return galleryRepository.findAllGalleriesByUserId(userId).stream()
                .map(GalleryMapper::mapToGalleryOverview)
                .toList();
    }

    @Override
    @Transactional
    public void createGallery(AddGalleryRequestDTO request, UserSession userSession) throws IOException {
        Gallery gallery = new Gallery();
        gallery.setGalleryName(request.getGalleryName());
        gallery = galleryRepository.save(gallery);

        List<MultipartFile> files = request.getFiles();
        for (MultipartFile file : files) {
            BufferedImage fileImage = ImageIO.read(file.getInputStream());
            byte[] photoData = ImageConverter.addWatermark(fileImage);

            GalleryPhoto photo = new GalleryPhoto();
            String path = storageService.uploadFile(photoData);
            photo.setPath(path);
            photo.setWidth(fileImage.getWidth());
            photo.setHeight(fileImage.getHeight());
            photo.setIdGallery(gallery);
            galleryPhotoRepository.save(photo);

            gallery.getGalleryPhotos().add(photo);
        }


        userSession.setIdGallery(gallery);
        userSessionRepository.save(userSession);
    }


}
