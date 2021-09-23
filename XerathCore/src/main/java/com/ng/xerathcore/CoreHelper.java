package com.ng.xerathcore;

import android.util.Log;

import androidx.annotation.Nullable;

import com.ng.xerathcore.utils.LogUtil;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2021/09/01
 * @description :
 * 数据收集入口
 * TODO 存储数据
 */
public class CoreHelper {
    private static final String TAG = "[ Xerath ] ";

    @Nullable
    public static CoreHelperListener onCoreHelperListener;

    public interface CoreHelperListener {
        void onCatchLog(@Nullable String s);
    }

    /**
     * 抓取异常
     */
    public static void handleException(Exception exception) {
        if (exception == null) {
            return;
        }
        String exceptionStr = Log.getStackTraceString(exception);
        LogUtil.print(TAG + " 抓取到了异常: " + exceptionStr);
        if (onCoreHelperListener != null) {
            onCoreHelperListener.onCatchLog(exceptionStr);
        }
    }

    /**
     * 抓取日志
     */
    public static void catchLog(@Nullable String s) {
        LogUtil.print(TAG + " 抓取到了日志: " + s);
        if (onCoreHelperListener != null) {
            onCoreHelperListener.onCatchLog(s);
        }
    }

}
