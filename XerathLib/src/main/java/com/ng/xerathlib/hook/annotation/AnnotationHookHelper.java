package com.ng.xerathlib.hook.annotation;

import com.android.annotations.NonNull;
import com.android.annotations.Nullable;
import com.ng.xerathlib.extension.ExtConstant;
import com.ng.xerathlib.hook.HookLifeCycle;
import com.ng.xerathlib.hook.annotation.plug.base.AnnotationPlugCreator;
import com.ng.xerathlib.hook.annotation.plug.base.IAnnotationPlug;
import com.ng.xerathlib.hook.params.HookParams;
import com.ng.xerathlib.utils.LogUtil;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2022/05/15
 * @description :
 */
public class AnnotationHookHelper implements HookLifeCycle {
    // 注解处理插件
    @Nullable
    private IAnnotationPlug mAnnotationPlug;

    @NonNull
    public HookParams mParams;

    //只要有注解受理
    public boolean hadChanged = false;

    public AnnotationHookHelper(@NonNull HookParams params) {
        this.mParams = params;
    }

    /**
     * 判断注解是否需要hook
     */
    public boolean isAnnotationNeedHook(String annotationStr) {
        mAnnotationPlug = getPlug(annotationStr);
        if (mAnnotationPlug != null) {
            mAnnotationPlug.init(mParams);
            hadChanged = true;
            return true;
        }
        return false;
    }

    public IAnnotationPlug getPlug(String annotationStr) {
        //注解开关
        if (!ExtConstant.sTransformExt.enableAnnotation) {
            return null;
        }
        IAnnotationPlug plug = AnnotationPlugCreator.createPlug(annotationStr);
        if (plug != null) {
            LogUtil.print("初始化AnnotationPlug:" + mParams.mMethodName + " " + mParams.mMethodDesc);
            return plug;
        }
        return null;
    }

    @Override
    public boolean onVisitClass(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces) {
        hadChanged = false;
        return false;
    }

    @Override
    public void visitClassField(final int access, final String name, final String descriptor, final String signature, final Object value) {
    }

    @Override
    public boolean isClassChanged() {
        return hadChanged;
    }

    @Override
    public void onVisitMethod(final int access, final String name, final String descriptor, final MethodVisitor methodVisitor, final String owner) {
    }

    @Override
    public boolean onVisitMethodInsn(final MethodVisitor mv, final int opcode, final String owner, final String name, final String desc, final boolean itf) {
        if (mAnnotationPlug != null) {
            return mAnnotationPlug.onVisitMethodInsn(mv, opcode, owner, name, desc, itf);
        }
        return false;
    }

    @Override
    public void visitMethodCode() {
        if (mAnnotationPlug != null) {
            mAnnotationPlug.onHookMethodStart(mParams.mMethodVisitor);
        }
    }

    @Override
    public void visitLineNumber(final int line, final Label start) {

    }
    @Override
    public boolean visitMethodAnnotation(final String descriptor, final boolean visible) {
        mAnnotationPlug = null;
        return isAnnotationNeedHook(descriptor);
    }

    @Override
    public void onHookMethodReturn(final int opcode, final MethodVisitor mv) {
        if (mAnnotationPlug != null) {
            mAnnotationPlug.onHookMethodReturn(opcode, mv);
        }
    }

    @Override
    public void onHookMethodEnd(final MethodVisitor mv) {
        if (mAnnotationPlug != null) {
            mAnnotationPlug.onHookMethodEnd(mv);
        }
    }

    @Override
    public void visitEnd() {
        mAnnotationPlug = null;
    }
}
