package com.ng.xerathlib.extension.bean;

import java.util.List;

import com.android.annotations.Nullable;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2022/05/14
 * @description :
 * track_method_stack
 *
 * todo
 * 1.出入参
 * 2.进程+线程
 */
public class TrackMethodStack {
    public boolean enable;

    //目标包列表 (忽略类，只匹配包名)
    @Nullable
    public List<String> targetPackageList;

    //目标类列表 (忽略包，只匹配类名)
    @Nullable
    public List<String> targetClassList;

    //过滤包列表 (忽略类，只匹配包名)_
    @Nullable
    public List<String> blackPackageList;

    //过滤类列表 (忽略包，只匹配类名)
    @Nullable
    public List<String> blackClassList;

    //耗时起点方法 (不传就从范围内调用的第一个方法开始统计耗时,格式包名+$+类名+$+方法名)
    @Nullable
    public String timeStartMethod;

    @Nullable
    public String logTag;

    @Override
    public String toString() {
        return "TrackMethodStack{" +
                "enable=" + enable +
                ", targetPackageList=" + targetPackageList +
                ", targetClassList=" + targetClassList +
                ", blackPackageList=" + blackPackageList +
                ", blackClassList=" + blackClassList +
                ", timeStartMethod='" + timeStartMethod + '\'' +
                ", logTag='" + logTag + '\'' +
                '}';
    }
}
