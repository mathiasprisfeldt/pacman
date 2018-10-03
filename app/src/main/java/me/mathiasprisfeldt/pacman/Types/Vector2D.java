package me.mathiasprisfeldt.pacman.Types;

import android.support.annotation.NonNull;
import android.support.v4.math.MathUtils;

public class Vector2D {
    public static final Vector2D Zero = new Vector2D(0, 0);
    private float _x;
    private float _y;

    private boolean _isSqrMagCalced;
    private double _sqrMagnitude;

    private boolean _isMagCalced;
    private double _magnitude;

    public float x() {
        return _x;
    }

    public float y() {
        return _y;
    }

    public double sqrMagnitude() {
        if (!_isSqrMagCalced) {
            _sqrMagnitude = Math.pow(_x, 2) + Math.pow(_y, 2);
            _isSqrMagCalced = true;
        }

        return _sqrMagnitude;
    }

    public double magnitude() {
        if (!_isMagCalced) {
            _magnitude = Math.sqrt(Math.pow(_x, 2) + Math.pow(_y, 2));
            _isMagCalced = true;
        }

        return _magnitude;
    }

    public Vector2D(float _x, float _y) {
        this._x = _x;
        this._y = _y;
    }

    public void Normalize() {
        float val = (float) (1f / magnitude());
        _x *= val;
        _y *= val;

        _isMagCalced = false;
        _isSqrMagCalced = false;
    }

    public void ToCardinal() {
        if (Math.abs(_x) > Math.abs(_y))
            _y = 0;
        else
            _x = 0;

        _isMagCalced = false;
        _isSqrMagCalced = false;
    }

    public boolean RadiusIntersect(Vector2D other, float otherRad, float ourRadius)
    {
        float dx = other.x() - x();
        float dy = other.y() - y();
        float radii = ourRadius + otherRad;

        return (dx * dx) + (dy * dy) < radii * radii;
    }

    public float Dot(Vector2D other)
    {
        return (_x * other.x()) + (_y * other.y());
    }

    public double DistanceTo(Vector2D other) {
        return this.subtract(other).magnitude();
    }

    public static Vector2D Lerp(Vector2D point1, Vector2D point2, float alpha)
    {
        return point1.add(point2.subtract(point1).multiply(alpha));
    }

    public Vector2D NearestPoint(Vector2D start, Vector2D end)
    {
        Vector2D line = end.subtract(start);
        double len = line.magnitude();
        line.Normalize();

        Vector2D v = this.subtract(start);
        float d = v.Dot(line);

        d = (float) MathUtils.clamp(d, 0f, len);

        return start.add(line.multiply(d));
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(
                _x + other._x,
                _y + other._y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(
                _x - other._x,
                _y - other._y
        );
    }

    public Vector2D multiply(float val) {
        return new Vector2D(_x * val, _y * val);
    }

    public Point2D toPoint() {
        return new Point2D(Math.round(_x), Math.round(_y));
    }

    public Vector2D copy() {
        return new Vector2D(_x, _y);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("X: %s | Y: %s", _x, _y);
    }
}
