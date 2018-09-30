package me.mathiasprisfeldt.pacman.Interfaces;

import android.view.VelocityTracker;

import me.mathiasprisfeldt.pacman.Types.Vector2D;

public interface Touchable {
    void touchDown(VelocityTracker velTracker);
    void touchMove(VelocityTracker velTracker, Vector2D vel);
    void touchUp(VelocityTracker velTracker);
}
