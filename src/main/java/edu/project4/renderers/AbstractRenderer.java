package edu.project4.renderers;

import edu.project4.model.AffineTransformation;
import edu.project4.model.Pixel;
import edu.project4.model.Point;

public abstract class AbstractRenderer implements Renderer {
    protected final static int START_DRAW = -20;

    protected Point applyTransformationToPoint(Point p, AffineTransformation t) {
        // Перемножение матриц и сложение с вектор-столбцом
        double newX = t.a() * p.x() + t.b() * p.y() + t.c();
        double newY = t.d() * p.x() + t.e() * p.y() + t.f();
        return new Point(newX, newY);
    }

    protected void setPixelColor(Pixel pixel, AffineTransformation affineTransformation) {
        if (pixel.getHitCount() == 0) {
            pixel.setR(affineTransformation.color().getRed());
            pixel.setG(affineTransformation.color().getGreen());
            pixel.setB(affineTransformation.color().getBlue());
        } else {
            pixel.setR((pixel.getR() + affineTransformation.color().getRed()) / 2);
            pixel.setG((pixel.getG() + affineTransformation.color().getGreen()) / 2);
            pixel.setB((pixel.getB() + affineTransformation.color().getBlue()) / 2);
        }
    }

}
