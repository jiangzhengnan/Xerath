package com.ng.xerathlib.core;


import com.ng.xerathlib.constants.AnnotationConstants;
import com.ng.xerathlib.core.plug.CalculateTimePlug;
import com.ng.xerathlib.core.plug.TryCatchPlug;
import com.ng.xerathlib.core.plug.base.IAnnotationPlug;
import com.ng.xerathlib.utils.LogUtil;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.LocalVariablesSorter;


/**
 * 描述:
 *
 * @author Jzn
 * @date 2021/9/14
 */
public class AnnotationHelper {
    //当前操作插件
    private IAnnotationPlug mPlug;

    private LocalVariablesSorter mAdapter;
    private String[] mAnnotationArrays;
    private String mOwner;
    private String mClassName;

    private static AnnotationHelper mInstance;

    private AnnotationHelper() {
        mAnnotationArrays = new String[]{
                AnnotationConstants.CALCULATE_TIME,
                AnnotationConstants.TRY_CATCH
        };
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

    public void init(LocalVariablesSorter adapter, String owner, String name) {
        this.mAdapter = adapter;
        this.mOwner = owner;
        this.mClassName = name;
    }

    public boolean isNeedHook(String descriptor) {
        LogUtil.print("遍历注解:" + descriptor);
        for (String temp : mAnnotationArrays) {
             if (temp.equals(descriptor)) {
                LogUtil.print("注解:" + descriptor + " 需要hook");
                switch (temp) {
                    case AnnotationConstants.CALCULATE_TIME:
                        mPlug = new CalculateTimePlug();
                        break;
                    case AnnotationConstants.TRY_CATCH:
                        mPlug = new TryCatchPlug();
                        break;
                    default:
                        break;
                }
                mPlug.init(mAdapter, mClassName, mOwner);
                return true;
            }
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
    }

}
