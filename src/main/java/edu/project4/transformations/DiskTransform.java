package edu.project4.transformations;

import edu.project4.model.Point;
import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class DiskTransform implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double r = sqrt(x * x + y * y) * PI;
        double theta = atan2(y, x) / PI;
        double newX = theta * sin(r);
        double newY = theta * cos(r);
        return new Point(newX, newY);
    }
}
