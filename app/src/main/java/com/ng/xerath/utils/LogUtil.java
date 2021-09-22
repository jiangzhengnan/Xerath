package com.ng.xerath.utils;

import android.util.Log;

/**
 * 描述:
 *
 * @author Jzn
 * @date 2021/9/4
 */
public class LogUtil {
    private static final String TAG = "[ Xerath ] ";

    public static void d(String s) {
        Log.d(TAG, s);
    }

    public static void println(String s) {
        System.out.println(s);
    }

}
