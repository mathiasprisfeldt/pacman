package me.mathiasprisfeldt.pacman;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import java.util.ArrayList;

import me.mathiasprisfeldt.pacman.Components.Component;
import me.mathiasprisfeldt.pacman.Components.Transform;
import me.mathiasprisfeldt.pacman.Interfaces.Drawable;
import me.mathiasprisfeldt.pacman.Interfaces.Resetable;
import me.mathiasprisfeldt.pacman.Interfaces.Touchable;
import me.mathiasprisfeldt.pacman.Interfaces.Updateable;
import me.mathiasprisfeldt.pacman.Types.Vector2D;

public class GameObject {
    private ArrayList<Component> _components = new ArrayList<>();
    private GameWorld _gameWorld;
    private Transform _transform;

    public GameWorld getGameWorld() {
        return _gameWorld;
    }

    public Transform getTransform() {
        return _transform;
    }

    public GameObject(GameWorld gameWorld) {
        _gameWorld = gameWorld;
        _gameWorld.add(this);

        addComponent(_transform = new Transform(this, 0, 0));
    }

    GameObject(GameWorld gameWorld, float x, float y) {
        _gameWorld = gameWorld;
        _gameWorld.add(this);

        addComponent(_transform = new Transform(this, x, y));
    }

    public <T extends Component> T addComponent(T component) {
        _components.add(component);
        component.onStart();
        return component;
    }

    <T extends Component> T getComponent(Class<T> type) {
        for (int i = 0; i < _components.size(); i++) {
            Component component = _components.get(i);
            if (type.isInstance(component))
                return (T) component;
        }

        return null;
    }

    public void destroy() {
        _gameWorld.destroy(this);
    }

    void onDestroyed() {
        for (int i = 0; i < _components.size(); i++) {
            Component component = _components.get(i);
            component.onDestroyed();
        }
    }

    public void onDraw(Canvas canvas) {
        for (int i = 0; i < _components.size(); i++) {
            Component component = _components.get(i);
            if (component instanceof Drawable)
                ((Drawable) component).onDraw(canvas);
        }
    }

    public void onReset() {
        for (int i = 0; i < _components.size(); i++) {
            Component component = _components.get(i);
            if (component instanceof Resetable)
                ((Resetable) component).onReset();
        }
    }

    public void onUpdate(float deltaTime) {
        for (int i = 0; i < _components.size(); i++) {
            Component component = _components.get(i);
            if (component instanceof Updateable)
                component.onUpdate(deltaTime);
        }
    }

    public void onTouchEvent(int action, VelocityTracker velTracker, Vector2D vel) {
        for (int i = 0; i < _components.size(); i++) {
            Component component = _components.get(i);
            if (component instanceof Touchable) {
                Touchable touchable = (Touchable) component;

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        touchable.touchDown(velTracker);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        touchable.touchMove(velTracker, vel);
                        break;
                    case MotionEvent.ACTION_UP:
                        touchable.touchUp(velTracker);
                        break;
                }
            }
        }
    }
}
