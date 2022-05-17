package com.ng.xerathlib.hook.annotation.plug.base;

import com.ng.xerathlib.hook.params.HookParams;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.LocalVariablesSorter;

/**
 * 描述:
 *
 * @author Jzn
 * @date 2021/9/14
 */
public abstract class AnnotationPlug implements IAnnotationPlug {
    // 方法返回值类型描述符
    protected int mMethodAccess;
    protected String mMethodDesc;
    protected String mOwner;
    protected String mMethodName;
    protected LocalVariablesSorter mAdapter;

    @Override
    public void init(HookParams params) {
        this.mMethodAccess = params.mMethodAccess;
        this.mAdapter = params.mAdapter;
        this.mOwner = params.mOwner;
        this.mMethodName = params.mMethodName;
        this.mMethodDesc = params.mMethodDesc;
    }

    protected boolean isStaticMethod() {
        return ((Opcodes.ACC_STATIC & mMethodAccess) != 0);
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
