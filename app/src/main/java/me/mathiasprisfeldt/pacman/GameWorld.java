package me.mathiasprisfeldt.pacman;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Matrix;
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

    private InGame _inGame;
    private MapBuilder _mapBuilder;
    private Map _map;

    private Matrix _canvasMatrix;

    private GameObject[] _onDrawObjects = new GameObject[0];
    private GameObject[] _onTouchObjects = new GameObject[0];

    private ArrayList<GameObject> _gameObjects = new ArrayList<>();
    private ArrayList<GameObject> _gameObjectsToAdd = new ArrayList<>();
    private ArrayList<GameObject> _gameObjectsToRemove = new ArrayList<>();
    private VelocityTracker velocityTracker;

    public ArrayList<GameObject> getGameObjects() {
        return _gameObjects;
    }

    public boolean getIsPaused() {
        return _isPaused;
    }

    public void setIsPaused(boolean _isPaused) {
        this._isPaused = _isPaused;
    }

    public GameWorld(Context context, AttributeSet attrs) {
        super(context, attrs);

        GameManager.getInstance().setGameWorld(this);

        _inGame = (InGame) context;
        _mapBuilder = new MapBuilder(this);
        _map = _mapBuilder.getMap();

        SoundManager.getInstance().initialize(context);

        Timer _updateTimer = new Timer();
        _updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                onUpdate();
            }
        }, 0, 16);
    }

    public void restartRound() {
        for (int i = 0; i < _gameObjects.size(); i++) {
            GameObject gameObject = _gameObjects.get(i);
            gameObject.setIsActive(true);
            gameObject.onReset();
        }
    }

    public void onUpdate() {
        //Calculate deltatime
        long currTime = System.nanoTime();
        float deltaTime = ((currTime - _lastTime) / 1000000000f);
        _lastTime = currTime;

        if (_isPaused)
            return;

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
        if (_canvasMatrix == null) {
            updateCanvasMatrix(canvas);
        }

        canvas.setMatrix(_canvasMatrix);

        _onDrawObjects = _gameObjects.toArray(_onDrawObjects);
        canvas.drawColor(getResources().getColor(R.color.game_world_bg_color));

        _map.onDraw(canvas);

        for (int i = 0; i < _onDrawObjects.length; i++) {
            GameObject gameObject = _onDrawObjects[i];
            gameObject.onDraw(canvas);
        }

        GameManager.getInstance().onUI(_inGame);

        super.onDraw(canvas);
    }

    private void updateCanvasMatrix(Canvas canvas) {
        _canvasMatrix = new Matrix();

        Vector2D mapSize = _map.getMapSize();
        float bgRatio = mapSize.y() / mapSize.x();
        float bgWidth = mapSize.x();
        float bgHeight = mapSize.y();
        float x;
        float y;
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        float xScale = width / bgWidth;
        if (bgWidth < width)
            xScale = bgWidth / width;

        float yScale = height / bgHeight;
        if (bgHeight < height)
            yScale = bgHeight / height;

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            yScale = xScale;
            height += height * (1 - yScale);

            x = 0;
            y = height / 2 - bgHeight / 2;
        } else {
            xScale = yScale;
            width = (int) (width / xScale);

            x = width / 2 - bgWidth / 2;
            y = 0;
        }

        _canvasMatrix.setTranslate(x, y);
        _canvasMatrix.postScale(xScale, yScale);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        _onTouchObjects = _gameObjects.toArray(_onTouchObjects);

        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int pointerId = event.getPointerId(index);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (velocityTracker == null) {
                    velocityTracker = VelocityTracker.obtain();
                } else {
                    velocityTracker.clear();
                }

                velocityTracker.addMovement(event);

                for (int i = 0; i < _onTouchObjects.length; i++) {
                    GameObject gameObject = _onTouchObjects[i];
                    gameObject.onTouchEvent(action, velocityTracker, Vector2D.Zero);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                velocityTracker.addMovement(event);
                velocityTracker.computeCurrentVelocity(1000);

                for (int i = 0; i < _onTouchObjects.length; i++) {
                    GameObject gameObject = _onTouchObjects[i];
                    gameObject.onTouchEvent(
                            action,
                            velocityTracker,
                            new Vector2D(
                                    velocityTracker.getXVelocity(pointerId),
                                    velocityTracker.getYVelocity(pointerId)));
                }
                break;
            case MotionEvent.ACTION_UP:
                for (int i = 0; i < _onTouchObjects.length; i++) {
                    GameObject gameObject = _onTouchObjects[i];
                    gameObject.onTouchEvent(action, velocityTracker, Vector2D.Zero);
                }
            case MotionEvent.ACTION_CANCEL:
                velocityTracker.recycle();
                velocityTracker = null;
                break;
        }
        return true;
    }

    public void add(GameObject gameObject) {
        _gameObjectsToAdd.add(gameObject);
    }

    public void destroy(GameObject gameObject) {
        _gameObjectsToRemove.add(gameObject);
    }
}
