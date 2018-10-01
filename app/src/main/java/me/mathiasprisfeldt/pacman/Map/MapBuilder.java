package me.mathiasprisfeldt.pacman.Map;

import android.util.Log;

import me.mathiasprisfeldt.pacman.Components.Pacman;
import me.mathiasprisfeldt.pacman.Components.SpriteRenderer;
import me.mathiasprisfeldt.pacman.Components.Transform;
import me.mathiasprisfeldt.pacman.GameObject;
import me.mathiasprisfeldt.pacman.GameWorld;
import me.mathiasprisfeldt.pacman.R;
import me.mathiasprisfeldt.pacman.Types.Vector2D;

public class MapBuilder {
    private GameWorld _gameWorld;
    private Map _map;

    public Map getMap() {
        return _map;
    }

    public MapBuilder(GameWorld gameWorld) {
        _gameWorld = gameWorld;
        _map = new Map();

        BuildPacman();
    }

    private void BuildPacman() {
        GameObject newPacman = new GameObject(_gameWorld);

        SpriteRenderer renderer = newPacman.addComponent(
                new SpriteRenderer(newPacman, R.drawable.player_placeholder));

        newPacman.addComponent(new Pacman(newPacman, renderer, _map.getPacmanSpawnPoint(), _map));
    }
}
