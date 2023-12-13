package edu.project4.transformations;

import edu.project4.model.Point;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;

public class TangentTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double newX = sin(x) / cos(y);
        double newY = tan(y);
        return new Point(newX, newY);
    }
}
