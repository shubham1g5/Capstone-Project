package com.example.shubham.sixfourfantasy.util;

import org.jetbrains.annotations.NotNull;

public class ArrayUtils {

    public static String[] addAll(@NotNull String[] a, @NotNull String[] b) {
        int aLen = a.length;
        int bLen = b.length;
        String[] c = new String[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
}
