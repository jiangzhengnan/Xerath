package com.ng.xerathlib.hook.target.base;

import com.ng.xerathlib.hook.params.HookParams;

import org.objectweb.asm.MethodVisitor;

public interface ITargetPlug {

    void init(HookParams params);

    void onHookMethodStart(MethodVisitor mv);

    void onHookMethodReturn(int opcode, MethodVisitor mv);

    void onHookMethodEnd(MethodVisitor mv);

    boolean onVisitMethodInsn(MethodVisitor mv, int opcode, String owner, String name, String desc, boolean itf);

}
