package com.ng.xerathlib.hook.params;

import com.android.annotations.NonNull;
import com.ng.xerathlib.utils.Parameter;

import org.objectweb.asm.commons.LocalVariablesSorter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HookParams {
    // 方法操作类
    public LocalVariablesSorter mAdapter;
    public String mOwner;
    public String mMethodName;
    public String mMethodDesc;
    //方法访问符号
    public int mMethodAccess;
    //当前行数
    public int mLineNumber;
    // 注解附带的参数
    @NonNull
    public Map<String, Object> mAnnotationParams;
    // 方法参数map
    @NonNull
    public Map<String, List<Parameter>> mMethodParametersMap;

    //静态成员变量
    public List<String> mStaticFiledList = new ArrayList<>();
    //成员变量
    public List<String> mFiledList = new ArrayList<>();
    //临时变量
    public List<String> mTempFiledList = new ArrayList<>();

    public HookParams() {
        this.mMethodParametersMap = new HashMap<>();
        this.mAnnotationParams = new HashMap<>();
    }

    //preLoad
    public void init(int access, String owner, String name, String methodDesc) {
        this.mMethodAccess = access;
        this.mMethodDesc = methodDesc;
        this.mOwner = owner;
        this.mMethodName = name;
    }

    //load
    public void init(int access, LocalVariablesSorter adapter, String owner, String name, String methodDesc) {
        this.mMethodAccess = access;
        this.mAdapter = adapter;
        this.mOwner = owner;
        this.mMethodName = name;
        this.mMethodDesc = methodDesc;
    }


    public void putAnnotationParams(String key, Object value) {
        mAnnotationParams.put(key, value);
    }

    public void putAnnotationArrayParams(String key, Object value) {
        Object[] values;
        if (mAnnotationParams.containsKey(key)) {
            values = (Object[]) mAnnotationParams.get(key);
            Object[] newValues = new Object[values.length + 1];
            System.arraycopy(values, 0, newValues, 0, values.length);
            newValues[values.length] = value;
            mAnnotationParams.put(key, newValues);
        } else {
            values = new Object[]{value};
            mAnnotationParams.put(key, values);
        }
    }

    public Object getAnnotationParams(String key) {
        if (mAnnotationParams.containsKey(key)) {
            return mAnnotationParams.get(key);
        }
        return null;
    }

    public void putMethodParams(String key, List<Parameter> value) {
        mMethodParametersMap.put(key, value);
        //LogUtil.printPre("参数列表:" + mMethodParametersMap.toString());
    }

    public Map<String, List<Parameter>> getMethodParamsMap() {
        return mMethodParametersMap;
    }

    public List<Parameter> getMethodParams(String key) {
        if (mMethodParametersMap.containsKey(key)) {
            return mMethodParametersMap.get(key);
        }
        return null;
    }

    public void setLineNumber(int lineNumber) {
        mLineNumber = lineNumber;
    }


    public List<String> getStaticFiledList() {
        return mStaticFiledList;
    }

    public List<String> getFiledList() {
        return mFiledList;
    }

    public List<String> getTempFiledList() {
        return mTempFiledList;
    }

    public void clearMethodData(){
        //清空注解附带参数
        mAnnotationParams.clear();
        //清空方法参数
        mMethodParametersMap.clear();

    }

    public void clearClassData(){
        mStaticFiledList.clear();
        mFiledList.clear();
        //每个类清理一次
        mTempFiledList.clear();
    }
}
