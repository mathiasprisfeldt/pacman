package me.mathiasprisfeldt.pacman;

import android.content.Context;
import android.widget.TextView;

public class GameManager {
    private static final GameManager ourInstance = new GameManager();

    public static GameManager getInstance() {
        return ourInstance;
    }

    private GameWorld _gameWorld;
    private int _lives;
    private int _coins;
    private int _maxCoins;
    private int _score;
    private int highscore;
    private boolean _gameover;
    private String _statusTxt;

    public void setStatusTxt(String _statusTxt) {
        this._statusTxt = _statusTxt;
    }

    public void setGameWorld(GameWorld _gameWorld) {
        this._gameWorld = _gameWorld;
        highscore = _gameWorld.getContext().getSharedPreferences("pacman", Context.MODE_PRIVATE).getInt("highscore", 0);
        resetGame();
    }

    public void addCoin(int score) {
        _coins++;
        set_score(_score + score);

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
        TextView scoreTxt = inGame.findViewById(R.id.in_game_score_txt);
        scoreTxt.setText(String.valueOf(getScore()));

        TextView livesTxt = inGame.findViewById(R.id.in_game_live_txt);
        livesTxt.setText(String.valueOf(_lives));

        TextView highscoreTxt = inGame.findViewById(R.id.in_game_highscore_txt);
        highscoreTxt.setText(String.valueOf(highscore));

        TextView statusTxt = inGame.findViewById(R.id.in_game_status);
        statusTxt.setText(_statusTxt);
    }

    public void restartRound(boolean clearCoins) {
        if (_gameover) {
            saveHighscore();
            return;
        }

        _coins = _gameover ? 0 : _coins;
        _gameWorld.restartRound(_gameover || clearCoins);
        _gameWorld.startCountdown();
    }

    private void roundWon() {
        set_score(_score + 500);
        _coins = 0;
        SoundManager.getInstance().playSound(SoundManager.win, 1.25f);
        restartRound(true);
    }

    public void onPacmanDied() {
        SoundManager.getInstance().playSound(SoundManager.death);
        _lives--;

        if (_lives <= 0) {
            _gameover = true;
            _statusTxt = "GAMEOVER!";
        }

        restartRound(false);
    }

    public void resetGame() {
        saveHighscore();

        _gameover = false;
        _gameWorld.setIsPaused(false);

        set_score(0);
        _lives = 3;
        _coins = 0;
        restartRound(true);
    }

    public void saveHighscore() {
        if (_score < highscore)
            return;

        _statusTxt = "NEW HIGHSCORE!";

        _gameWorld.getContext().getSharedPreferences("pacman", Context.MODE_PRIVATE)
                .edit().putInt("highscore", highscore =_score).apply();
    }

    public void set_score(int _score) {
        this._score = _score;

        if (_score > highscore)
            highscore = _score;
    }
}
