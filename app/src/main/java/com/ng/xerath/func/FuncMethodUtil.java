package com.ng.xerath.func;

import com.ng.xerathcore.CoreHelper;
import com.ng.xerathcore.annotation.Xerath_CalculateTime;
import com.ng.xerathcore.annotation.Xerath_CollectParams;
import com.ng.xerathcore.annotation.Xerath_LimitCall;
import com.ng.xerathcore.annotation.Xerath_TryCatch;

import org.json.JSONObject;

/**
 * 描述:
 *
 * @author Jzn
 * @date 2021/9/4
 */
public class FuncMethodUtil {


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
        CoreHelper.catchLog("click");
    }

    /**
     * 限制重复调用
     */
    @Xerath_LimitCall(time = 1000L)
    public static boolean doubleClickReturnBoolean() {
        CoreHelper.catchLog("click");
        return false;
    }

}
