package com.ng.xerathlib.core;

import com.ng.xerathlib.core.constants.AnnotationCreator;
import com.ng.xerathlib.core.plug.base.IAnnotationPlug;
import com.ng.xerathlib.utils.LogUtil;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.LocalVariablesSorter;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 *
 * @author Jzn
 * @date 2021/9/14
 */
public class AnnotationHelper {
    // 当前操作插件
    private IAnnotationPlug mPlug;
    // 方法操作类
    private LocalVariablesSorter mAdapter;
    // 方法返回值类型描述符
    private String methodDesc;
    private String mOwner;
    private String mClassName;

    // 注解参数
    private Map<String, Object> mAnnotationParams;

    private static AnnotationHelper mInstance;

    private AnnotationHelper() {
    }

    public static AnnotationHelper getInstance() {
        if (mInstance == null) {
            synchronized (AnnotationHelper.class) {
                if (mInstance == null) {
                    mInstance = new AnnotationHelper();
                }
            }
        }
        return mInstance;
    }

    public void init(LocalVariablesSorter adapter, String owner, String name, String methodDesc) {
        LogUtil.print("[AnnotationHelper]入参分析");
        LogUtil.print("owner:" + owner);
        LogUtil.print("方法名:name:" + name);
        LogUtil.print("方法描述:methodDesc:" + methodDesc);

        this.mAdapter = adapter;
        this.mOwner = owner;
        this.mClassName = name;
        this.methodDesc = methodDesc;
        this.mAnnotationParams = new HashMap<>();
    }

    public void putParams(String key, Object value) {
        mAnnotationParams.put(key, value);
    }

    public Object getParams(String key) {
        if (mAnnotationParams != null && mAnnotationParams.containsKey(key)) {
            return mAnnotationParams.get(key);
        }
        return null;
    }

    public boolean isNeedHook(String descriptor) {
        mPlug = AnnotationCreator.createPlug(descriptor);
        if (mPlug != null) {
            mPlug.init(mAdapter, mClassName, mOwner, methodDesc);
            return true;
        }
        return false;
    }

    public void hookMethodStart(MethodVisitor mv) {
        if (mPlug != null) {
            mPlug.hookMethodStart(mv);
        }
    }

    public void hookMethodEnd(MethodVisitor mv) {
        if (mPlug != null) {
            mPlug.hookMethodEnd(mv);
        }
    }

    public void hookMethodReturn(MethodVisitor mv) {
        if (mPlug != null) {
            mPlug.hookMethodReturn(mv);
        }
    }

    public void reset() {
        mPlug = null;
        mAnnotationParams.clear();
    }

}
