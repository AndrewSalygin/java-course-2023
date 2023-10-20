package edu.hw2.Task2;

public class Square extends Rectangle {
    private int side;

    public Square(int side) {
        super(side, side);
    }

    public Square() {
    }

    public Rectangle setWidth(int width) {
        return new Rectangle(width, side);
    }

    public Rectangle setHeight(int height) {
        return new Rectangle(side, height);
    }
}
