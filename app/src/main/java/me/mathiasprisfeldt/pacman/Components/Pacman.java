package me.mathiasprisfeldt.pacman.Components;

import android.content.res.Resources;
import android.os.Debug;
import android.util.Log;
import android.view.VelocityTracker;

import me.mathiasprisfeldt.pacman.GameObject;
import me.mathiasprisfeldt.pacman.Interfaces.Touchable;
import me.mathiasprisfeldt.pacman.Interfaces.Updateable;
import me.mathiasprisfeldt.pacman.Map.CardinalDirection;
import me.mathiasprisfeldt.pacman.Map.Edge;
import me.mathiasprisfeldt.pacman.Map.Map;
import me.mathiasprisfeldt.pacman.Map.Node;
import me.mathiasprisfeldt.pacman.Types.Vector2D;

public class Pacman extends Pawn implements Touchable {

    public Pacman(GameObject gameObject, SpriteRenderer renderer, Node node, Map map) {
        super(gameObject, renderer, node, map);
    }

    @Override
    public void onUpdate(float deltaTime) {
        super.onUpdate(deltaTime);
    }

    @Override
    public void touchDown(VelocityTracker velTracker) {

    }

    @Override
    public void touchMove(VelocityTracker velTracker, Vector2D vel) {
        if (vel.sqrMagnitude() > 2000000) {
            vel.Normalize();
            vel.ToCardinal();

            onNewDirection(CardinalDirection.fromPoint(vel.toPoint()));
        }
    }

    @Override
    public void touchUp(VelocityTracker velTracker) {

    }
}
