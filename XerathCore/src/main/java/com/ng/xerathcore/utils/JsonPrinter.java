package com.ng.xerathcore.utils;


import com.ng.xerathcore.CoreHelper;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2021/10/06
 * @description :
 */
public class JsonPrinter {

    public static void print(String name, Object value) {
        if (name != null && value != null) {
            CoreHelper.catchLog("输入json . name:" + name + "  value:" + value.toString());
        } else {
            CoreHelper.catchLog("输入json 有空的");
        }
    }
}
