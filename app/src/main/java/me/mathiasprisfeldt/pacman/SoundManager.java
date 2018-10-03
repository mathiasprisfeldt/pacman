package me.mathiasprisfeldt.pacman;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundManager {
    private static final SoundManager ourInstance = new SoundManager();

    public static SoundManager getInstance() {
        return ourInstance;
    }

    SoundPool _soundPool;

    int eatSound;

    void initialize(Context context) {
        _soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 100);
        eatSound = _soundPool.load(context, R.raw.eat, 1);
    }

    public void playEatSound() {
        if (_soundPool != null)
            _soundPool.play(eatSound, 1, 1, 1, 0, 1);
    }
}
