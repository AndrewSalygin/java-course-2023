package edu.project4.transformations;

import edu.project4.model.Point;
import static java.lang.Math.sqrt;

public class FishEyeTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double r = 2.0 / (1.0 + sqrt(x * x + y * y));
        double newX = r * y;
        double newY = r * x;
        return new Point(newX, newY);
    }
}
