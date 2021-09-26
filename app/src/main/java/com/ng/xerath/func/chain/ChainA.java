package com.ng.xerath.func.chain;

import com.ng.xerathcore.annotation.Xerath_CallChain;

/**
 * @author : jiangzhengnan.jzn
 * @creation : 2021/09/25
 * @description :
 */
public class ChainA {
    public String a_params = "a_params";

    public ChainB child;

    @Xerath_CallChain
    public void doSomeThing() {
        //hook完整调用链路
        //CoreHelper.catchCallChain(this);
    }

    @Xerath_CallChain
    public static void doSomeThing2() {
        //hook完整调用链路,static下，默认会不处理,防止crash
        //CoreHelper.catchCallChain(this);
    }
}
