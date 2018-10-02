package me.mathiasprisfeldt.pacman.Extensions;

import java.lang.reflect.Array;

public class ArrayExtensions {
    public static <T> T[] Trim(Class<T> type, T[] array) {
        int notNullsCount = 0;

        for (int i = 0; i < array.length; i++) {
            T t = array[i];
            if (t != null)
                notNullsCount++;
        }

        int index = 0;
        T[] notNulls = (T[]) Array.newInstance(type, notNullsCount);

        for (int i = 0; i < array.length; i++) {
            T t = array[i];
            if (t != null) {
                notNulls[index] = t;
                index++;
            }
        }

        return notNulls;
    }
}
