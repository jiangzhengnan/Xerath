package com.ng.xerath.asm;

import com.ng.xerathcore.CoreHelper;

import org.json.JSONObject;

/**
 * @author : jiangzhengnan.jzn
 * @creation : 2021/09/16
 * @description :
 */
public class ASMShow {

    public void test(JSONObject methodName) {
        System.out.println("测试～！");

        CoreHelper.catchCallChain(this);
    }

}
