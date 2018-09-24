package me.mathiasprisfeldt.pacman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.mathiasprisfeldt.pacman.Interfaces.Drawable;
import me.mathiasprisfeldt.pacman.Interfaces.Updateable;

public class GameWorld extends View {
    private long _lastTime = System.nanoTime();
    private Timer _updateTimer;

    private ArrayList<GameObject> _gameObjects;
    private ArrayList<GameObject> _gameObjectsToAdd;
    private ArrayList<GameObject> _gameObjectsToRemove;

    public GameWorld(Context context) {
        super(context);

        _updateTimer = new Timer();
        _updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                onUpdate();
            }
        }, 0, 16);
    }

    public void onUpdate() {
        long currTime = System.nanoTime();
        float deltaTime = (int) ((currTime - _lastTime) / 1000);
        _lastTime = currTime;

        for (GameObject gameObject : _gameObjects) {
            if (gameObject instanceof Updateable)
                ((Updateable) gameObject).onUpdate(deltaTime);
        }

        invalidate();
    }

    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        for (GameObject gameObject : _gameObjects) {
            if (gameObject instanceof Drawable)
                ((Drawable) gameObject).onDraw(canvas);
        }

        super.onDraw(canvas);
    }
}
