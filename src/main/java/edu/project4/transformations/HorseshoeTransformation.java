package edu.project4.transformations;

import edu.project4.model.Point;
import static java.lang.Math.sqrt;

public class HorseshoeTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double r = 1 / sqrt(x * x + y * y);
        return new Point(
            r * (x - y) * (x + y),
            r * 2 * x * y
        );
    }
}
