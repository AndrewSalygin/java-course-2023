package edu.project4.transformations;

import edu.project4.model.Point;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.exp;
import static java.lang.Math.sin;

public class ExponentialTransform implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double newX = exp(x - 1.0) * cos(PI * y);
        double newY = exp(x - 1.0) * sin(PI * y);
        return new Point(newX, newY);
    }
}
