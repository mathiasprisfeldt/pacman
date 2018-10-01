package me.mathiasprisfeldt.pacman.Map;

import me.mathiasprisfeldt.pacman.Types.Point2D;
import me.mathiasprisfeldt.pacman.Types.Vector2D;

public enum CardinalDirection {
    None(-1),
    Left(0),
    Up(1),
    Right(2),
    Down(3);

    private int _index;

    CardinalDirection(int i) {
        _index = i;
    }

    public static CardinalDirection fromPoint(Point2D vel) {
        if (vel.x() == -1)
            return Left;

        if (vel.x() == 1)
            return Right;

        if (vel.y() == -1)
            return Up;

        if (vel.y() == 1)
            return Down;

        return None;
    }

    public CardinalDirection invert(CardinalDirection dir) {
        if (dir == Left)
            return Right;

        if (dir == Right)
            return Left;

        if (dir == Up)
            return Down;

        if (dir == Down)
            return Up;

        return None;
    }

    public int getIndex() {
        return _index;
    }

    public CardinalDirection invert() {
        return invert(this);
    }

    public Vector2D toVector() {
        switch (this) {
            case None:
                return Vector2D.Zero;
            case Left:
                return new Vector2D(-1 , 0);
            case Up:
                return new Vector2D(0, -1);
            case Right:
                return new Vector2D(1, 0);
            case Down:
                return new Vector2D(0, 1);
        }

        return Vector2D.Zero;
    }
}
