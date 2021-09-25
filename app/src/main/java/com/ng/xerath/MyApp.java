package com.ng.xerath;

import android.app.Application;

import com.ng.xerathcore.XerathEngine;

/**
 * @author : jiangzhengnan.jzn
 * @creation : 2021/09/20
 * @description :
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //框架初始化
        XerathEngine.getInstance().init(getApplicationContext());
    }
}
