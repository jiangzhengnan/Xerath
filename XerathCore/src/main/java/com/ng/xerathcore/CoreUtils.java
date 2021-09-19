package com.ng.xerathcore;

import android.util.Log;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2021/09/01
 * @description :
 * todo
 * 存储数据
 */
public class CoreUtils {

    public static void handleException(Exception exception) {
        if (exception == null) {
            return;
        }
        Log.d("ExceptionHandler", "handleException:", exception);
    }

    public static void catchLog(String s) {
        System.out.println("XerathCore 工程 抓取到了日志 coreutils: " + s);
    }
}
