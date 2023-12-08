package edu.project4.renderers;

import edu.project4.model.AffineTransformation;
import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import edu.project4.model.Point;
import edu.project4.model.Rect;
import edu.project4.transformations.Transformation;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class ConsistentFractalRenderer extends AbstractRenderer {
    @Override
    public FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        int samples,
        int iterPerSample,
        int symmetry
    ) {
        int variationIndex;
        Point pwr;
        Optional<Pixel> pixelOptional;
        Pixel pixel;
        int indexTransformation;
        AffineTransformation[] transformations = AffineTransformation.getRandomTransformations(samples);

        for (int num = 0; num < samples; num++) {
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
                        setPixelColor(pixel, transformations[indexTransformation]);
                        pixel.setHitCount(pixel.getHitCount() + 1);
                    }
                }
            }
        }
        return canvas;
    }
}
