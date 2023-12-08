package edu.project4.transformations;

import edu.project4.model.Point;
import static java.lang.Math.PI;
import static java.lang.Math.cos;

@SuppressWarnings("MagicNumber")
public class CollatzTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double newX = 0.25 * (1.0 + 4.0 * x - (1.0 + 2.0 * x) * cos(PI * x));
        double newY = 0.25 * (1.0 + 4.0 * y - (1.0 + 2.0 * y) * cos(PI * y));
        return new Point(newX, newY);
    }
}
