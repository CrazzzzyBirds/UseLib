package com.heima.uselib;

public class JniUtil {
    static {
        System.loadLibrary("native-lib");
    }

    public static native void getInt();

}
