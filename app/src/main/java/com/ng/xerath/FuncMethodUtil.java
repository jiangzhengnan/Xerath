package com.ng.xerath;

import com.ng.xerath.utils.LogUtil;
import com.ng.xerathcore.CalculateTime;

/**
 * 描述:
 *
 * @author Jzn
 * @date 2021/9/4
 */
class FuncMethodUtil {


    /**
     * 使用注解统计耗时方法
     */
    @CalculateTime
    public static void CalculateTimeMethod() {
        LogUtil.println("CalculateTimeMethod start ...");
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.println("CalculateTimeMethod end ...");
    }
}
