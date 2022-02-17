package com.ng.xerathlib.core;

import com.android.tools.r8.graph.O;
import com.ng.xerathlib.asm.base.Parameter;
import com.ng.xerathlib.core.plug.base.IAnnotationPlug;
import com.ng.xerathlib.utils.LogUtil;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.LocalVariablesSorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @author Jzn
 * @date 2021/9/14
 */
public class XerathHookHelper {
    // 当前操作插件
    private IAnnotationPlug mPlug;

    // 方法操作类
    private LocalVariablesSorter mAdapter;
    private String mOwner;
    private String mMethodName;
    private String mMethodDesc;
    //方法访问符号
    private int mMethodAccess;
    //当前行数
    private int mLineNumber;

    // 注解附带的参数
    private Map<String, Object> mAnnotationParams;
    // 方法参数map
    private Map<String, List<Parameter>> mMethodParametersMap;

    //静态成员变量
    private List<String> mStaticFiledList = new ArrayList<>();
    //成员变量
    private List<String> mFiledList = new ArrayList<>();
    //临时变量
    private List<String> mTempFiledList = new ArrayList<>();

    private static XerathHookHelper mInstance;

    private XerathHookHelper() {
        this.mMethodParametersMap = new HashMap<>();
        this.mAnnotationParams = new HashMap<>();
    }

    public static XerathHookHelper getInstance() {
        if (mInstance == null) {
            synchronized (XerathHookHelper.class) {
                if (mInstance == null) {
                    mInstance = new XerathHookHelper();
                }
            }
        }
        return mInstance;
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

    /**
     * 判断注解是否需要hook
     */
    public boolean isNeedHook(String annotationStr) {
        mPlug = AnnotationPlugCreator.createPlug(annotationStr);
        if (mPlug != null) {
            //LogUtil.print("初始化plug:" + mMethodName + " " + mMethodDesc);
            mPlug.init(mMethodAccess, mAdapter, mOwner, mMethodName, mMethodDesc);
            return true;
        }
        return false;
    }

    public void printPrams() {
        LogUtil.print("方法名:name:" + mMethodName + " 方法描述:methodDesc:" + mMethodDesc);
        //LogUtil.print("owner:" + mOwner);
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

    public void onHookMethodStart(MethodVisitor mv) {
        if (mPlug != null) {
            mPlug.onHookMethodStart(mv);
        }
    }

    public void onHookMethodReturn(int opcode, MethodVisitor mv) {
        if (mPlug != null) {
            mPlug.onHookMethodReturn(opcode, mv);
        }
    }

    public void onHookMethodEnd(MethodVisitor mv) {
        if (mPlug != null) {
            mPlug.onHookMethodEnd(mv);
        }
    }

    public boolean onVisitMethodInsn(MethodVisitor mv, int opcode, String owner, String name, String desc, boolean itf) {
        if (mPlug != null) {
            return mPlug.onVisitMethodInsn(mv, opcode, owner, name, desc, itf);
        }
        return false;
    }

    public void setLineNumber(int lineNumber) {
        mLineNumber = lineNumber;
        if (mPlug != null) {
            mPlug.setLineNumber(mLineNumber);
        }
    }

    // 清空数据
    public void resetOnMethodEnd() {
        mPlug = null;
        //清空注解附带参数
        mAnnotationParams.clear();
        //清空方法参数
        mMethodParametersMap.clear();

    }

    public void resetOnClass() {
        LogUtil.print("XerathHookHelper-清空");
        mStaticFiledList.clear();
        mFiledList.clear();
        //每个类清理一次
        mTempFiledList.clear();
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
}
