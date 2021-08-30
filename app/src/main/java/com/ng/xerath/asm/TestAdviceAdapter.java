package com.ng.xerath.asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

import dalvik.bytecode.Opcodes;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2021/08/25
 * @description :
 * 使用 AdviceAdapter 是 MethodVisitor 的子类，功能更全
 */
public class TestAdviceAdapter extends AdviceAdapter implements Opcodes {

    public TestAdviceAdapter(int api, MethodVisitor mv, int access, String name, String descriptor) {
        super(api, mv, access, name, descriptor);
    }

    @Override
    public void visitCode() {
        //表示 ASM 开始扫描这个方法
        super.visitCode();
    }
    Label label0 = new Label();

    @Override
    public void visitEnd() {
        //表示方法扫码完毕
        super.visitEnd();
    }

    @Override
    protected void onMethodEnter() {
        //进入这个方法
        super.onMethodEnter();
        mv.visitLabel(label0);
        mv.visitLineNumber(50, label0);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        mv.visitVarInsn(LSTORE, 1);
    }

    @Override
    protected void onMethodExit(int opcode) {
        //即将从这个方法出去
        super.onMethodExit(opcode);

        Label label1 = new Label();
        mv.visitLabel(label1);
        mv.visitLineNumber(52, label1);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        mv.visitVarInsn(LSTORE, 3);
        Label label2 = new Label();
        mv.visitLabel(label2);
        mv.visitLineNumber(53, label2);
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        mv.visitLdcInsn("\u8017\u65f6:");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(LLOAD, 3);
        mv.visitVarInsn(LLOAD, 1);
        mv.visitInsn(LSUB);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        Label label3 = new Label();
        mv.visitLabel(label3);
        mv.visitLineNumber(54, label3);
        mv.visitInsn(RETURN);
        Label label4 = new Label();
        mv.visitLabel(label4);
        mv.visitLocalVariable("this", "Lcom/ng/xerath/MainActivity;", null, label0, label4, 0);
        mv.visitLocalVariable("start_time", "J", null, label1, label4, 1);
        mv.visitLocalVariable("end_time", "J", null, label2, label4, 3);
        mv.visitMaxs(6, 5);
        mv.visitEnd();  }

    @Override
    public void visitInsn(int opcode) {
        //扫描Opcodes操作符
        super.visitInsn(opcode);
    }
}
