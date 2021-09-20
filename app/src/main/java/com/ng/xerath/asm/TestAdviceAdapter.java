package com.ng.xerath.asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.LocalVariablesSorter;

import static org.objectweb.asm.Opcodes.ATHROW;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.RETURN;


/**
 * @author : jiangzhengnan
 * @creation : 2021/08/25
 * @description :
 * 使用 AdviceAdapter 是 MethodVisitor 的子类，功能更全
 */
public class TestAdviceAdapter extends LocalVariablesSorter {
    private MethodVisitor methodVisitor;

    protected TestAdviceAdapter(int api, int access, String descriptor, MethodVisitor methodVisitor) {
        super(api, access, descriptor, methodVisitor);
        methodVisitor = mv;
    }

    @Override
    public void visitEnd() {
        //表示方法扫码完毕
        super.visitEnd();
    }


    @Override
    public void visitCode() {
        MethodVisitor methodVisitor = mv;
        Label label0 = new Label();
        methodVisitor.visitLabel(label0);
        methodVisitor.visitLineNumber(48, label0);
        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "com/ng/xerathcore/utils/CoreUtils", "isDoubleClick", "()Z", false);

        Label label1 = new Label();
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, label1);

        Label label2 = new Label();
        methodVisitor.visitLabel(label2);
        methodVisitor.visitLineNumber(49, label2);
        methodVisitor.visitLdcInsn("\u963b\u6b62\u91cd\u590d\u70b9\u51fb");
        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "com/ng/xerathcore/CoreHelper", "catchLog", "(Ljava/lang/String;)V", false);
        Label label3 = new Label();
        methodVisitor.visitLabel(label3);
        methodVisitor.visitLineNumber(50, label3);
        methodVisitor.visitInsn(RETURN);

        methodVisitor.visitLabel(label1);
        methodVisitor.visitLineNumber(52, label1);
        methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        //methodVisitor.visitInsn(RETURN);

    }

    @Override
    public void visitInsn(int opcode) {
        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
        }
        super.visitInsn(opcode);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack, maxLocals);
    }
}
