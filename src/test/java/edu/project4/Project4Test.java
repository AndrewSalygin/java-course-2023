package edu.project4;

import edu.project4.model.FractalImage;
import edu.project4.model.ImageFormat;
import edu.project4.model.ImageUtils;
import edu.project4.model.Pixel;
import edu.project4.model.Rect;
import edu.project4.processors.GammaCorrectionImageProcessor;
import edu.project4.renderers.ConsistentFractalRenderer;
import edu.project4.renderers.ParallelFractalRenderer;
import edu.project4.transformations.HyberbolicTransformation;
import edu.project4.transformations.SpiralTransform;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Project4Test {

    @Test
    @DisplayName("Отрисовка (Parallel)")
    void renderParallelTest() {
        assertDoesNotThrow(() -> new ParallelFractalRenderer(4).render(
            FractalImage.create(1920, 1080),
            new Rect(-4, -3, 8, 6),
            List.of(
                new HyberbolicTransformation(),
                new SpiralTransform()
            ),
            4,
            1000,
            5
        ));
    }

    @Test
    @DisplayName("Отрисовка (Consistent)")
    void renderConsistentTest() {
        assertDoesNotThrow(() -> new ConsistentFractalRenderer().render(
            FractalImage.create(1920, 1080),
            new Rect(-4, -3, 8, 6),
            List.of(
                new HyberbolicTransformation(),
                new SpiralTransform()
            ),
            4,
            1000,
            5
        ));
    }

    @Test
    @DisplayName("Сохранение файла")
    void saveTest() {
        Path path = Path.of("src/main/resources/project4/file.png");
        assertDoesNotThrow(() -> ImageUtils.save(
            FractalImage.create(1920, 1080),
            path,
            ImageFormat.PNG));
        assertTrue(Files.exists(path));
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Создание изображения без исключений (Parallel)")
    void fractalFlameGeneratorParallelTest() {
        Path path = Path.of("src/main/resources/project4/file.png");
        FractalFlameGenerator generator =
            new FractalFlameGenerator(
                1920,
                1080,
                new ParallelFractalRenderer(4),
                new Rect(-4, -3, 8, 6),
                List.of(
                    new HyberbolicTransformation(),
                    new SpiralTransform()
                ),
                new GammaCorrectionImageProcessor()
            );
        assertDoesNotThrow(() -> generator.generateFractalFlame(
            4,
            1000,
            5,
            path,
            ImageFormat.PNG
        ));
        assertTrue(Files.exists(path));
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Создание изображения без исключений (Consistent)")
    void fractalFlameGeneratorConsistentTest() {
        Path path = Path.of("src/main/resources/project4/file.png");
        FractalFlameGenerator generator =
            new FractalFlameGenerator(
                1920,
                1080,
                new ConsistentFractalRenderer(),
                new Rect(-4, -3, 8, 6),
                List.of(
                    new HyberbolicTransformation(),
                    new SpiralTransform()
                ),
                new GammaCorrectionImageProcessor()
            );
        assertDoesNotThrow(() -> generator.generateFractalFlame(
            4,
            1000,
            5,
            path,
            ImageFormat.PNG
        ));
        assertTrue(Files.exists(path));
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
