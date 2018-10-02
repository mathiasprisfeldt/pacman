package me.mathiasprisfeldt.pacman.Components;

import android.content.res.Resources;
import android.os.Debug;
import android.util.Log;
import android.view.VelocityTracker;

import me.mathiasprisfeldt.pacman.GameManager;
import me.mathiasprisfeldt.pacman.GameObject;
import me.mathiasprisfeldt.pacman.Interfaces.Collideable;
import me.mathiasprisfeldt.pacman.Interfaces.Touchable;
import me.mathiasprisfeldt.pacman.Interfaces.Updateable;
import me.mathiasprisfeldt.pacman.Map.CardinalDirection;
import me.mathiasprisfeldt.pacman.Map.Edge;
import me.mathiasprisfeldt.pacman.Map.Map;
import me.mathiasprisfeldt.pacman.Map.Node;
import me.mathiasprisfeldt.pacman.Types.Vector2D;

public class Pacman extends Pawn implements Touchable, Collideable {

    private Animator _animator;

    public Pacman(GameObject gameObject,
                  SpriteRenderer renderer,
                  Animator animator,
                  Node node, Map map,
                  float speed) {
        super(gameObject, renderer, node, map, speed);
        _animator = animator;
    }

    @Override
    public void onUpdate(float deltaTime) {
        _animator.setSpeed(_direction == CardinalDirection.None ? 0 : 1);
        if (_direction == CardinalDirection.None)
            _animator.setImage(0);

        switch (_direction) {
            case Left:
                _transform.setRotation(-180);
                break;
            case Up:
                _transform.setRotation(-90);
                break;
            case Right:
                _transform.setRotation(0);
                break;
            case Down:
                _transform.setRotation(90);
                break;
        }

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

    @Override
    public void OnCollisionEnter(GameObject other) {
        if (other.compareTag("ENEMY")) {
            _gameObject.setIsActive(false);
            GameManager.getInstance().onPacmanDied();
        }
    }

    @Override
    void onNewNode(Node newNode) {
        super.onNewNode(newNode);

        if (newNode.getEdge(_direction) == null) {
            _direction = CardinalDirection.None;
        }
    }
}
