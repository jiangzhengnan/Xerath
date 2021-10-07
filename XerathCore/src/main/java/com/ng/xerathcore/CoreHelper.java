package com.ng.xerathcore;

import android.util.Log;

import androidx.annotation.Nullable;

import com.ng.xerathcore.utils.LogUtil;
import com.ng.xerathcore.utils.ReflectUtil;

import org.json.JSONObject;

/**
 * @author : jiangzhengnan.jzn
 * @creation : 2021/09/01
 * @description :
 * 数据收集入口
 * TODO 存储数据
 */
public class CoreHelper {

    @Nullable
    public static CoreHelperListener onCoreHelperListener;

    public interface CoreHelperListener {
        void onCatchLog(@Nullable String s);
    }

    /**
     * 抓取调用链路
     */
    public static void catchCallChain(@Nullable Object object) {
        if (object != null) {
            ReflectUtil.monitor(object);
        }
    }

    /**
     * 抓取异常
     */
    public static void handleException(Exception exception) {
        if (exception == null) {
            return;
        }
        String exceptionStr = Log.getStackTraceString(exception);
        LogUtil.print("抓取到了异常: " + exceptionStr);
        if (onCoreHelperListener != null) {
            onCoreHelperListener.onCatchLog(exceptionStr);
        }
    }

    /**
     * 抓取日志
     */
    public static void catchLog(String s) {
        LogUtil.print("抓取到了日志: " + s);
        if (onCoreHelperListener != null) {
            onCoreHelperListener.onCatchLog(s);
        }
    }

    public static void catchTest() {
        LogUtil.print("catchTest");
        if (onCoreHelperListener != null) {
            onCoreHelperListener.onCatchLog("catchTest");
        }
    }

    public static void catchJsonFiled(JSONObject jsonObject) {
        if (jsonObject!=null) {
            if (onCoreHelperListener != null) {
                onCoreHelperListener.onCatchLog("抓到了json:" + jsonObject);
            }
        }
    }
}
