package pl.otwartemigawki.OtwarteMigawkiApp.util;

import pl.otwartemigawki.OtwarteMigawkiApp.dto.GalleryOverviewDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.GalleryPhotoDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.Gallery;
import pl.otwartemigawki.OtwarteMigawkiApp.model.GalleryPhoto;

import java.util.Iterator;
import java.util.Set;

public class GalleryMapper {
    public static GalleryOverviewDTO mapToGalleryOverview(Gallery gallery){
        GalleryOverviewDTO dto = new GalleryOverviewDTO();
        dto.setGalleryId(gallery.getId());
        dto.setGalleryName(gallery.getGalleryName());
        dto.setDate(gallery.getUserSession().getDate());
        Set<GalleryPhoto> galleryPhotos = gallery.getGalleryPhotos();
        Iterator<GalleryPhoto> iterator = galleryPhotos.iterator();
        if (iterator.hasNext()) {
            GalleryPhoto coverPhoto = iterator.next();
            dto.setCoverPhoto(coverPhoto.getPath());
        }
        return dto;
    }

    public static GalleryPhotoDTO mapToPhotoDto(GalleryPhoto photo){
        GalleryPhotoDTO dto = new GalleryPhotoDTO();
        dto.setId(photo.getId());
        dto.setSrc(photo.getPath());
        dto.setWidth(440);
        dto.setHeight(440*photo.getHeight()/ photo.getWidth());
        return dto;
    }
}
