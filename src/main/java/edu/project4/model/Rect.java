package edu.project4.model;

import java.util.concurrent.ThreadLocalRandom;

public record Rect(double x, double y, double width, double height) {
    public boolean containsPoint(Point p) {
        return p.x() >= x && p.x() <= x + width && p.y() >= y && p.y() <= y + height;
    }

    public Point randomPoint() {
        double newX = x + width * ThreadLocalRandom.current().nextDouble();
        double newY = y + height * ThreadLocalRandom.current().nextDouble();
        return new Point(newX, newY);
    }
}
