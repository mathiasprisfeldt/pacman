package me.mathiasprisfeldt.pacman.Map;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import me.mathiasprisfeldt.pacman.GameWorld;
import me.mathiasprisfeldt.pacman.R;
import me.mathiasprisfeldt.pacman.Types.Vector2D;

public class Map {
    private GameWorld _gameWorld;
    private Bitmap _background;

    private Paint _mapPaint = new Paint();
    private Paint _bgPaint = new Paint();

    private ArrayList<Node> _nodes = new ArrayList<>();
    private ArrayList<Edge> _edges = new ArrayList<>();

    private Node _pacmanSpawnPoint;
    private Node _enemySpawnPoint;
    private Vector2D _offset = Vector2D.Zero;

    private Vector2D _mapSize;
    private int _width;
    private int _height;
    private Vector2D _cellDimensions;
    private int _columns = 14;
    private int _rows = 14;

    public Vector2D getMapSize() {
        return _mapSize;
    }

    public Node getPacmanSpawnPoint() {
        return _pacmanSpawnPoint;
    }

    public Node getEnemySpawnPoint() {
        return _enemySpawnPoint;
    }

    public ArrayList<Edge> getEdges() {
        return _edges;
    }

    public ArrayList<Node> getNodes() {
        return _nodes;
    }

    Map(GameWorld gameWorld) {
        _gameWorld = gameWorld;
        _background = BitmapFactory.decodeResource(gameWorld.getResources(), R.drawable.game_board);
        _mapSize = new Vector2D(_background.getWidth(), _background.getHeight());
        _width = _background.getWidth();
        _height = _background.getHeight();

        Node topLeft = CreateNode(0, 0);
        Node topRight = CreateNode(_columns, 0);
        Node bottomRight = CreateNode(_columns, _columns);
        Node bottomLeft = CreateNode(0, _columns);

        Node middle = CreateNode(_rows/2, _columns/2);
        Node leftMiddle = CreateNode(0, _columns/2);
        Node topMiddle = CreateNode(_rows/2, 0);
        Node rightMiddle = CreateNode(_columns, _columns/2);
        Node bottomMiddle = CreateNode(_rows/2, _columns);

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
        _enemySpawnPoint = middle;

        _mapPaint.setColor(Color.argb(255,80,80,80));
        _mapPaint.setStrokeWidth(5f);
    }

    private Node CreateNode(float x, float y) {
        Vector2D mapCoord = getMapCoord(x, y);
        Node newNode = new Node(mapCoord.x(), mapCoord.y());
        _nodes.add(newNode);
        return newNode;
    }

    private Edge CreateEdge(Node from, Node to, CardinalDirection dir) {
        Edge edge = from.connectTo(to, dir);
        _edges.add(edge);
        return edge;
    }

    public Vector2D getMapCoord(float coordX, float coordY) {

        float xStep = coordX;
        float x = xStep * (_width / _columns);

        float yStep = coordY;
        float y = yStep * (_height / _rows);

        return new Vector2D(x, y).add(_offset);
    }

    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(_background, 0, 0, _bgPaint);

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
