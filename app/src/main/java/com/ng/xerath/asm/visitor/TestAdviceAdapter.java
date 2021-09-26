package com.ng.xerath.asm.visitor;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.LocalVariablesSorter;


/**
 * @author : jiangzhengnan
 * @creation : 2021/08/25
 * @description :
 */
public class TestAdviceAdapter extends LocalVariablesSorter implements Opcodes {
    //启动时间
    private int timingStartVarIndex;

    private MethodVisitor methodVisitor;

    protected TestAdviceAdapter(int api, int access, String descriptor, MethodVisitor methodVisitor) {
        super(api, access, descriptor, methodVisitor);
    }

    @Override
    public void visitEnd() {
        //表示方法扫码完毕
        super.visitEnd();
    }

    /**
     * 获得方法参数列表定义
     */
    @Override
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        System.out.println("【visitLocalVariable】 name:" + name);
        super.visitLocalVariable(name, desc, signature, start, end, index);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        //INVOKESTATIC = 184;
        System.out.println("【visitMethodInsn】 opcode:" + opcode);
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }

    @Override
    public void visitCode() {
    }

    @Override
    public void visitLabel(Label label) {
        System.out.println("【visitLabel】");
        super.visitLabel(label);
    }

    @Override
    public void visitInsn(int opcode) {
        System.out.println("【visitInsn】");
        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
        }
        super.visitInsn(opcode);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        System.out.println("【visitMaxs】");
        super.visitMaxs(maxStack, maxLocals);
    }


}