package com.ng.xerath.func.member;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2021/10/06
 * @description :
 */
public class TestMember1 {
    public JSONObject json1;
   public static JSONObject staticJson1;

    public void test() {
        try {
            json1 = new JSONObject();
            json1.put("name_1", "jzn");
            staticJson1 = new JSONObject();
            staticJson1.put("name_2", "lili");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
