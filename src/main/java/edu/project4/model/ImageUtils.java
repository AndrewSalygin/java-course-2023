package edu.project4.model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import javax.imageio.ImageIO;

public final class ImageUtils {
    private ImageUtils() {
    }

    public static void save(FractalImage image, Path path, ImageFormat format) {
        try {
            if (image == null) {
                throw new IllegalArgumentException("Изображение не было передано");
            }
            if (path == null) {
                throw new IllegalArgumentException("Путь не был передан");
            }
            if (format == null) {
                throw new IllegalArgumentException("Формат не был передан");
            }

            BufferedImage bufferedImage = convertToBufferedImage(image);
            ImageIO.write(bufferedImage, format.name(), path.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static BufferedImage convertToBufferedImage(FractalImage image) {
        BufferedImage bufferedImage = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_RGB);

        Optional<Pixel> pixelOptional;
        Color color;
        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                pixelOptional = image.getPixel(x, y);
                if (pixelOptional.isPresent()) {
                    color =
                        new Color(pixelOptional.get().getR(), pixelOptional.get().getG(), pixelOptional.get().getB());
                    bufferedImage.setRGB(x, y, color.getRGB());
                }
            }
        }
        return bufferedImage;
    }
}
