package edu.project4.model;

import java.util.Optional;

public record FractalImage(Pixel[][] data, int width, int height) {
    public static FractalImage create(int width, int height) {
        Pixel[][] data = new Pixel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                data[i][j] = new Pixel(0, 0, 0, 0, 0);
            }
        }
        return new FractalImage(data, width, height);
    }

    public boolean containsPixel(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Optional<Pixel> getPixel(int x, int y) {
        if (containsPixel(x, y)) {
            return Optional.of(data[x][y]);
        }
        return Optional.empty();
    }
}
