package com.ng.xerathcore.func;

import android.util.Log;

import com.ng.xerathcore.CoreHelper;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2022/05/29
 * @description :
 * 超过5秒自动重置时间计算
 */
public class TrackMethodStackHelper {
    private static long firstTime = 0;
    private static long lastTime = 0;

    public static void callMethod(String methodInfo) {
        String timeStr = "";
        long timeConsuming = 0;
        long nowTime = System.currentTimeMillis();
        if (nowTime - firstTime > 5000) {
            //当前为初次调用
            lastTime = nowTime;
            firstTime = nowTime;
        }
        timeConsuming = nowTime - lastTime;
        lastTime = nowTime;

        timeStr = "上一个方法耗时:" + timeConsuming + "ms    总时长:" + (nowTime - firstTime) + "ms";
        String result = timeStr + "\n" + methodInfo;
        Log.d("xerath", result);
    }
}
