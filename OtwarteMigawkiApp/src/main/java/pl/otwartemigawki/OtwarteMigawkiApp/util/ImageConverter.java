package pl.otwartemigawki.OtwarteMigawkiApp.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageConverter {

    public static byte[] addWatermark(BufferedImage originalImage) {
        try {
            BufferedImage watermarkImage = ImageIO.read(new File("src/main/resources/watermark/logo.png")); // Ensure the path is correct
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();

            BufferedImage watermarked = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = (Graphics2D) watermarked.getGraphics();

            g2d.drawImage(originalImage, 0, 0, width, height, null);

            int watermarkWidth = watermarkImage.getWidth();
            int watermarkHeight = watermarkImage.getHeight();
            int x = (width - watermarkWidth) / 2;
            int y = (height - watermarkHeight) / 2;
            g2d.drawImage(watermarkImage, x, y, null);
            g2d.dispose();

            return convertToByteArray(watermarked); // Pass the correct image here
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] convertToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        return os.toByteArray(); // Ensure to return the byte array
    }
}
