package me.mathiasprisfeldt.pacman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InGame extends AppCompatActivity {

    private GameWorld _gameWorld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);
        _gameWorld = findViewById(R.id.game_world);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (_gameWorld != null)
            _gameWorld.setIsPaused(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (_gameWorld != null)
            _gameWorld.setIsPaused(false);
    }
}
