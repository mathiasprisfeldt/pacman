package me.mathiasprisfeldt.pacman.Map;

import android.support.v4.content.res.TypedArrayUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import me.mathiasprisfeldt.pacman.Types.Vector2D;

public class Node {
    private int _directionCount;
    private CardinalDirection[] _validDirections = new CardinalDirection[4];
    private ArrayList<Node> _neighborNodes;
    private Edge[] _edges = new Edge[4];
    private Vector2D _position;
    private float _radius = 10;

    public ArrayList<Node> getNeighbors() {
        if (_neighborNodes == null) {
            _neighborNodes = new ArrayList<>(4);

            for (int i = 0; i < _edges.length; i++) {
                Edge edge = _edges[i];

                if (edge == null)
                    continue;

                Node newNode = edge.getFrom();

                if (newNode == this || newNode == null)
                    newNode = edge.getTo();

                if (newNode != null)
                    _neighborNodes.add(newNode);
            }
        }

        return _neighborNodes;
    }

    public int getDirectionCount() {
        return _directionCount;
    }

    public CardinalDirection[] getValidDirections() {
        return _validDirections;
    }

    public double getRadius() {
        return _radius;
    }

    public Vector2D getPosition() {
        return _position;
    }

    Node(float x, float y) {
        _position = new Vector2D(x, y);
    }

    public Edge connectTo(Node other, CardinalDirection dir) {
        if (dir == CardinalDirection.None)
            return null;

        Edge edge = new Edge(this, other, dir);

        addEdge(edge, dir);
        other.addEdge(edge, dir.invert());

        return edge;
    }

    private void addEdge(Edge edge, CardinalDirection dir) {
        if (dir != CardinalDirection.None) {
            int index = dir.getIndex();
            _edges[index] = edge;
            _validDirections[index] = dir;
        }

        _directionCount = 0;
        for (int i = 0; i < _validDirections.length; i++) {
            if (_validDirections[i] != null)
                _directionCount++;
        }
    }

    public Edge getEdge(CardinalDirection dir) {
        if (dir == CardinalDirection.None)
            return null;

        return _edges[dir.getIndex()];
    }

    public boolean isColliding(Vector2D pos) {
        return getPosition().RadiusIntersect(pos, _radius, _radius);
    }
}
