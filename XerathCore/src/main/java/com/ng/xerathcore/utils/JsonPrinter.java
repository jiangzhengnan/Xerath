package com.ng.xerathcore.utils;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ng.xerathcore.CoreHelper;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2021/10/06
 * @description :
 */
public class JsonPrinter {

    public static void print(@NonNull String name, @Nullable Object value) {
        if (value != null) {
            CoreHelper.catchLog("输入json . name:" + name + "  value:" + value.toString());
        } else {
            CoreHelper.catchLog("输入json value 为空");
        }
    }
}
