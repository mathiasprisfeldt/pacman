package me.mathiasprisfeldt.pacman;

import me.mathiasprisfeldt.pacman.Components.Pacman;
import me.mathiasprisfeldt.pacman.Components.SpriteRenderer;
import me.mathiasprisfeldt.pacman.Components.Transform;
import me.mathiasprisfeldt.pacman.Types.Vector2D;

public class MapBuilder {
    private GameWorld _gameWorld;

    public MapBuilder(GameWorld gameWorld) {
        _gameWorld = gameWorld;

        BuildPacman();
    }

    private void BuildPacman() {
        GameObject newPacman = new GameObject(_gameWorld);

        Transform tr = newPacman.getTransform();
        tr.setPosition(new Vector2D(50, 50));

        SpriteRenderer renderer = newPacman.addComponent(
                new SpriteRenderer(newPacman, R.drawable.player_placeholder));

        newPacman.addComponent(new Pacman(newPacman, renderer));
    }
}
