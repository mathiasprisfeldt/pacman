package me.mathiasprisfeldt.pacman.Map;

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
    private final boolean DRAW_BACKGROUND = false;
    private final boolean DEBUG = false;

    private GameWorld _gameWorld;
    private Bitmap _background;

    private Paint _debugPaint = new Paint();
    private Paint _mapPaint = new Paint();
    private Paint _bgPaint = new Paint();

    private ArrayList<Node> _debugNodes = new ArrayList<>();
    private ArrayList<Node> _nodes = new ArrayList<>();
    private ArrayList<Edge> _edges = new ArrayList<>();

    private Node _pacmanSpawnPoint;
    private Node _enemySpawnPoint;

    private Vector2D[] _debugRowsIndicator;
    private Vector2D[] _debugColumnsIndicator;

    private Vector2D[] _rowOffsets;
    private Vector2D[] _columnsOffsets;
    private Vector2D _offset = new Vector2D(60, 60);
    private Vector2D _mapSize;
    private int _width;
    private int _height;
    private int _columns = 15;
    private int _rows = 15;

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
        _rowOffsets = new Vector2D[_rows + 1];
        _columnsOffsets = new Vector2D[_columns + 1];

        _debugRowsIndicator = new Vector2D[_rows + 1];
        _debugColumnsIndicator = new Vector2D[_columns + 1];

        CreateMap0();

        if (DEBUG)
            CreateDebugGrid();

        _debugPaint.setColor(Color.argb(255,80,80,80));
        _debugPaint.setStrokeWidth(5f);

        _mapPaint.setTextSize(40);
        _mapPaint.setColor(Color.argb(255,255,80,80));
        _mapPaint.setStrokeWidth(5f);
    }

    private void CreateDebugGrid() {
        for (int y = 0; y < _columns + 1; y++) {
            for (int x = 0; x < _rows + 1; x++) {
                Node newNode = CreateDebugNode(x, y);

                if (x == 0)
                    _debugColumnsIndicator[y] = newNode.getPosition();

                if (y == _rows)
                    _debugRowsIndicator[x] = newNode.getPosition();
            }
        }
    }

    private void CreateMap0() {
        _rowOffsets[2] = new Vector2D(0, 5);
        _rowOffsets[4] = new Vector2D(0, -15);
        _rowOffsets[5] = new Vector2D(0, 25);
        _rowOffsets[9] = new Vector2D(0, -35);
        _rowOffsets[10] = new Vector2D(0, 10);
        _rowOffsets[12] = new Vector2D(0, -25);
        _rowOffsets[13] = new Vector2D(0, 25);

        _columnsOffsets[1] = new Vector2D(15, 0);
        _columnsOffsets[5] = new Vector2D(-5, 0);
        _columnsOffsets[6] = new Vector2D(35, 0);
        _columnsOffsets[7] = new Vector2D(35, 0);
        _columnsOffsets[8] = new Vector2D(30, 0);
        _columnsOffsets[10] = new Vector2D(5, 0);
        _columnsOffsets[14] = new Vector2D(-10, 0);

        Node plySpawn = CreateNode(0, 0); //0
        CreateNode(3, 0); //1
        CreateNode(6, 0); //2
        CreateNode(8, 0); //3
        CreateNode(12, 0); //4
        CreateNode(15, 0); //5

        CreateNode(0, 2);
        CreateNode(3, 2);
        CreateNode(5, 2);
        CreateNode(6, 2);
        CreateNode(8, 2);
        CreateNode(10, 2);
        CreateNode(12, 2);
        CreateNode(15, 2);

        CreateNode(0, 4);
        CreateNode(3, 4);
        CreateNode(5, 4);
        CreateNode(6, 4);
        CreateNode(8, 4);
        CreateNode(10, 4);
        CreateNode(12, 4);
        Node enemySpawn = CreateNode(15, 4);

        CreateNode(5, 5);
        CreateNode(6, 5);
        CreateNode(8, 5);
        CreateNode(10, 5);

        CreateNode(0, 7);
        CreateNode(3, 7);
        CreateNode(5, 7);
        CreateNode(10, 7);
        CreateNode(12, 7);
        CreateNode(15, 7);

        CreateNode(5, 9);
        CreateNode(10, 9);

        CreateNode(0, 10);
        CreateNode(3, 10);
        CreateNode(5, 10);
        CreateNode(6, 10);
        CreateNode(8, 10);
        CreateNode(10, 10);
        CreateNode(12, 10);
        CreateNode(15, 10);

        CreateNode(0, 12);
        CreateNode(1, 12);
        CreateNode(3, 12);
        CreateNode(5, 12);
        CreateNode(6, 12);
        CreateNode(8, 12);
        CreateNode(10, 12);
        CreateNode(12, 12);
        CreateNode(14, 12);
        CreateNode(15, 12);

        CreateNode(0, 13);
        CreateNode(1, 13);
        CreateNode(3, 13);
        CreateNode(5, 13);
        CreateNode(6, 13);
        CreateNode(8, 13);
        CreateNode(10, 13);
        CreateNode(12, 13);
        CreateNode(14, 13);
        CreateNode(15, 13);

        CreateNode(0, 15);
        CreateNode(6, 15);
        CreateNode(8, 15);
        CreateNode(15, 15);

        //Middle enemy spawn
        CreateNode(7, 5);
        CreateNode(6, 7);
        CreateNode(7, 7);
        CreateNode(8, 7);

        CreateConnection(0, 1, 2);
        CreateConnection(0, 6, 3);
        CreateConnection(1, 7, 3);
        CreateConnection(1, 2, 2);
        CreateConnection(2, 9, 3);
        CreateConnection(3, 10, 3);
        CreateConnection(3, 4, 2);
        CreateConnection(4, 12, 3);
        CreateConnection(4, 5, 2);
        CreateConnection(5, 13, 3);

        CreateConnection(6, 7, 2);
        CreateConnection(7, 8, 2);
        CreateConnection(8, 9, 2);
        CreateConnection(9, 10, 2);
        CreateConnection(10, 11, 2);
        CreateConnection(11, 12, 2);
        CreateConnection(12, 13, 2);

        CreateConnection(6, 14, 3);
        CreateConnection(14, 15, 2);
        CreateConnection(7, 15, 3);
        CreateConnection(8, 16, 3);
        CreateConnection(11, 19, 3);
        CreateConnection(12, 20, 3);
        CreateConnection(13, 21, 3);

        CreateConnection(16, 17, 2);
        CreateConnection(18, 19, 2);
        CreateConnection(20, 21, 2);
        CreateConnection(20, 30, 3);

        CreateConnection(17, 23, 3);
        CreateConnection(18, 24, 3);

        CreateConnection(22, 28, 3);
        CreateConnection(25, 29, 3);
        CreateConnection(22, 23, 2);
        CreateConnection(23, 66, 2);
        CreateConnection(66, 24, 2);
        CreateConnection(24, 25, 2);

        CreateConnection(26, 27, 2);
        CreateConnection(27, 28, 2);
        CreateConnection(27, 35, 3);
        CreateConnection(15, 27, 3);
        CreateConnection(28, 32, 3);
        CreateConnection(29, 33, 3);
        CreateConnection(29, 30, 2);
        CreateConnection(30, 31, 2);
        CreateConnection(30, 40, 3);
        CreateConnection(32, 33, 2);
        CreateConnection(66, 68, 3);
        CreateConnection(67, 68, 2);
        CreateConnection(68, 69, 2);

        CreateConnection(34, 35, 2);
        CreateConnection(35, 36, 2);
        CreateConnection(36, 37, 2);
        CreateConnection(38, 39, 2);
        CreateConnection(39, 40, 2);
        CreateConnection(40, 41, 2);

        CreateConnection(32, 36, 3);
        CreateConnection(33, 39, 3);
        CreateConnection(34, 42, 3);
        CreateConnection(35, 44, 3);
        CreateConnection(37, 46, 3);
        CreateConnection(38, 47, 3);
        CreateConnection(40, 49, 3);
        CreateConnection(41, 51, 3);

        CreateConnection(42, 43, 2);
        CreateConnection(44, 45, 2);
        CreateConnection(45, 46, 2);
        CreateConnection(46, 47, 2);
        CreateConnection(47, 48, 2);
        CreateConnection(48, 49, 2);
        CreateConnection(50, 51, 2);

        CreateConnection(52, 53, 2);
        CreateConnection(53, 54, 2);
        CreateConnection(55, 56, 2);
        CreateConnection(57, 58, 2);
        CreateConnection(59, 60, 2);
        CreateConnection(60, 61, 2);
        CreateConnection(62, 63, 2);
        CreateConnection(63, 64, 2);
        CreateConnection(64, 65, 2);

        CreateConnection(43, 53, 3);
        CreateConnection(44, 54, 3);
        CreateConnection(45, 55, 3);
        CreateConnection(48, 58, 3);
        CreateConnection(49, 59, 3);
        CreateConnection(50, 60, 3);
        CreateConnection(52, 62, 3);
        CreateConnection(56, 63, 3);
        CreateConnection(57, 64, 3);
        CreateConnection(61, 65, 3);

        _pacmanSpawnPoint = plySpawn;
        _enemySpawnPoint = enemySpawn;
    }

    private void CreateMap1() {
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
    }

    private Node CreateDebugNode(float x, float y) {
        Vector2D mapCoord = getMapCoord(x, y);
        Node newNode = new Node(mapCoord.x(), mapCoord.y());
        _debugNodes.add(newNode);
        return newNode;
    }

    private void CreateConnection(int index, int otherIndex, int dir) {
        CreateEdge(_nodes.get(index), _nodes.get(otherIndex), CardinalDirection.fromInt(dir));
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
        float calcedWidth = _width - _offset.x() * 2;
        float calcedHeight = _height - _offset.y() * 2;

        float xStep = coordX;
        float x = xStep * (calcedWidth / _columns);

        float yStep = coordY;
        float y = yStep * (calcedHeight / _rows);

        Vector2D pos = new Vector2D(x, y).add(_offset);

        Vector2D rowOffset = _columnsOffsets[(int) coordX];
        if (rowOffset != null)
            pos = pos.add(rowOffset);

        Vector2D columnOffset = _rowOffsets[(int) coordY];
        if (columnOffset != null)
            pos = pos.add(columnOffset);

        return pos;
    }

    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(_background, 0, 0, _bgPaint);

        if (DEBUG) {
            for (int i = 0; i < _debugRowsIndicator.length; i++) {
                Vector2D vector2D = _debugRowsIndicator[i];
                if (vector2D != null) {
                    canvas.drawText(String.valueOf(i), vector2D.x(), vector2D.y() + 55, _mapPaint);
                }
            }

            for (int i = 0; i < _debugColumnsIndicator.length; i++) {
                Vector2D vector2D = _debugColumnsIndicator[i];
                if (vector2D != null) {
                    canvas.drawText(String.valueOf(i), vector2D.x(), vector2D.y(), _mapPaint);
                }
            }

            for (Node node : _debugNodes) {
                Vector2D pos = node.getPosition();
                canvas.drawCircle(pos.x(), pos.y(), 10f, _debugPaint);
            }
        }

        if (DRAW_BACKGROUND) {

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

}
