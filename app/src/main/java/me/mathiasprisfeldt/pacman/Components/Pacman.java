package me.mathiasprisfeldt.pacman.Components;

import android.content.res.Resources;
import android.util.Log;
import android.view.VelocityTracker;

import me.mathiasprisfeldt.pacman.GameObject;
import me.mathiasprisfeldt.pacman.Interfaces.Touchable;
import me.mathiasprisfeldt.pacman.Interfaces.Updateable;
import me.mathiasprisfeldt.pacman.Types.Vector2D;

public class Pacman extends Component implements Updateable, Touchable {
    private SpriteRenderer _renderer;
    private Vector2D direction = Vector2D.Zero;

    public Pacman(GameObject gameObject, SpriteRenderer renderer) {
        super(gameObject);
        _renderer = renderer;
    }

    @Override
    public void onUpdate(float deltaTime) {
        super.onUpdate(deltaTime);

        Vector2D pos = _gameObject.getTransform().getPosition();
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels - _renderer.getSize().x();
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        if (pos.x() < 0)
            direction = new Vector2D(1, direction.y());
        else if (pos.x() > screenWidth)
            direction = new Vector2D(-1, direction.y());

        if (pos.y() < 0)
            direction = new Vector2D(direction.x(), 1);
        else if (pos.y() > screenHeight)
            direction = new Vector2D(direction.x(), -1);

        _gameObject.getTransform().setVelocity(direction.multiply(500));
    }

    @Override
    public void touchDown(VelocityTracker velTracker) {

    }

    @Override
    public void touchMove(VelocityTracker velTracker, Vector2D vel) {
        if (vel.sqrMagnitude() > 2000000) {
            vel.Normalize();
            vel.ToCardinal();
            direction = vel;
        }
    }

    @Override
    public void touchUp(VelocityTracker velTracker) {

    }
}
