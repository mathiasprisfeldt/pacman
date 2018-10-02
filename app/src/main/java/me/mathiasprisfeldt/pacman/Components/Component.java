package me.mathiasprisfeldt.pacman.Components;

import me.mathiasprisfeldt.pacman.GameObject;
import me.mathiasprisfeldt.pacman.GameWorld;

public class Component {
    GameObject _gameObject;
    GameWorld _gameWorld;
    Transform _transform;

    Component(GameObject gameObject) {
        _gameObject = gameObject;
        _gameWorld = _gameObject.getGameWorld();
        _transform = _gameObject.getTransform();
    }

    public void onStart() {

    }

    public void onDestroyed() {

    }
}
