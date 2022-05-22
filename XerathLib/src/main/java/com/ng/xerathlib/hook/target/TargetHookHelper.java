package com.ng.xerathlib.hook.target;

import com.ng.xerathlib.hook.HookLifeCycle;
import com.ng.xerathlib.hook.target.base.ITargetPlug;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2022/05/15
 * @description :
 */
public class TargetHookHelper implements HookLifeCycle {

    /**
     * 判断当前类/方法是否需要修改
     */
    public static ITargetPlug getPlug(String className, String methodName) {
        //todo ExtConstant.sTrackMethodStack.targetClassList

        return null;
    }

    @Override
    public boolean onVisitClass(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces) {

        return false;
    }

    @Override
    public void visitClassField(final int access, final String name, final String descriptor, final String signature, final Object value) {

    }

    @Override
    public boolean isClassChanged() {
        return false;
    }

    @Override
    public void onVisitMethod(final int access, final String name, final String descriptor, final MethodVisitor methodVisitor, final String owner) {

    }

    @Override
    public boolean onVisitMethodInsn(final MethodVisitor mv, final int opcode, final String owner, final String name, final String desc, final boolean itf) {
        return false;
    }

    @Override
    public void visitMethodCode() {

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

    }

    @Override
    public void onHookMethodEnd(final MethodVisitor mv) {

    }

    @Override
    public void visitEnd() {

    }
}
