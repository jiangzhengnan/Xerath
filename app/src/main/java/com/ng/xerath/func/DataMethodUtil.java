package com.ng.xerath.func;

import com.ng.xerathcore.CoreHelper;
import com.ng.xerathcore.annotation.Xerath_CalculateTime;
import com.ng.xerathcore.annotation.Xerath_CollectParams;

import org.json.JSONObject;

/**
 * 描述:
 *
 * @author Jzn
 * @date 2021/9/23
 */
public class DataMethodUtil {
    /**
     * 参数统计
     */
    @Xerath_CollectParams
    public static String testParams(boolean boolParam,
                                    byte byteParam,
                                    char charParam,
                                    short shortParam,
                                    int intParam,
                                    long longParam,
                                    float floatParam,
                                    double doubleParam,
                                    String stringParam,
                                    int[] intArrParam,
                                    JSONObject json) {
        String result = boolParam + " " +
                byteParam + " " +
                charParam + " " +
                shortParam + " " +
                intParam + " " +
                longParam + " " +
                floatParam + " " +
                doubleParam + " " +
                stringParam + " " +
                intArrParam.length +
                json.toString();
        return result;
    }

    /**
     * 耗时方法
     */
    @Xerath_CalculateTime
    public static void calculateTimeMethod() {
        CoreHelper.catchLog("CalculateTimeMethod start ...");
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CoreHelper.catchLog("CalculateTimeMethod end ...");
    }
}
