package edu.project4.transformations;

import edu.project4.model.Point;
import static java.lang.Math.atan2;
import static java.lang.Math.sqrt;

public class PolarTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        return new Point(
            atan2(y, x) / Math.PI,
            sqrt(x * x + y * y) - 1
        );
    }
}
