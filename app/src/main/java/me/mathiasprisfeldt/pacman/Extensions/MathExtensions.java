package me.mathiasprisfeldt.pacman.Extensions;

public class MathExtensions {

    public static int Wrap(int value, int min, int max)
    {
        // Code from http://stackoverflow.com/a/707426/1356325
        int range_size = max - min + 1;

        if (value < min)
            value += range_size * ((min - value) / range_size + 1);

        return min + (value - min) % range_size;
    }
}
