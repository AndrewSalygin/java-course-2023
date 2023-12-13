package edu.project4.transformations;

import edu.project4.model.Point;

@SuppressWarnings("MagicNumber")
public class BentTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double newX;
        double newY;
        if (x >= 0.0 && y >= 0.0) {
            newX = x;
            newY = y;
        } else if (x < 0.0 && y >= 0.0) {
            newX = 2.0 * x;
            newY = y;
        } else if (x >= 0.0 && y < 0.0) {
            newX = x;
            newY = y * 0.5;
        } else {
            newX = 2.0 * x;
            newY = y * 0.5;
        }
        return new Point(newX, newY);
    }
}
