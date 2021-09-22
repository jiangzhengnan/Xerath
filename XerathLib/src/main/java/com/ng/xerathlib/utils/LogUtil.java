package com.ng.xerathlib.utils;

/**
 * 描述:
 *
 * @author Jzn
 * @date 2021/9/4
 */
public class LogUtil {
    private static final String TAG = "[ Xerath ] ";

    public static void print(String s) {
        System.out.println(TAG + s);
    }

    public static void printPre(String s) {
        System.out.println(TAG + "预处理 " + s);
    }
}
