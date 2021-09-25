package com.ng.xerath.func.chain;

import com.ng.xerathcore.CoreHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author : jiangzhengnan.jzn
 * @creation : 2021/09/25
 * @description :
 */
public class ChainD extends ChainB {

    /**
     * 获取指定返回值或者指定参数的调用链路
     * 不能用static修饰!
     */
    public JSONObject getCallChain() {
        JSONObject testJson = new JSONObject();
        try {
            testJson.put("ChainD", "ChainD");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CoreHelper.catchCallChain(this);
        return testJson;
    }
}
