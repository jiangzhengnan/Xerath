package com.ng.xerath.func.member;

import com.ng.xerathcore.CoreHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2021/10/06
 * @description :
 */
public class TestMember1 {
    public JSONObject json1;
    public static JSONObject staticJson1;


//    public void test() {
//        //成员变量 和 类变量
//        try {
//            json1 = new JSONObject();
//            json1.put("name_1", "jzn");
//            staticJson1 = new JSONObject();
//            staticJson1.put("name_2", "lili");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        //临时变量
//        JSONObject json3 = new JSONObject();
//        try {
//            json3.put("name_3", "haha");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            JSONObject json4 = new JSONObject();
//            json4.put("name_4", "haha");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }


    public void test1() {
        AdSlot adSlot;
        boolean useTemplateAd = System.currentTimeMillis() > 1000;
        if (useTemplateAd) {
            adSlot = new AdSlot.Builder()
                    .build();
        } else {
            // SDK渲染请求
            adSlot = new AdSlot.Builder()
                    .build();
        }

    }

    public void test2() {
        AdSlot adSlot;
        boolean useTemplateAd = System.currentTimeMillis() > 1000;
        int a = new Random().nextInt();
        if (a == 1) {
            adSlot = new AdSlot.Builder()
                    .build();
        } else if (a == 2) {
            // SDK渲染请求
            adSlot = new AdSlot.Builder()
                    .build();
        } else if (a == 3) {
            // SDK渲染请求
            adSlot = new AdSlot.Builder()
                    .build();
        }


    }

    public void test3() {
        boolean useTemplateAd = System.currentTimeMillis() > 1000;
        AdSlot adSlot = new AdSlot.Builder()
                .build();

    }
}
