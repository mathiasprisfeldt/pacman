package me.mathiasprisfeldt.pacman.Map;

import android.support.v4.math.MathUtils;

import me.mathiasprisfeldt.pacman.Types.Vector2D;

public class Edge {
    private Node _from;
    private Node _to;
    private CardinalDirection _dir;

    public Node getFrom() {
        return _from;
    }

    public CardinalDirection getDir() {
        return _dir;
    }

    public Node getTo() {
        return _to;
    }

    public Edge(Node from, Node to, CardinalDirection dir) {
        _from = from;
        _to = to;
        _dir = dir;
    }

    public Vector2D Clamp(Vector2D other) {
        Vector2D fromPosition = _from.getPosition();
        Vector2D toPosition = _to.getPosition();

        float minX = Math.min(fromPosition.x(), toPosition.x());
        float maxX = Math.max(fromPosition.x(), toPosition.x());

        float minY = Math.min(fromPosition.y(), toPosition.y());
        float maxY = Math.max(fromPosition.y(), toPosition.y());

        return new Vector2D(
                MathUtils.clamp(other.x(), minX, maxX),
                MathUtils.clamp(other.y(), minY, maxY));
    }
}
