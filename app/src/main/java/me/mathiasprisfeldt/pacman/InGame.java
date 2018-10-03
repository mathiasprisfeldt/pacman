package me.mathiasprisfeldt.pacman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class InGame extends AppCompatActivity {

    private GameWorld _gameWorld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);
        _gameWorld = findViewById(R.id.game_world);

        final Button pauseBtn = findViewById(R.id.in_game_pause);
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isPaused = !_gameWorld.getIsPaused();
                _gameWorld.setIsPaused(isPaused);
                pauseBtn.setText(isPaused ? "RESUME" : "PAUSE");
            }
        });

        final Button restartBtn = findViewById(R.id.in_game_reset);
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameManager.getInstance().resetGame();
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GameManager.getInstance().saveHighscore();
    }
}
