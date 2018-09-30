package me.mathiasprisfeldt.pacman.Components;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import me.mathiasprisfeldt.pacman.GameObject;
import me.mathiasprisfeldt.pacman.Interfaces.Drawable;
import me.mathiasprisfeldt.pacman.Types.Point2D;
import me.mathiasprisfeldt.pacman.Types.Vector2D;

public class SpriteRenderer extends Component implements Drawable {

    private Bitmap _bitmap;
    private Paint _paint = new Paint();
    private Vector2D _pivot;
    private Point2D _size;

    public Point2D getSize() {
        return _size;
    }

    public void setBitmap(int bitmapId) {
        _bitmap = BitmapFactory.decodeResource(_gameObject.getGameWorld().getResources(), bitmapId);
    }

    private void setSize(int width, int height) {
        _size = new Point2D(width, height);

        float widthScale = (float) _size.x() / _bitmap.getWidth();
        float heightScale = (float) _size.y() / _bitmap.getHeight();

        _gameObject.getTransform().setScale(new Vector2D(widthScale, heightScale));
    }

    private void setSize(float width, float height) {
        float bitmapWidth = width * _bitmap.getWidth();
        float bitmapHeight = height * _bitmap.getHeight();

        _size = new Point2D((int) bitmapWidth, (int) bitmapHeight);

        _gameObject.getTransform().setScale(new Vector2D(width, height));
    }

    public void setPivot(Vector2D pivot) {
        _pivot = pivot;

        Transform tr = _gameObject.getTransform();
        Vector2D pos = tr.getPosition();

        float left = pos.x() - _size.x() * _pivot.x();
        float top = pos.y() - _size.y() * _pivot.y();

        tr.setOffset(new Vector2D(left, top));
    }

    public SpriteRenderer(GameObject gameObject, int bitmap, int width, int height) {
        super(gameObject);
        setBitmap(bitmap);
        setSize(width, height);
        setPivot(new Vector2D(0.5f, 0.5f));
    }

    public SpriteRenderer(GameObject gameObject, int bitmap) {
        super(gameObject);
        setBitmap(bitmap);
        setSize(1f, 1f);
        setPivot(new Vector2D(0.5f, 0.5f));
    }

    @Override
    public void onDraw(Canvas canvas) {
        Transform tr = _gameObject.getTransform();
        canvas.drawBitmap(_bitmap, tr.getMatrix(), _paint);
    }
}
