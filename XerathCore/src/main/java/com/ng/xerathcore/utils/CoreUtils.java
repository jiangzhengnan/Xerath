package com.ng.xerathcore.utils;

import com.ng.xerathcore.constants.PlugConstant;

/**
 * @author : jiangzhengnan.jzn
 * @creation : 2021/09/19
 * @description :
 */
public class CoreUtils {

    public static boolean needLimitCall(long time) {
        long lastTime = SpUtil.getLongValue(PlugConstant.SpName.LIMIT_CALL_TIME,0);
        if (System.currentTimeMillis() - lastTime < time) {
            return true;
        }
        SpUtil.putLongValue(PlugConstant.SpName.LIMIT_CALL_TIME,System.currentTimeMillis());
        return false;
    }

}
