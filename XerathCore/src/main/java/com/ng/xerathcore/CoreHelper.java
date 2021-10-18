package com.ng.xerathcore;

import android.util.Log;

import androidx.annotation.Nullable;

import com.ng.xerathcore.utils.LogUtil;
import com.ng.xerathcore.utils.ReflectUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    //暂时存储单个方法中的临时变量
    private static List<JSONObject> mCatchJsonFiled = new ArrayList<>();

    public static void catchJsonFiled(Object jsonObject) {
        if (onCoreHelperListener != null) {
            onCoreHelperListener.onCatchLog("catchJsonFiled  :" + jsonObject == null ? "null" : jsonObject.toString());
        }
        if (jsonObject instanceof JSONObject) {
            if (onCoreHelperListener != null) {
                onCoreHelperListener.onCatchLog("抓到了json:" + jsonObject.toString());
            }
        }
    }

    public static void catchTempJsonFiled(Object jsonObject) {
        if (onCoreHelperListener != null) {
            onCoreHelperListener.onCatchLog("抓到了obj:" + jsonObject.getClass().getName() + " " + jsonObject.toString());
        }

        if (jsonObject == null) {
            return;
        }
        if (jsonObject.getClass().isArray()) {
            return;
        }
        if (jsonObject instanceof JSONObject) {
            mCatchJsonFiled.add((JSONObject) jsonObject);
        }
    }

    public static void catchTempJsonFiled(boolean value) {
        if (onCoreHelperListener != null) {
            onCoreHelperListener.onCatchLog("抓到了 boolean:" + value);
        }
    }

    public static void catchTempJsonFiled(int value) {
        if (onCoreHelperListener != null) {
            onCoreHelperListener.onCatchLog("抓到了 int:" + value);
        }
    }

    public static void catchTempJsonFiled(short value) {
        if (onCoreHelperListener != null) {
            onCoreHelperListener.onCatchLog("抓到了 short:" + value);
        }
    }

    public static void catchTempJsonFiled(byte value) {
        if (onCoreHelperListener != null) {
            onCoreHelperListener.onCatchLog("抓到了 byte:" + value);
        }
    }

    public static void catchTempJsonFiled(char value) {
        if (onCoreHelperListener != null) {
            onCoreHelperListener.onCatchLog("抓到了 char:" + value);
        }
    }

    public static void catchTempJsonFiled(long value) {
        if (onCoreHelperListener != null) {
            onCoreHelperListener.onCatchLog("抓到了 long:" + value);
        }
    }

    public static void catchTempJsonFiled(double value) {
        if (onCoreHelperListener != null) {
            onCoreHelperListener.onCatchLog("抓到了 double:" + value);
        }
    }

    public static void catchTempJsonFiled(float value) {
        if (onCoreHelperListener != null) {
            onCoreHelperListener.onCatchLog("抓到了 float:" + value);
        }
    }

    public static void onCatchTempJsonFileFinish() {
        if (mCatchJsonFiled.size() == 0) {
            return;
        }

        for (JSONObject jsonObject : mCatchJsonFiled) {
            if (jsonObject != null) {
                if (onCoreHelperListener != null) {
                    onCoreHelperListener.onCatchLog("抓到了json:" + jsonObject);
                }
            }
        }
        mCatchJsonFiled.clear();
    }
}
