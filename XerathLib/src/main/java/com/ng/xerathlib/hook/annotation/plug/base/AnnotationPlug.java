package com.ng.xerathlib.hook.annotation.plug.base;

import com.android.annotations.NonNull;
import com.ng.xerathlib.hook.params.HookParams;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 描述:
 *
 * @author Jzn
 * @date 2021/9/14
 */
public abstract class AnnotationPlug implements IAnnotationPlug {
    @NonNull
    protected HookParams mParams;

    @Override
    public void init(@NonNull HookParams params) {
        this.mParams = params;
    }

    protected boolean isStaticMethod() {
        return ((Opcodes.ACC_STATIC & mParams.mMethodAccess) != 0);
    }

    @Override
    public void onHookMethodStart(MethodVisitor mv) {

    }

    @Override
    public void onHookMethodReturn(int opcode, MethodVisitor mv) {

    }

    @Override
    public void onHookMethodEnd(MethodVisitor mv) {

    }

    @Override
    public boolean onVisitMethodInsn(MethodVisitor mv, int opcode, String owner, String name, String desc, boolean itf) {
        return false;
    }

    @Override
    public void setLineNumber(int lineNumber) {
    }
}
