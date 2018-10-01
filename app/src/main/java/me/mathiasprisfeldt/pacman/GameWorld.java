package me.mathiasprisfeldt.pacman;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.mathiasprisfeldt.pacman.Map.Map;
import me.mathiasprisfeldt.pacman.Map.MapBuilder;
import me.mathiasprisfeldt.pacman.Types.Vector2D;

public class GameWorld extends View {
    private boolean _isPaused;
    private long _lastTime = System.nanoTime();

    private MapBuilder _mapBuilder;
    private Map _map;

    private GameObject[] _onDrawObjects = new GameObject[0];
    private GameObject[] _onTouchObjects = new GameObject[0];

    private ArrayList<GameObject> _gameObjects = new ArrayList<>();
    private ArrayList<GameObject> _gameObjectsToAdd = new ArrayList<>();
    private ArrayList<GameObject> _gameObjectsToRemove = new ArrayList<>();
    private VelocityTracker mVelocityTracker;

    public void setIsPaused(boolean _isPaused) {
        this._isPaused = _isPaused;
    }

    public GameWorld(Context context, AttributeSet attrs) {
        super(context);

        Timer _updateTimer = new Timer();
        _updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                onUpdate();
            }
        }, 0, 16);

        _mapBuilder = new MapBuilder(this);
        _map = _mapBuilder.getMap();
    }

    public void onUpdate() {
        if (_isPaused)
            return;

        //Calculate deltatime
        long currTime = System.nanoTime();
        float deltaTime = ((currTime - _lastTime) / 1000000000f);
        _lastTime = currTime;

        //Remove gameobjects that were marked for removal last frame
        for (int i = 0; i < _gameObjectsToRemove.size(); i++) {
            GameObject gameObject = _gameObjectsToRemove.get(i);
            gameObject.onDestroyed();
        }

        _gameObjects.removeAll(_gameObjectsToRemove);
        _gameObjectsToRemove.clear();

        //Add gameobjects that were added in last frame
        _gameObjects.addAll(_gameObjectsToAdd);
        _gameObjectsToAdd.clear();

        //Run update method for each gameobject
        for (int i = 0; i < _gameObjects.size(); i++) {
            GameObject gameObject = _gameObjects.get(i);
            gameObject.onUpdate(deltaTime);
        }

        postInvalidate();
    }

    public void onDraw(Canvas canvas) {
        _onDrawObjects = _gameObjects.toArray(_onDrawObjects);
        canvas.drawColor(getResources().getColor(R.color.game_world_bg_color));

        _map.onDraw(canvas);

        for (int i = 0; i < _onDrawObjects.length; i++) {
            GameObject gameObject = _onDrawObjects[i];
            gameObject.onDraw(canvas);
        }

        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        _onTouchObjects = _gameObjects.toArray(_onTouchObjects);

        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int pointerId = event.getPointerId(index);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain();
                } else {
                    mVelocityTracker.clear();
                }

                mVelocityTracker.addMovement(event);

                for (int i = 0; i < _onTouchObjects.length; i++) {
                    GameObject gameObject = _onTouchObjects[i];
                    gameObject.onTouchEvent(action, mVelocityTracker, Vector2D.Zero);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(event);
                mVelocityTracker.computeCurrentVelocity(1000);

                for (int i = 0; i < _onTouchObjects.length; i++) {
                    GameObject gameObject = _onTouchObjects[i];
                    gameObject.onTouchEvent(
                            action,
                            mVelocityTracker,
                            new Vector2D(
                                    mVelocityTracker.getXVelocity(pointerId),
                                    mVelocityTracker.getYVelocity(pointerId)));
                }
                break;
            case MotionEvent.ACTION_UP:
                for (int i = 0; i < _onTouchObjects.length; i++) {
                    GameObject gameObject = _onTouchObjects[i];
                    gameObject.onTouchEvent(action, mVelocityTracker, Vector2D.Zero);
                }
            case MotionEvent.ACTION_CANCEL:
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                break;
        }
        return true;
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
