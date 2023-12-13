package edu.project4.model;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public record AffineTransformation(double a, double b, double c, double d, double e, double f, Color color) {
    private static final int COUNT_OF_COEFFICIENTS = 6;
    private static final int MAX_VALUE_COLOR = 256;

    public static AffineTransformation[] getRandomTransformations(int samples) {
        AffineTransformation[] transformations = new AffineTransformation[samples];
        for (int i = 0; i < samples; i++) {
            transformations[i] = AffineTransformation.create();
        }
        return transformations;
    }

    @SuppressWarnings("MagicNumber")
    public static AffineTransformation create() {
        // a, b, c, d, e, f
        double[] coeff = new double[COUNT_OF_COEFFICIENTS];
        do {
            for (int i = 0; i < COUNT_OF_COEFFICIENTS; i++) {
                coeff[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
            }
        } while (isCoefficientValid(coeff));
        return new AffineTransformation(coeff[0], coeff[1], coeff[2], coeff[3], coeff[4], coeff[5], getRandomColor());
    }

    @SuppressWarnings("MagicNumber")
    private static boolean isCoefficientValid(double[] coeff) {
        double a = coeff[0];
        double b = coeff[1];
        double d = coeff[3];
        double e = coeff[4];
        return (a * a + d * d) < 1 && (b * b + e * e) < 1
            && (a * a + b * b + d * d + e * e) < 1 + (a * e - b * d) * (a * e - b * d);
    }

    private static Color getRandomColor() {
        return new Color(
            ThreadLocalRandom.current().nextInt(0, MAX_VALUE_COLOR),
            ThreadLocalRandom.current().nextInt(0, MAX_VALUE_COLOR),
            ThreadLocalRandom.current().nextInt(0, MAX_VALUE_COLOR)
        );
    }
}
