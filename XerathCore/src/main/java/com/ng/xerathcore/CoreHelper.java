package com.ng.xerathcore;

import android.util.Log;

import com.ng.xerathcore.utils.LogUtil;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2021/09/01
 * @description :
 * 数据收集入口
 * TODO 存储数据
 */
public class CoreHelper {
    private final static String TAG = "CoreHelper";

    /**
     * 抓取异常
     */
    public static void handleException(Exception exception) {
        if (exception == null) {
            return;
        }
        LogUtil.print(TAG + " 抓取到了异常: " + Log.getStackTraceString(exception));
    }

    /**
     * 抓取日志
     */
    public static void catchLog(String s) {
        LogUtil.print(TAG + " 抓取到了日志: " + s);
    }


}
