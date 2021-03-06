package me.mathiasprisfeldt.pacman.Components;

import android.graphics.Matrix;
import android.util.Log;

import me.mathiasprisfeldt.pacman.GameObject;
import me.mathiasprisfeldt.pacman.Interfaces.Resetable;
import me.mathiasprisfeldt.pacman.Interfaces.Updateable;
import me.mathiasprisfeldt.pacman.Types.Vector2D;

public class Transform extends Component implements Updateable, Resetable {

    private Matrix _matrix = new Matrix();

    private Vector2D _offset = new Vector2D(0, 0);
    private Vector2D _position = new Vector2D(0, 0);
    private Vector2D _scale = new Vector2D(1, 1);
    private Vector2D _velocity = new Vector2D(0, 0);
    private float _rotation;

    public Matrix getMatrix() {
        return _matrix;
    }

    public void setRotation(float _rotation) {
        this._rotation = _rotation;
        updateMatrix();
    }

    public void setOffset(Vector2D offset) {
        _offset = offset;
        setPosition(_position);
    }

    public Vector2D getPosition() {
        return _position;
    }

    public void setPosition(Vector2D pos) {
        _position = pos.copy();
        updateMatrix();
    }

    public void setScale(Vector2D scale) {
        _scale = scale;
        updateMatrix();
    }

    public void setVelocity(Vector2D _velocity) {
        this._velocity = _velocity;
    }

    public void updateMatrix() {
        _matrix.setTranslate(_position.x() + _offset.x(), _position.y() + _offset.y());
        _matrix.postScale(_scale.x(), _scale.y());
        _matrix.postRotate(_rotation, _position.x(), _position.y());
    }

    public Transform(GameObject gameObject, float x, float y) {
        super(gameObject);
        setPosition(new Vector2D(x, y));
        setScale(new Vector2D(1, 1));
    }

    @Override
    public void onUpdate(float deltaTime) {
        setPosition(_position.add(_velocity.multiply(deltaTime)));
        _velocity = Vector2D.Zero;
    }

    @Override
    public void onReset() {
        _velocity = Vector2D.Zero;
        setRotation(0);
    }
}
