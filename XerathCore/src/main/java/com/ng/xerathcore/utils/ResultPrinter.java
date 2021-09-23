package com.ng.xerathcore.utils;

import com.ng.xerathcore.CoreHelper;

import java.util.Arrays;

public class ResultPrinter {
    public static final String RETURN_PRINT_FORMAT = "出参统计 %s=\"%s\"";

    public static void print(String methodName, byte returnVal) {
        CoreHelper.catchLog(String.format(RETURN_PRINT_FORMAT, methodName, returnVal + ""));
    }

    public static void print(String methodName, char returnVal) {
        CoreHelper.catchLog(String.format(RETURN_PRINT_FORMAT, methodName, returnVal + ""));
    }

    public static void print(String methodName, short returnVal) {
        CoreHelper.catchLog(String.format(RETURN_PRINT_FORMAT, methodName, returnVal + ""));
    }

    public static void print(String methodName, int returnVal) {
        CoreHelper.catchLog(String.format(RETURN_PRINT_FORMAT, methodName, returnVal + ""));
    }

    public static void print(String methodName, boolean returnVal) {
        CoreHelper.catchLog(String.format(RETURN_PRINT_FORMAT, methodName, returnVal + ""));
    }

    public static void print(String methodName, long returnVal) {
        CoreHelper.catchLog(String.format(RETURN_PRINT_FORMAT, methodName, returnVal + ""));
    }

    public static void print(String methodName, float returnVal) {
        CoreHelper.catchLog(String.format(RETURN_PRINT_FORMAT, methodName, returnVal + ""));
    }

    public static void print(String methodName, double returnVal) {
        CoreHelper.catchLog(String.format(RETURN_PRINT_FORMAT, methodName, returnVal + ""));
    }

    public static void print(String methodName, Object returnVal) {
        if (returnVal != null && returnVal.getClass().isArray()) {
            CoreHelper.catchLog(String.format(RETURN_PRINT_FORMAT, methodName, arrayToString(returnVal)));
        } else {
            CoreHelper.catchLog(String.format(RETURN_PRINT_FORMAT, methodName, returnVal));
        }
    }


    private static String arrayToString(Object val) {
        if (!(val instanceof Object[])) {
            if (val instanceof int[]) {
                return Arrays.toString((int[]) val);
            } else if (val instanceof char[]) {
                return Arrays.toString((char[]) val);
            } else if (val instanceof boolean[]) {
                return Arrays.toString((boolean[]) val);
            } else if (val instanceof byte[]) {
                return Arrays.toString((byte[]) val);
            } else if (val instanceof long[]) {
                return Arrays.toString((long[]) val);
            } else if (val instanceof double[]) {
                return Arrays.toString((double[]) val);
            } else if (val instanceof float[]) {
                return Arrays.toString((float[]) val);
            } else if (val instanceof short[]) {
                return Arrays.toString((short[]) val);
            } else {
                return "Unknown type array";
            }
        } else {
            return Arrays.deepToString((Object[]) val);
        }
    }


}
