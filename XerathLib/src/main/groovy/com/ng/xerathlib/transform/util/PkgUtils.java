package com.ng.xerathlib.transform.util;

/**
 * @author : jiangzhengnan
 * @creation : 2021/11/16
 * @description :
 */
public class PkgUtils {
    private static final String MY_PKG_NAME = "com.ng";

    private static boolean isContainer(String className, String[] arrays) {
        for (String temp : arrays) {
            if (className.contains(temp)) {
                return true;
            }
        }
        return false;
    }
}
