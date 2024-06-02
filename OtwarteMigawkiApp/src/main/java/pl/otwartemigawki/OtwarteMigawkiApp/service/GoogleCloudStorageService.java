package pl.otwartemigawki.OtwarteMigawkiApp.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GoogleCloudStorageService {
    String uploadFile(MultipartFile file) throws IOException;
    String uploadFile(byte[] fileData) throws IOException;
}
