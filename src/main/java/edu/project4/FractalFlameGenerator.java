package edu.project4;

import edu.project4.model.FractalImage;
import edu.project4.model.ImageFormat;
import edu.project4.model.ImageUtils;
import edu.project4.model.Rect;
import edu.project4.processors.ImageProcessor;
import edu.project4.renderers.Renderer;
import edu.project4.transformations.Transformation;
import java.nio.file.Path;
import java.util.List;

public class FractalFlameGenerator {
    private final Renderer renderer;
    private final FractalImage canvas;
    private final Rect world;
    private final List<Transformation> variations;
    private final ImageProcessor imageProcessor;

    public FractalFlameGenerator(
        int width,
        int height,
        Renderer renderer,
        Rect world,
        List<Transformation> variations,
        ImageProcessor imageProcessor
    ) {
        this.canvas = FractalImage.create(width, height);
        this.renderer = renderer;
        this.world = world;
        this.variations = variations;
        this.imageProcessor = imageProcessor;
    }

    public void generateFractalFlame(
        int samples,
        int iterPerSample,
        int symmetry,
        Path path,
        ImageFormat imageFormat
    ) {
        if (renderer == null) {
            throw new IllegalArgumentException("Не указан рендер");
        }
        if (world == null) {
            throw new IllegalArgumentException("Не указана система координат рисунка");
        }
        if (variations == null || variations.isEmpty()) {
            throw new IllegalArgumentException("Не указаны преобразования");
        }
        if (imageProcessor == null) {
            throw new IllegalArgumentException("Не указан обработчик изображения");
        }
        renderer.render(canvas, world, variations, samples, iterPerSample, symmetry);
        imageProcessor.process(canvas);
        ImageUtils.save(canvas, path, imageFormat);
    }
}
