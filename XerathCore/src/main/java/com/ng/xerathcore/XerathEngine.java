package com.ng.xerathcore;

import androidx.annotation.NonNull;

/**
 * 描述:框架总入口
 *
 * @author Jzn
 * @date 2021/9/5
 */
public class XerathEngine {

    private static XerathEngine mInstance;

    private XerathEngine() {
    }

    public static XerathEngine getInstance() {
        if (mInstance == null) {
            synchronized (XerathEngine.class) {
                if (mInstance == null) {
                    mInstance = new XerathEngine();
                }
            }
        }
        return mInstance;
    }


    public void start() {
    }

}
