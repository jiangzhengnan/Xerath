package com.ng.xerathlib.hook;


import com.android.annotations.NonNull;
import com.ng.xerathlib.hook.annotation.plug.AnnotationPlugCreator;
import com.ng.xerathlib.hook.annotation.plug.base.IAnnotationPlug;
import com.ng.xerathlib.extension.TransformExtConstant;
import com.ng.xerathlib.hook.params.HookParams;
import com.ng.xerathlib.utils.LogUtil;

import org.objectweb.asm.MethodVisitor;

/**
 * @author Jzn
 * @date 2021/9/14
 * 描述: 外观入口
 * <p>
 * 参数管理器
 * 负责类和方法出入参收集
 * <p>
 * 执行管理器
 * 通过注解定义(annotation)
 * 通过包类名(normal)
 * <p>
 * 生命周期回调
 * 负责监听/回调 类和方法的各种执行生命周期
 * <p>
 * //测试点
 * 保证以前的功能正常
 */
public class XerathHookHelper {
    // 当前操作插件
    private IAnnotationPlug mPlug;
    @NonNull
    public HookParams mParams;

    private static XerathHookHelper mInstance;

    private XerathHookHelper() {
        mParams = new HookParams();
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

    @NonNull
    public HookParams getParams() {
        return mParams;
    }

    /**
     * 判断注解是否需要hook
     */
    public boolean isNeedHook(String annotationStr) {
        if (!TransformExtConstant.sTransformExt.enableAnnotation) {
            return false;
        }
        mPlug = AnnotationPlugCreator.createPlug(annotationStr);
        if (mPlug != null) {
            //LogUtil.print("初始化plug:" + mMethodName + " " + mMethodDesc);
            mPlug.init(mParams);
            return true;
        }
        return false;
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

    // 清空数据
    public void resetOnMethodEnd() {
        mPlug = null;
        mParams.clearMethodData();
    }

    public void resetOnClass() {
        LogUtil.print("XerathHookHelper-清空");
        mParams.clearClassData();
    }
}
