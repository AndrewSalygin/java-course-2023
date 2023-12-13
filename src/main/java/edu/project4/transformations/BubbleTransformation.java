package edu.project4.transformations;

import edu.project4.model.Point;

@SuppressWarnings("MagicNumber")
public class BubbleTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double r = 4 + x * x + y * y;
        double newX = (4.0 * x) / r;
        double newY = (4.0 * y) / r;
        return new Point(newX, newY);
    }
}
