package me.mathiasprisfeldt.pacman.Components;

import me.mathiasprisfeldt.pacman.GameManager;
import me.mathiasprisfeldt.pacman.GameObject;
import me.mathiasprisfeldt.pacman.Interfaces.Collideable;
import me.mathiasprisfeldt.pacman.SoundManager;

public class Coin extends Component implements Collideable {
    public Coin(GameObject gameObject) {
        super(gameObject);

    }

    @Override
    public void OnCollisionEnter(GameObject other) {

    }
}
