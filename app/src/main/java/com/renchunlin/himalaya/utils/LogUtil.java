package com.renchunlin.himalaya.utils;

import android.util.Log;

/*
 * class title: Log工具类
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/1.
 * PS: Not easy to write code, please indicate.
 */
public class LogUtil {
    public static String sTAG = "LogUtil";

    //控制是否要输出log
    public static boolean sIsRelease = false;

    /**
     * 如果是要发布了，可以在application里面把这里release一下，这样子就没有log输出了
     */
    public static void init(String baseTag, boolean isRelease) {
        sTAG = baseTag;
        sIsRelease = isRelease;
    }

    public static void d(String TAG, String content) {
        if (!sIsRelease) {
            Log.d("[" + sTAG + "]" + TAG, content);
        }
    }

    public static void v(String TAG, String content) {
        if (!sIsRelease) {
            Log.d("[" + sTAG + "]" + TAG, content);
        }
    }

    public static void i(String TAG, String content) {
        if (!sIsRelease) {
            Log.d("[" + sTAG + "]" + TAG, content);
        }
    }

    public static void w(String TAG, String content) {
        if (!sIsRelease) {
            Log.d("[" + sTAG + "]" + TAG, content);
        }
    }

    public static void e(String TAG, String content) {
        if (!sIsRelease) {
            Log.d("[" + sTAG + "]" + TAG, content);
        }
    }
}
