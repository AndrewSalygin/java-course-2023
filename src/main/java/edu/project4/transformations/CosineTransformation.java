package edu.project4.transformations;

import edu.project4.model.Point;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.cosh;
import static java.lang.Math.sin;
import static java.lang.Math.sinh;

public class CosineTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double newX = cos(PI * x) * cosh(y);
        double newY = -sin(PI * x) * sinh(y);
        return new Point(newX, newY);
    }
}
