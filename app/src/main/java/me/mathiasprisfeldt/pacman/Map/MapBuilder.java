package me.mathiasprisfeldt.pacman.Map;

import android.util.Log;

import me.mathiasprisfeldt.pacman.Components.Animator;
import me.mathiasprisfeldt.pacman.Components.CircleCollider;
import me.mathiasprisfeldt.pacman.Components.Coin;
import me.mathiasprisfeldt.pacman.Components.Enemy;
import me.mathiasprisfeldt.pacman.Components.Pacman;
import me.mathiasprisfeldt.pacman.Components.SpriteRenderer;
import me.mathiasprisfeldt.pacman.Components.Transform;
import me.mathiasprisfeldt.pacman.GameManager;
import me.mathiasprisfeldt.pacman.GameObject;
import me.mathiasprisfeldt.pacman.GameWorld;
import me.mathiasprisfeldt.pacman.R;
import me.mathiasprisfeldt.pacman.Types.Vector2D;

public class MapBuilder {
    private static final float COIN_SPACE = 55f;
    private GameWorld _gameWorld;
    private Map _map;

    public Map getMap() {
        return _map;
    }

    public MapBuilder(GameWorld gameWorld) {
        _gameWorld = gameWorld;
        _map = new Map(gameWorld);

        BuildCoins();
//        BuildPacman();
//        BuildEnemies();
    }

    private void BuildEnemies() {
        int[] red = new int[] {
                R.drawable.enemy_red_left,
                R.drawable.enemy_red_up,
                R.drawable.enemy_red_right,
                R.drawable.enemy_red_down
        };
        BuildEnemy(R.drawable.enemy_red_right, 500).setImages(red);

        int[] blue = new int[] {
                R.drawable.enemy_blue_left,
                R.drawable.enemy_blue_up,
                R.drawable.enemy_blue_right,
                R.drawable.enemy_blue_down
        };
        BuildEnemy(R.drawable.enemy_blue_right, 250).setImages(blue);

        int[] orange = new int[] {
                R.drawable.enemy_orange_left,
                R.drawable.enemy_orange_up,
                R.drawable.enemy_orange_right,
                R.drawable.enemy_orange_down
        };
        BuildEnemy(R.drawable.enemy_orange_right, 100).setImages(orange);

        int[] pink = new int[] {
                R.drawable.enemy_pink_left,
                R.drawable.enemy_pink_up,
                R.drawable.enemy_pink_right,
                R.drawable.enemy_pink_down
        };
        BuildEnemy(R.drawable.enemy_pink_right, 250).setImages(pink);
    }

    private Enemy BuildEnemy(int image, float speed) {
        GameObject enemyGo = new GameObject(_gameWorld, "ENEMY");

        SpriteRenderer renderer = enemyGo.addComponent(
                new SpriteRenderer(enemyGo, image));

        Enemy enemy = enemyGo.addComponent(new Enemy(enemyGo, renderer, _map.getEnemySpawnPoint(), _map, speed));
        enemyGo.addComponent(new CircleCollider(enemyGo, false, 10));

        return enemy;
    }

    private void BuildCoins() {
        int amountOfCoins = 0;

        for (Node node : _map.getNodes()) {
            if (node == _map.getPacmanSpawnPoint())
                continue;

            BuildCoin(node.getPosition());
            amountOfCoins++;
        }

        for (Edge edge : _map.getEdges()) {
            double edgeLength = edge.getLength();
            int coinAmount = Math.round((float) edgeLength / COIN_SPACE);

            for (int i = 1; i < coinAmount; i++) {
                BuildCoin(edge.getPosition((float) i / coinAmount));
                amountOfCoins++;
            }
        }

        GameManager.getInstance().setCoinAmount(amountOfCoins);
    }

    private Coin BuildCoin(Vector2D pos) {
        GameObject newCoin = new GameObject(_gameWorld, "COIN");

        SpriteRenderer renderer = newCoin.addComponent(
                new SpriteRenderer(newCoin, R.drawable.coin));

        newCoin.addComponent(new CircleCollider(newCoin, false, 5));

        Coin coin = new Coin(newCoin);
        newCoin.addComponent(coin);

        newCoin.getTransform().setPosition(pos);

        return coin;
    }

    private void BuildPacman() {
        GameObject newPacman = new GameObject(_gameWorld, "PLAYER");

        SpriteRenderer renderer = newPacman.addComponent(
                new SpriteRenderer(newPacman, R.drawable.player_0));

        int[] frames = new int[] {
                R.drawable.player_0,
                R.drawable.player_1,
                R.drawable.player_2
        };
        Animator animator = newPacman.addComponent(new Animator(newPacman, renderer, frames, 10));

        newPacman.addComponent(new Pacman(newPacman, renderer, animator, _map.getPacmanSpawnPoint(), _map, 600));
        newPacman.addComponent(new CircleCollider(newPacman, false, 10));
    }
}
