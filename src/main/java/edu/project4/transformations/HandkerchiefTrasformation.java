package edu.project4.transformations;

import edu.project4.model.Point;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class HandkerchiefTrasformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double r = sqrt(x * x + y * y);
        double theta = atan2(y, x);
        return new Point(
            r * sin(theta + r),
            r * cos(theta - r)
        );
    }
}
