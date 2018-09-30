package me.mathiasprisfeldt.pacman.Components;

import me.mathiasprisfeldt.pacman.GameObject;

public class Component {
    GameObject _gameObject;

    Component(GameObject gameObject) {
        _gameObject = gameObject;
    }

    public void onStart() {

    }

    public void onUpdate(float deltaTime) {

    }

    public void onDestroyed() {

    }
}
