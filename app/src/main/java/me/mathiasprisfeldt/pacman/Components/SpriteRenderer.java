package me.mathiasprisfeldt.pacman.Components;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import me.mathiasprisfeldt.pacman.Interfaces.Drawable;
import me.mathiasprisfeldt.pacman.Interfaces.Updateable;

public class SpriteRenderer extends Component implements Drawable, Updateable {

    private float x, y;
    private Paint paint = new Paint();
    private Rect rect = new Rect(0, 0, 200, 200);

    @Override
    public void onDraw(Canvas canvas) {
        paint.setStrokeWidth(10);
        paint.setColor(Color.RED);

        canvas.drawRect(rect, paint);
    }

    @Override
    public void onUpdate(float deltaTime) {

    }
}
