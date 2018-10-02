package me.mathiasprisfeldt.pacman.Components;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import me.mathiasprisfeldt.pacman.Extensions.MathExtensions;
import me.mathiasprisfeldt.pacman.GameObject;
import me.mathiasprisfeldt.pacman.Interfaces.Resetable;
import me.mathiasprisfeldt.pacman.Interfaces.Updateable;

public class Animator extends Component implements Updateable, Resetable {

    private float _speed = 1;
    private int _currImg;
    private float _elapsed;
    private int _fps;
    private SpriteRenderer _renderer;
    private Bitmap[] _imageStrip;

    public void setSpeed(float _speed) {
        this._speed = _speed;
    }

    public void setImage(int _currImg) {
        this._currImg = _currImg;
    }

    public Animator(GameObject gameObject, SpriteRenderer renderer, int[] images, int fps) {
        super(gameObject);
        _renderer = renderer;
        _fps = fps;

        _imageStrip = new Bitmap[images.length];
        Resources resources = _gameObject.getGameWorld().getResources();
        for (int i = 0; i < _imageStrip.length; i++) {
            _imageStrip[i] = BitmapFactory.decodeResource(resources, images[i]);
        }
    }

    @Override
    public void onUpdate(float deltaTime) {
        int newImg = (int) (_elapsed * (_fps * _speed));

        _currImg = MathExtensions.Wrap(newImg, 0, _imageStrip.length - 1);
        _renderer.setBitmap(_imageStrip[_currImg]);
        _elapsed += deltaTime;
    }

    @Override
    public void onReset() {
        _currImg = 0;
        _speed = 1;
    }
}
