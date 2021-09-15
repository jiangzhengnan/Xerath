package com.ng.xerath.test.func;

import com.ng.xerath.utils.LogUtil;
import com.ng.xerathcore.CalculateTime;
import com.ng.xerathcore.TryCatch;
import com.ng.xerathcore.Xerath_DoubleClick;

/**
 * 描述:
 *
 * @author Jzn
 * @date 2021/9/4
 */
public class FuncMethodUtil {

    /**
     * 使用注解统计耗时方法
     */
    @CalculateTime
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
     * 使用注解统计耗时方法
     */
    @TryCatch
    public static void tryCatchMethod() {
        int a = 1;
        int b = 0;
        int c = a / b;
    }

    /**
     * 重复点击
     */
    @Xerath_DoubleClick
    public static void doubleClick() {
        System.out.println("click");
    }
}
