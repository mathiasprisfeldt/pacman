package me.mathiasprisfeldt.pacman;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {
    private static final SoundManager ourInstance = new SoundManager();

    public static SoundManager getInstance() {
        return ourInstance;
    }

    SoundPool _soundPool;

    public static int eat;
    public static int roundStart;
    public static int death;

    void initialize(Context context) {
        _soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 100);

        _soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                playSound(roundStart);
            }
        });

        eat = _soundPool.load(context, R.raw.eat, 1);
        roundStart = _soundPool.load(context, R.raw.round_start, 20);
        death = _soundPool.load(context, R.raw.death, 1);
    }

    public void playEatSound() {
        playSound(eat, 1);
    }

    public void playSound(int soundId) {
        _soundPool.play(soundId, 1, 1, 1, 0, 1);
    }

    public void playSound(int soundId, float rate) {
        _soundPool.play(soundId, 1, 1, 1, 0, rate);
    }

}
