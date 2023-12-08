package edu.project4.transformations;

import edu.project4.model.Point;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class ExTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double r = sqrt(x * x + y * y);
        double theta = atan2(y, x);
        double p0 = sin(theta + r);
        p0 = p0 * p0 * p0;
        double p1 = cos(theta - r);
        p1 = p1 * p1 * p1;
        double newX = r * (p0 + p1);
        double newY = r * (p0 - p1);
        return new Point(newX, newY);
    }
}
