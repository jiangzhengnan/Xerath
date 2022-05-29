package com.ng.xerathlib.hook.target;

import com.android.annotations.NonNull;
import com.android.annotations.Nullable;
import com.ng.xerathlib.hook.HookLifeCycle;
import com.ng.xerathlib.hook.params.HookParams;
import com.ng.xerathlib.hook.target.base.ITargetPlug;
import com.ng.xerathlib.hook.target.plug.TargetPlugCreator;
import com.ng.xerathlib.utils.LogUtil;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2022/05/15
 * @description :
 */
public class TargetHookHelper implements HookLifeCycle {
    // 注解处理插件
    @Nullable
    private ITargetPlug mTargetPlug;
    @NonNull
    public HookParams mParams;
    public boolean hadChanged = false;

    public TargetHookHelper(@NonNull final HookParams params) {
        mParams = params;
    }

    @Override
    public boolean onVisitClass(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces) {
        mTargetPlug = TargetPlugCreator.getPlug(mParams.mOwner);
        hadChanged = mTargetPlug != null;
        return hadChanged;
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
        if (mTargetPlug != null) {
            mTargetPlug.init(mParams);
        }
    }

    @Override
    public boolean onVisitMethodInsn(final MethodVisitor mv, final int opcode, final String owner, final String name, final String desc, final boolean itf) {
        if (mTargetPlug != null) {
            return mTargetPlug.onVisitMethodInsn(mv, opcode, owner, name, desc, itf);
        }
        return false;
    }

    @Override
    public void visitMethodCode() {
        if (mTargetPlug != null) {
            mTargetPlug.onHookMethodStart(mParams.mMethodVisitor);
        }
    }

    @Override
    public void visitLineNumber(final int line, final Label start) {

    }

    @Override
    public boolean visitMethodAnnotation(final String descriptor, final boolean visible) {
        return false;
    }

    @Override
    public void onHookMethodReturn(final int opcode, final MethodVisitor mv) {
        if (mTargetPlug != null) {
            mTargetPlug.onHookMethodReturn(opcode, mv);
        }
    }

    @Override
    public void onHookMethodEnd(final MethodVisitor mv) {
        if (mTargetPlug != null) {
            mTargetPlug.onHookMethodEnd(mv);
        }
    }

    @Override
    public void visitEnd() {
    }
}
