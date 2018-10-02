package me.mathiasprisfeldt.pacman.Interfaces;

import me.mathiasprisfeldt.pacman.GameObject;

public interface Collideable {
    void OnCollisionEnter(GameObject other);
}
