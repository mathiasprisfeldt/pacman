package me.mathiasprisfeldt.pacman.Map;

import me.mathiasprisfeldt.pacman.Types.Vector2D;

public class Node {
    private Edge[] _edges = new Edge[4];
    private Vector2D _position;
    private double _radius = 10;

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
        if (dir != CardinalDirection.None)
            _edges[dir.getIndex()] = edge;
    }

    public Edge getEdge(CardinalDirection dir) {
        if (dir == CardinalDirection.None)
            return null;

        return _edges[dir.getIndex()];
    }

    public boolean isColliding(Vector2D pos) {
        double distanceTo = getPosition().DistanceTo(pos);
        return distanceTo < _radius;
    }
}
