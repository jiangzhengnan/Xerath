package com.ng.xerathlib.hook;


import com.android.annotations.NonNull;
import com.android.annotations.Nullable;
import com.ng.xerathlib.hook.annotation.AnnotationHookHelper;
import com.ng.xerathlib.hook.annotation.plug.base.AnnotationPlugCreator;
import com.ng.xerathlib.hook.annotation.plug.base.IAnnotationPlug;
import com.ng.xerathlib.extension.ExtConstant;
import com.ng.xerathlib.hook.params.HookParams;
import com.ng.xerathlib.hook.target.TargetHookHelper;
import com.ng.xerathlib.hook.target.base.ITargetPlug;
import com.ng.xerathlib.hook.target.base.TargetPlug;
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
    // 注解处理插件
    @Nullable
    private IAnnotationPlug mAnnotationPlug;
    // 目标处理插件
    @Nullable
    private ITargetPlug mTargetPlug;
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
    public boolean isAnnotationNeedHook(String annotationStr) {
        mAnnotationPlug = AnnotationHookHelper.getPlug(annotationStr);
        if (mAnnotationPlug != null) {
            mAnnotationPlug.init(mParams);
            return true;
        }
        return false;
    }

    public boolean isTargetNeedHook(String className, String methodName) {
        mTargetPlug = TargetHookHelper.getPlug(className, methodName);
        if (mTargetPlug != null) {
            mTargetPlug.init(mParams);
            return true;
        }
        return false;
    }

    public void onHookMethodStart(MethodVisitor mv) {
        if (mAnnotationPlug != null) {
            mAnnotationPlug.onHookMethodStart(mv);
        }
        if (mTargetPlug != null) {
            mTargetPlug.onHookMethodStart(mv);
        }
    }

    public void onHookMethodReturn(int opcode, MethodVisitor mv) {
        if (mAnnotationPlug != null) {
            mAnnotationPlug.onHookMethodReturn(opcode, mv);
        }
        if (mTargetPlug != null) {
            mTargetPlug.onHookMethodReturn(opcode, mv);
        }
    }

    public void onHookMethodEnd(MethodVisitor mv) {
        if (mAnnotationPlug != null) {
            mAnnotationPlug.onHookMethodEnd(mv);
        }
    }

    public boolean onVisitMethodInsn(MethodVisitor mv, int opcode, String owner, String name, String desc, boolean itf) {
        if (mAnnotationPlug != null) {
            return mAnnotationPlug.onVisitMethodInsn(mv, opcode, owner, name, desc, itf);
        }
        if (mTargetPlug != null) {
            return mTargetPlug.onVisitMethodInsn(mv, opcode, owner, name, desc, itf);
        }
        return false;
    }

    // 清空数据
    public void resetOnMethodEnd() {
        mAnnotationPlug = null;
        mTargetPlug = null;
        mParams.clearMethodData();
    }

    public void resetOnClass() {
        LogUtil.print("XerathHookHelper-清空");
        mParams.clearClassData();
    }
}
