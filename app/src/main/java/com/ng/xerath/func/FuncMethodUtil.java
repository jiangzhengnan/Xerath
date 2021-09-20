package com.ng.xerath.func;

import com.ng.xerath.utils.LogUtil;
import com.ng.xerathcore.annotation.Xerath_CalculateTime;
import com.ng.xerathcore.annotation.Xerath_LimitCall;
import com.ng.xerathcore.annotation.Xerath_TryCatch;

/**
 * 描述:
 *
 * @author Jzn
 * @date 2021/9/4
 */
public class FuncMethodUtil {

    /**
     * 耗时方法
     */
    @Xerath_CalculateTime
    public static void calculateTimeMethod() {
        LogUtil.println("CalculateTimeMethod start ...");
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.println("CalculateTimeMethod end ...");
    }

    /**
     * try-catch void
     */
    @Xerath_TryCatch
    public static void tryCatchMethod() {
        int a = 1 / 0;
    }

    /**
     * try-catch boolean
     */
    @Xerath_TryCatch
    public static boolean tryCatchMethodReturnBoolean() {
        int a = 1 / 0;
        return false;
    }

    /**
     * 限制重复调用 void
     */
    @Xerath_LimitCall(time = 8000L)
    public static void doubleClick() {
        System.out.println("click");
    }

    /**
     * 限制重复调用
     */
    @Xerath_LimitCall(time = 1000L)
    public static boolean doubleClickReturnBoolean() {
        System.out.println("click");
        return false;
    }
}
