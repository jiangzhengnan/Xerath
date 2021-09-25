package com.ng.xerathlib.core.plug;

import com.ng.xerathlib.core.XerathHookHelper;
import com.ng.xerathlib.core.plug.base.AnnotationPlug;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.LocalVariablesSorter;

/**
 * @author : jiangzhengnan.jzn
 * @creation : 2021/09/20
 * @description :
 */
public class PopToastPlug extends AnnotationPlug {

    @Override
    public void init(LocalVariablesSorter adapter, String owner, String name, String methodDesc) {
        super.init(adapter, owner, name, methodDesc);
    }

    @Override
    public void onHookMethodStart(MethodVisitor mv) {
        String showStr = (String) XerathHookHelper.getInstance().getAnnotationParams("str");
        Label label0 = new Label();
        mv.visitLabel(label0);
        mv.visitLineNumber(0, label0);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/ng/xerathcore/XerathEngine", "getInstance", "()Lcom/ng/xerathcore/XerathEngine;", false);
        mv.visitLdcInsn(showStr + "");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/ng/xerathcore/XerathEngine", "showToast", "(Ljava/lang/String;)V", false);
    }

    @Override
    public void onHookMethodReturn(int opcode, MethodVisitor mv) {

    }

    @Override
    public void onHookMethodEnd(MethodVisitor mv) {

    }
}
