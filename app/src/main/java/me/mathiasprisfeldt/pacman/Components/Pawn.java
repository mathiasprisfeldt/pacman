package me.mathiasprisfeldt.pacman.Components;

import android.content.res.Resources;

import me.mathiasprisfeldt.pacman.GameObject;
import me.mathiasprisfeldt.pacman.Interfaces.Updateable;
import me.mathiasprisfeldt.pacman.Map.CardinalDirection;
import me.mathiasprisfeldt.pacman.Map.Edge;
import me.mathiasprisfeldt.pacman.Map.Map;
import me.mathiasprisfeldt.pacman.Map.Node;
import me.mathiasprisfeldt.pacman.Types.Vector2D;

public class Pawn extends Component implements Updateable {
    private Map _map;
    private SpriteRenderer _renderer;

    private float _speed = 500;
    private Edge _currEdge;
    private Node _currNode;

    private CardinalDirection _direction = CardinalDirection.None;
    private CardinalDirection _queuedDir = CardinalDirection.None;

    Pawn(GameObject gameObject, SpriteRenderer renderer, Node node, Map map) {
        super(gameObject);
        _renderer = renderer;
        _currNode = node;
        _map = map;
        _gameObject.getTransform().setPosition(node.getPosition());
    }

    @Override
    public void onUpdate(float deltaTime) {
        super.onUpdate(deltaTime);

        Transform transform = _gameObject.getTransform();
        Vector2D pos = transform.getPosition();

        transform.setVelocity(_direction.toVector().multiply(_speed));

        if (_currEdge != null)
            transform.setPosition(_currEdge.Clamp(pos));

        //TODO: Should properly be a collision component
        if (_currNode != null && !_currNode.isColliding(pos))
            _currNode = null;

        for (Node node : _map.getNodes()) {
          if (node.isColliding(pos)){
              _currNode = node;

              if (_queuedDir != CardinalDirection.None)
                onNewDirection(_queuedDir);
              else
                onNewDirection(_direction);

              _queuedDir = CardinalDirection.None;
          }
        }
    }

    void onNewDirection(CardinalDirection dir) {
        if (_currNode != null) {
            Edge edge = _currNode.getEdge(dir);
            if (edge != null) {
                _direction = dir;
                _currEdge = edge;
            }
        }
        else if (_currEdge != null &&
                dir.invert() == _currEdge.getDir() ||
                dir.invert() == _currEdge.getDir().invert())
            _direction = dir;
        else
            _queuedDir = dir;
    }
}
