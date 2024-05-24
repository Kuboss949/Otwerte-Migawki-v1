package pl.otwartemigawki.OtwarteMigawkiApp.service;

import org.springframework.web.multipart.MultipartFile;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class GoogleCloudStorageServiceImpl implements GoogleCloudStorageService{
    @Autowired
    private Storage storage;

    private final String bucketName = "ow_photos";
    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String blobName = file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, blobName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        Blob blob = storage.create(blobInfo, file.getBytes());
        return blob.getMediaLink();
    }
    @Override
    public byte[] downloadFile(String fileName) {
        Blob blob = storage.get(BlobId.of(bucketName, fileName));
        return blob.getContent();
    }
    @Override
    public boolean deleteFile(String fileName) {
        BlobId blobId = BlobId.of(bucketName, fileName);
        return storage.delete(blobId);
    }
    @Override
    public String uploadFile(byte[] fileData) throws IOException {
        String fileName = UUID.randomUUID().toString() + ".jpeg"; // Generate a random file name
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        Blob blob = storage.create(blobInfo, fileData);
        return blob.getMediaLink();
    }
}
