package com.ng.xerath.func;

import com.ng.xerath.func.chain.ChainA;
import com.ng.xerath.func.chain.ChainB;
import com.ng.xerath.func.chain.ChainC;
import com.ng.xerath.func.chain.ChainD;
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
     * 抓取调用链路
     * 注解添加在最上层基类ChainA中
     */
    public static void testCallChain() {
        CoreHelper.catchLog("调用链路测试开始");
        ChainA chainA = new ChainA();
        chainA.a_params = "chainA";
        ChainB chainB = new ChainB();
        chainB.b_params = "chainB";
        ChainC chainC = new ChainC();
        chainC.c_params = "chainC";
        ChainD chainD = new ChainD();
        chainA.child = chainB;
        chainB.child = chainC;
        chainC.child = chainD;
        chainD.getCallChain();
        //hook
        chainA.doSomeThing();
        chainA.doSomeThing2();
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
