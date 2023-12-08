package edu.project4.processors;

import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import java.util.Optional;

public class GammaCorrectionImageProcessor implements ImageProcessor {

    private static final double GAMMA = 2.1;

    @Override
    public void process(FractalImage image) {
        double max = 0;
        Optional<Pixel> pixelOptional;
        Pixel pixel;
        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                pixelOptional = image.getPixel(x, y);
                if (pixelOptional.isPresent()) {
                    pixel = pixelOptional.get();
                    if (pixel.getHitCount() != 0) {
                        pixel.setNormal(Math.log10(pixel.getHitCount()));
                        max = Math.max(max, pixel.getNormal());
                    }
                    pixel.setNormal(pixel.getNormal() / max);
                    pixel.setR(
                        (int) (pixel.getR() * Math.pow(pixel.getNormal(), (1.0 / GAMMA))));
                    pixel.setG(
                        (int) (pixel.getG() * Math.pow(pixel.getNormal(), (1.0 / GAMMA))));
                    pixel.setB(
                        (int) (pixel.getB() * Math.pow(pixel.getNormal(), (1.0 / GAMMA))));
                }
            }
        }
    }
}
