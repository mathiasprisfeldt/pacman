package me.mathiasprisfeldt.pacman.Components;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

import me.mathiasprisfeldt.pacman.GameObject;
import me.mathiasprisfeldt.pacman.Interfaces.Collideable;
import me.mathiasprisfeldt.pacman.Interfaces.Updateable;
import me.mathiasprisfeldt.pacman.Types.Vector2D;

public class CircleCollider extends Component implements Updateable {
    private boolean _passiveCollider;
    private float _radius;

    public float getRadius() {
        return _radius;
    }

    public CircleCollider(GameObject gameObject, boolean passiveCollider, float radius) {
        super(gameObject);
        _passiveCollider = passiveCollider;
        _radius = radius;
    }

    @Override
    public void onUpdate(float deltaTime) {
        if (_passiveCollider)
            return;

        ArrayList<GameObject> gameObjects = _gameWorld.getGameObjects();
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject gameObject = gameObjects.get(i);

            if (gameObject == _gameObject)
                continue;

            CircleCollider otherCollider = gameObject.getComponent(CircleCollider.class);

            if (otherCollider == null)
                continue;

            Vector2D otherPos = gameObject.getTransform().getPosition();

            if (_transform.getPosition().RadiusIntersect(otherPos, otherCollider.getRadius(), _radius))
                notifyListeners(gameObject);
        }
    }

    private void notifyListeners(GameObject other) {
        Collideable[] components = _gameObject.getComponents(Collideable.class);
        for (int i = 0; i < components.length; i++) {
            Collideable collideable = components[i];

            if (collideable != null)
                collideable.OnCollisionEnter(other);
        }
    }
}