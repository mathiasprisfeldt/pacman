package me.mathiasprisfeldt.pacman.Components;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.Random;

import me.mathiasprisfeldt.pacman.Extensions.ArrayExtensions;
import me.mathiasprisfeldt.pacman.GameObject;
import me.mathiasprisfeldt.pacman.Map.CardinalDirection;
import me.mathiasprisfeldt.pacman.Map.Map;
import me.mathiasprisfeldt.pacman.Map.Node;

public class Enemy extends Pawn {

    private Bitmap[] _imgDirections = new Bitmap[4];
    private Random _random;
    private CardinalDirection _lastDirInverted;

    public Enemy(GameObject gameObject, SpriteRenderer renderer, Node node, Map map, float speed) {
        super(gameObject, renderer, node, map, speed);
    }

    public void setImages(int[] images) {
        Resources resources = _gameObject.getGameWorld().getResources();
        for (int i = 0; i < _imgDirections.length; i++) {
            _imgDirections[i] = BitmapFactory.decodeResource(resources, images[i]);
        }
    }

    @Override
    public void onUpdate(float deltaTime) {
        super.onUpdate(deltaTime);

        switch (_direction) {
            case Left:
                _renderer.setBitmap(_imgDirections[0]);
                break;
            case Up:
                _renderer.setBitmap(_imgDirections[1]);
                break;
            case Right:
                _renderer.setBitmap(_imgDirections[2]);
                break;
            case Down:
                _renderer.setBitmap(_imgDirections[3]);
                break;
        }

        if (_direction == CardinalDirection.None && _currNode != null) {
            onNewNode(_currNode);
        }
    }

    @Override
    void onNewNode(Node newNode) {
        super.onNewNode(newNode);

        if (_random == null)
            _random = new Random();

        CardinalDirection[] directions = newNode.getValidDirections().clone();
        int validDirCount = newNode.getDirectionCount();

        _lastDirInverted = _direction.invert();
        if (_lastDirInverted != CardinalDirection.None && validDirCount > 1) {
            directions[_lastDirInverted.getIndex()] = null;
            directions = ArrayExtensions.Trim(CardinalDirection.class, directions);
            validDirCount--;
        }

        int randomIndex = _random.nextInt(validDirCount);

        _direction = directions[randomIndex];
    }

    @Override
    public void onReset() {
        super.onReset();
        _lastDirInverted = CardinalDirection.None;
    }
}
