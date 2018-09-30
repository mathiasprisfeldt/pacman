package me.mathiasprisfeldt.pacman.Types;

import android.support.annotation.NonNull;

import me.mathiasprisfeldt.pacman.Interfaces.Resetable;

public class Vector2D {
    public static final Vector2D Zero = new Vector2D(0, 0);
    private float _x;
    private float _y;
    private double _sqrMagnitude;
    private double _magnitude;

    public float x() {
        return _x;
    }

    public float y() {
        return _y;
    }

    public double sqrMagnitude() {
        return _sqrMagnitude;
    }

    public double magnitude() {
        return _magnitude;
    }

    public Vector2D(float _x, float _y) {
        this._x = _x;
        this._y = _y;

        Recalculate();
    }

    private void Recalculate() {
        _sqrMagnitude = Math.pow(_x, 2) + Math.pow(_y, 2);
        _magnitude = Math.sqrt(Math.pow(_x, 2) + Math.pow(_y, 2));
    }

    public void Normalize() {
        float val = (float) (1f / _magnitude);
        _x *= val;
        _y *= val;

        Recalculate();
    }

    public void ToCardinal() {
        if (Math.abs(_x) > Math.abs(_y))
            _y = 0;
        else
            _x = 0;

        Recalculate();
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

    @NonNull
    @Override
    public String toString() {
        return String.format("X: %s | Y: %s", _x, _y);
    }
}
