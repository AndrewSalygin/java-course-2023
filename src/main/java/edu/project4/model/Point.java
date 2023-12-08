package edu.project4.model;

public record Point(double x, double y) {
     public static Point getRotatedPoint(Point pw, double theta) {
        double xRotated = pw.x() * Math.cos(theta) - pw.y() * Math.sin(theta);
        double yRotated = pw.x() * Math.sin(theta) + pw.y() * Math.cos(theta);
        return new Point(xRotated, yRotated);
    }
}
