package me.mathiasprisfeldt.pacman.Map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import me.mathiasprisfeldt.pacman.Types.Vector2D;

public class Map {
    private Paint _mapPaint = new Paint();

    private ArrayList<Node> _nodes = new ArrayList<>();
    private ArrayList<Edge> _edges = new ArrayList<>();

    private Node _pacmanSpawnPoint;

    public Node getPacmanSpawnPoint() {
        return _pacmanSpawnPoint;
    }

    public ArrayList<Node> getNodes() {
        return _nodes;
    }

    Map() {
        Node topLeft = CreateNode(50, 50);
        Node topRight = CreateNode(1000, 50);
        Node bottomRight = CreateNode(1000, 1000);
        Node bottomLeft = CreateNode(50, 1000);

        Node middle = CreateNode(525, 525);
        Node leftMiddle = CreateNode(50, 525);
        Node topMiddle = CreateNode(525, 50);
        Node rightMiddle = CreateNode(1000, 525);
        Node bottomMiddle = CreateNode(525, 1000);

        CreateEdge(leftMiddle, topLeft, CardinalDirection.Up);
        CreateEdge(leftMiddle, bottomLeft, CardinalDirection.Down);

        CreateEdge(topMiddle, topLeft, CardinalDirection.Left);
        CreateEdge(topMiddle, topRight, CardinalDirection.Right);

        CreateEdge(rightMiddle, topRight, CardinalDirection.Up);
        CreateEdge(rightMiddle, bottomRight, CardinalDirection.Down);

        CreateEdge(bottomMiddle, bottomLeft, CardinalDirection.Left);
        CreateEdge(bottomMiddle, bottomRight, CardinalDirection.Right);

        CreateEdge(middle, leftMiddle, CardinalDirection.Left);
        CreateEdge(middle, topMiddle, CardinalDirection.Up);
        CreateEdge(middle, rightMiddle, CardinalDirection.Right);
        CreateEdge(middle, bottomMiddle, CardinalDirection.Down);

        _pacmanSpawnPoint = topLeft;

        _mapPaint.setColor(Color.argb(255,80,80,80));
        _mapPaint.setStrokeWidth(5f);
    }

    private Node CreateNode(float x, float y) {
        Node newNode = new Node(x, y);
        _nodes.add(newNode);
        return newNode;
    }

    private Edge CreateEdge(Node from, Node to, CardinalDirection dir) {
        Edge edge = from.connectTo(to, dir);
        _edges.add(edge);
        return edge;
    }

    public void onDraw(Canvas canvas) {
        for (Node node : _nodes) {
            Vector2D pos = node.getPosition();
            canvas.drawCircle(pos.x(), pos.y(), 10f, _mapPaint);
        }

        for (Edge edge : _edges) {
            Vector2D fromPos = edge.getFrom().getPosition();
            Vector2D toPos = edge.getTo().getPosition();
            canvas.drawLine(fromPos.x(), fromPos.y(), toPos.x(), toPos.y(), _mapPaint);
        }
    }

}
