package edu.project4.renderers;

import edu.project4.model.AffineTransformation;
import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import edu.project4.model.Point;
import edu.project4.model.Rect;
import edu.project4.transformations.Transformation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class ParallelFractalRenderer extends AbstractRenderer {

    private final int countOfFlows;

    public ParallelFractalRenderer(int countOfFlows) {
        this.countOfFlows = countOfFlows;
    }

    @Override
    public FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        int samples,
        int iterPerSample,
        int symmetry
    ) {
        ExecutorService executorService = Executors.newFixedThreadPool(countOfFlows);
        AffineTransformation[] transformations = AffineTransformation.getRandomTransformations(samples);
        List<Callable<Void>> tasks = new ArrayList<>();
        for (int num = 0; num < samples; num++) {
            tasks.add(() -> {
                splitRender(
                    canvas,
                    world,
                    variations,
                    samples,
                    iterPerSample,
                    symmetry,
                    transformations
                );
                return null;
            });

        }
        try {
            executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
        return canvas;
    }

    private void splitRender(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        int samples,
        int iterPerSample,
        int symmetry,
        AffineTransformation[] transformations
    ) {
        int variationIndex;
        Point pwr;
        Optional<Pixel> pixelOptional;
        Pixel pixel;
        int indexTransformation;

        Point pw = world.randomPoint();
        for (int step = START_DRAW; step < iterPerSample; step++) {
            indexTransformation = ThreadLocalRandom.current().nextInt(0, samples);
            pw = applyTransformationToPoint(pw, transformations[indexTransformation]);

            if (step < 0) {
                continue;
            }
            variationIndex = ThreadLocalRandom.current().nextInt(0, variations.size());
            Transformation variation = variations.get(variationIndex);
            pw = variation.apply(pw);

            double theta = 0.0;
            for (int s = 0; s < symmetry; theta += 2 * Math.PI / symmetry, s++) {
                pwr = Point.getRotatedPoint(pw, theta);

                if (!world.containsPoint(pwr)) {
                    continue;
                }

                pixelOptional = canvas.getPixel(
                    (int) (((pwr.x() - world.x())) * canvas.width() / world.width()),
                    (int) ((pwr.y() - world.y()) * canvas.height() / world.height())
                );
                if (pixelOptional.isPresent()) {
                    pixel = pixelOptional.get();
                    synchronized (pixel) {
                        setPixelColor(pixel, transformations[indexTransformation]);
                        pixel.setHitCount(pixel.getHitCount() + 1);
                    }
                }
            }
        }
    }
}
