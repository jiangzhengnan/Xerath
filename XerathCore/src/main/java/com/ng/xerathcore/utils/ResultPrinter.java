package com.ng.xerathcore.utils;

import java.util.Arrays;

public class ResultPrinter {
    public static final String RETURN_PRINT_FORMAT = "出参统计 %s[%sms]=\"%s\"";

    public static void print(String methodName, long costedMilles, byte returnVal) {
        LogUtil.print(String.format(RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void print(String methodName, long costedMilles, char returnVal) {
        LogUtil.print(String.format(RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void print(String methodName, long costedMilles, short returnVal) {
        LogUtil.print(String.format(RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void print(String methodName, long costedMilles, int returnVal) {
        LogUtil.print(String.format(RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void print(String methodName, long costedMilles, boolean returnVal) {
        LogUtil.print(String.format(RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void print(String methodName, long costedMilles, long returnVal) {
        LogUtil.print(String.format(RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void print(String methodName, long costedMilles, float returnVal) {
        LogUtil.print(String.format(RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void print(String methodName, long costedMilles, double returnVal) {
        LogUtil.print(String.format(RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void print(String methodName, long costedMilles, Object returnVal) {
        if (returnVal != null && returnVal.getClass().isArray()) {
            LogUtil.print(String.format(RETURN_PRINT_FORMAT, methodName, costedMilles + "", arrayToString(returnVal)));
        } else {
            LogUtil.print(String.format(RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal));
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
