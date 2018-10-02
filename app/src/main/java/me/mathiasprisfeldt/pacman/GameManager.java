package me.mathiasprisfeldt.pacman;

import android.content.Context;
import android.widget.TextView;

public class GameManager {
    private static final GameManager ourInstance = new GameManager();

    public static GameManager getInstance() {
        return ourInstance;
    }

    private GameWorld _gameWorld;
    private int _lives = 3;
    private int _coins;
    private int _maxCoins;
    private int _score;
    private boolean _gameover;

    public void setGameWorld(GameWorld _gameWorld) {
        this._gameWorld = _gameWorld;
    }

    public void addCoin() {
        _coins++;
        _score += 10;

        if (_coins >= _maxCoins) {
            roundWon();
        }
    }

    private int getScore() {
        return _score;
    }

    private GameManager() {
        _lives = 3;
    }

    public void setCoinAmount(int maxCoins) {
        _coins = 0;
        _maxCoins = maxCoins;
    }

    public void onUI(InGame inGame) {
        TextView scoreTxt = inGame.findViewById(R.id.in_game_score);
        scoreTxt.setText("SCORE\n" + getScore());

        TextView livesTxt = inGame.findViewById(R.id.in_game_live);
        livesTxt.setText("LIVES\n" + _lives);
    }

    public void restartRound() {
        if (_gameover) {
            _gameWorld.setIsPaused(true);
            return;
        }

        _coins = 0;
        _gameWorld.restartRound();
    }

    private void roundWon() {
        _score += 1000;
        restartRound();
    }

    public void onPacmanDied() {
        _lives--;

        if (_lives <= 0)
            _gameover = true;

        restartRound();
    }

    public void resetGame() {
        _gameover = false;
        _gameWorld.setIsPaused(false);

        _score = 0;
        _lives = 3;
        restartRound();
    }
}
