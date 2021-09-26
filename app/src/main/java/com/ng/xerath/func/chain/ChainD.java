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
    public JSONObject chainDJson;

    /**
     * 获取指定返回值或者指定参数的调用链路
     * 不能用static修饰!
     */
    public JSONObject getCallChain() {
        chainDJson = new JSONObject();
        try {
            chainDJson.put("ChainD_key", "ChainD_value");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chainDJson;
    }
}
