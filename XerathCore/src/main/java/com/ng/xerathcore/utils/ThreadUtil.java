package com.ng.xerathcore.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2021/09/20
 * @description :
 */
public class ThreadUtil {
    /**
     * 主线程的Handler
     */
    private static Handler sMainHandler;

    private static Handler getMainHandler() {
        if (null == sMainHandler) {
            sMainHandler = new Handler(Looper.getMainLooper());
        }
        return sMainHandler;
    }

    public static void runOnUiThread(Runnable action) {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            Handler handler = getMainHandler();
            handler.post(action);
        } else {
            action.run();
        }
    }

    public static void runThread(Runnable action) {
        new Thread(action).start();
    }
}
