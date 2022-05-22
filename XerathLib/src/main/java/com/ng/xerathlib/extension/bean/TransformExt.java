package com.ng.xerathlib.extension.bean;

/**
 *
 */
public class TransformExt {
    //默认debug 环境
    public RunScene runVariant = RunScene.DEBUG;

    //是否开启注解功能,默认允许
    public boolean enableAnnotation = true;

    public boolean enableLog = true;

    @Override
    public String toString() {
        return "TransformExt{" +
               "runVariant=" + runVariant +
               ", enableAnnotation=" + enableAnnotation +
               ", enableLog=" + enableLog +
               '}';
    }

    public enum RunScene {
        DEBUG,          //demo 环境使用
        RELEASE
    }

}
