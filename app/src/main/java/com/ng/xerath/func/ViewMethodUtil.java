package com.ng.xerath.func;

import com.ng.xerathcore.annotation.Xerath_PopToast;

/**
 * 描述:
 *
 * @author Jzn
 * @date 2021/9/4
 */
public class ViewMethodUtil {

    /**
     * 弹出Toast
     */
    @Xerath_PopToast(str = "测试Toast")
    public static void popToast() {
        System.out.println("popToast");
    }
}
