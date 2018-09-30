package me.mathiasprisfeldt.pacman.Types;

public class Point2D {
    private int _x;
    private int _y;

    public int x() {
        return _x;
    }

    public int y() {
        return _y;
    }

    public Point2D(int _x, int _y) {
        this._x = _x;
        this._y = _y;
    }

    public Point2D add(Point2D other) {
        return new Point2D(
                _x + other._x,
                _y + other._y);
    }

    public Point2D subtract(Point2D other) {
        return new Point2D(
                _x - other._x,
                _y - other._y
        );
    }
}
