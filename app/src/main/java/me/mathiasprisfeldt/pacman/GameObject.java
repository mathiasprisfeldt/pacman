package me.mathiasprisfeldt.pacman;

import android.graphics.Canvas;

import java.util.ArrayList;

import me.mathiasprisfeldt.pacman.Components.Component;
import me.mathiasprisfeldt.pacman.Interfaces.Drawable;
import me.mathiasprisfeldt.pacman.Interfaces.Resetable;
import me.mathiasprisfeldt.pacman.Interfaces.Updateable;

public class GameObject {
    private ArrayList<Component> _components = new ArrayList<>();
    private GameWorld _gameWorld;

    GameObject(GameWorld gameWorld) {
        _gameWorld = gameWorld;
        _gameWorld.add(this);
    }

    <T extends Component> GameObject addComponent(T component) {
        _components.add(component);
        component.onStart();
        return this;
    }

    <T extends Component> T getComponent(Class<T> type) {
        for (Component component : _components) {
            if (type.isInstance(component))
                return (T) component;
        }

        return null;
    }

    public void destroy() {
        _gameWorld.destroy(this);
    }

    void onDestroyed() {
        for (Component component : _components) {
            component.onDestroyed();
        }
    }

    public void onDraw(Canvas canvas) {
        for (Component component : _components) {
            if (component instanceof Drawable)
                ((Drawable) component).onDraw(canvas);
        }
    }

    public void onReset() {
        for (Component component : _components) {
            if (component instanceof Resetable)
                ((Resetable) component).onReset();
        }
    }

    public void onUpdate(float deltaTime) {
        for (Component component : _components) {
            if (component instanceof Updateable)
                component.onUpdate(deltaTime);
        }
    }
}
