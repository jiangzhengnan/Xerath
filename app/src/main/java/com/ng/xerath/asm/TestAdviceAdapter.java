package com.ng.xerath.asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.ATHROW;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.RETURN;


/**
 * @author : jiangzhengnan
 * @creation : 2021/08/25
 * @description :
 * 使用 AdviceAdapter 是 MethodVisitor 的子类，功能更全
 */
public class TestAdviceAdapter extends LocalVariablesSorter {
    Label l1;
    Label l2;
    private String exceptionHandleClass;
    private String exceptionHandleMethod;

    protected TestAdviceAdapter(int api, int access, String descriptor, MethodVisitor methodVisitor) {
        super(api, access, descriptor, methodVisitor);
    }

    @Override
    public void visitEnd() {
        //表示方法扫码完毕
        super.visitEnd();
    }

    private Label from = new Label(),
            to = new Label(),
            target = new Label();

    @Override
    public void visitCode() {
        Label l0 = new Label();
        l1 = new Label();
        l2 = new Label();
        mv.visitTryCatchBlock(l0, l1, l2, "java/lang/Exception");
        mv.visitLabel(l0);
    }

    @Override
    public void visitInsn(int opcode) {
        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
            mv.visitLabel(l1);
            Label l3 = new Label();
            mv.visitJumpInsn(Opcodes.GOTO, l3);
            mv.visitLabel(l2);
            mv.visitVarInsn(ASTORE, 1);
            if (exceptionHandleClass != null && exceptionHandleMethod != null) {
                mv.visitVarInsn(ALOAD, 1);
                mv.visitMethodInsn(INVOKESTATIC, exceptionHandleClass,
                        exceptionHandleMethod, "(Ljava/lang/Exception;)V", false);
            }
            mv.visitLabel(l3);
        }
        super.visitInsn(opcode);
    }


}
