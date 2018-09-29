package me.mathiasprisfeldt.pacman;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.mathiasprisfeldt.pacman.Components.SpriteRenderer;

public class GameWorld extends View {
    private long _lastTime = System.nanoTime();

    private ArrayList<GameObject> _gameObjects = new ArrayList<>();
    private ArrayList<GameObject> _gameObjectsToAdd = new ArrayList<>();
    private ArrayList<GameObject> _gameObjectsToRemove = new ArrayList<>();

    public GameWorld(Context context, AttributeSet attrs) {
        super(context);

        Timer _updateTimer = new Timer();
        _updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                onUpdate();
            }
        }, 0, 16);

        GameObject newPacman = new GameObject(this);
        newPacman.addComponent(new SpriteRenderer());
    }

    public void onUpdate() {
        //Calculate deltatime
        long currTime = System.nanoTime();
        float deltaTime = (int) ((currTime - _lastTime) / 1000);
        _lastTime = currTime;

        //Remove gameobjects that were marked for removal last frame
        for (GameObject gameObject : _gameObjectsToRemove) {
            gameObject.onDestroyed();
        }
        _gameObjects.removeAll(_gameObjectsToRemove);
        _gameObjectsToRemove.clear();

        //Add gameobjects that were added in last frame
        _gameObjects.addAll(_gameObjectsToAdd);
        _gameObjectsToAdd.clear();

        //Run update method for each gameobject
        for (GameObject gameObject : _gameObjects) {
            gameObject.onUpdate(deltaTime);
        }

        invalidate();
    }

    public void onDraw(Canvas canvas) {
        canvas.drawColor(getResources().getColor(R.color.game_world_bg_color));

        for (GameObject gameObject : _gameObjects) {
            gameObject.onDraw(canvas);
        }

        super.onDraw(canvas);
    }

    public void onReset() {
        for (GameObject gameObject : _gameObjects) {
            gameObject.onReset();
        }
    }

    public void add(GameObject gameObject) {
        _gameObjectsToAdd.add(gameObject);
    }

    public void destroy(GameObject gameObject) {
        _gameObjectsToRemove.add(gameObject);
    }
}
