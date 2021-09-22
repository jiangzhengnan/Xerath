package com.ng.xerath.asm;

import com.ng.xerathcore.utils.ResultPrinter;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2021/09/16
 * @description :
 */
public class ASMShow {

    public void test() {

        String className = "";
        String methodName = "";
        long costedMilles = 0;
        byte returnVal = 1;
        ResultPrinter.print(methodName, costedMilles, returnVal);

    }

}
