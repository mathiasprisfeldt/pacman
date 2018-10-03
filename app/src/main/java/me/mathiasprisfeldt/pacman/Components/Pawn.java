package me.mathiasprisfeldt.pacman.Components;

import android.content.res.Resources;

import java.util.ArrayList;

import me.mathiasprisfeldt.pacman.GameObject;
import me.mathiasprisfeldt.pacman.Interfaces.Collideable;
import me.mathiasprisfeldt.pacman.Interfaces.Resetable;
import me.mathiasprisfeldt.pacman.Interfaces.Updateable;
import me.mathiasprisfeldt.pacman.Map.CardinalDirection;
import me.mathiasprisfeldt.pacman.Map.Edge;
import me.mathiasprisfeldt.pacman.Map.Map;
import me.mathiasprisfeldt.pacman.Map.Node;
import me.mathiasprisfeldt.pacman.Types.Vector2D;

public class Pawn extends Component implements Updateable, Resetable {
    private Map _map;
    private float _speed;
    private Edge _currEdge;
    private Node _spawnPoint;

    Node _lastNode;
    Node _currNode;
    SpriteRenderer _renderer;
    CardinalDirection _direction = CardinalDirection.None;
    CardinalDirection _queuedDir = CardinalDirection.None;

    Pawn(GameObject gameObject, SpriteRenderer renderer, Node node, Map map, float speed) {
        super(gameObject);
        _renderer = renderer;
        _spawnPoint = node;
        _map = map;
        _speed = speed;

        ResetPos();
    }

    private void ResetPos() {
        _currNode = _spawnPoint;
        onNewNode(_currNode);
        _gameObject.getTransform().setPosition(_spawnPoint.getPosition());
    }

    @Override
    public void onUpdate(float deltaTime) {
        Transform transform = _gameObject.getTransform();
        Vector2D pos = transform.getPosition();

        transform.setVelocity(_direction.toVector().multiply(_speed));

        if (_currEdge != null)
            transform.setPosition(pos = _currEdge.Clamp(pos));

        if (_currNode != null && !_currNode.isColliding(pos))
            _currNode = null;

        for (Node node : _map.getNodes()) {
          if (node.isColliding(pos)){
              if (_currNode == null)
                  onNewNode(node);

              _lastNode = _currNode;
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

    void onNewNode(Node newNode) {

    }

    @Override
    public void onReset() {
        _currNode = null;
        _currEdge = null;
        _lastNode = null;
        _direction = CardinalDirection.None;
        _queuedDir = CardinalDirection.None;

        ResetPos();
    }
}
