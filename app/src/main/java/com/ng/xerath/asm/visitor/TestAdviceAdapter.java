package com.ng.xerath.asm.visitor;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.LocalVariablesSorter;


/**
 * @author : jiangzhengnan
 * @creation : 2021/08/25
 * @description :
 * 使用 AdviceAdapter 是 MethodVisitor 的子类，功能更全
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
        System.out.println("【visitLocalVariable】");
        super.visitLocalVariable(name, desc, signature, start, end, index);
    }

    @Override
    public void visitCode() {
//        System.out.println("【visitCode】");
//        int printUtilsVarIndex = newLocal(Type.getObjectType("com.ng.xerath.utils/ParameterPrinter"));
//        mv.visitTypeInsn(NEW, "com.ng.xerath.utils/ParameterPrinter");
//        mv.visitInsn(DUP);
//        mv.visitLdcInsn(className);
//        mv.visitLdcInsn(methodName);
//        mv.visitMethodInsn(INVOKESPECIAL, "com.ng.xerath.utils/ParameterPrinter", "<init>", "(Ljava/lang/String;Ljava/lang/String;)V", false);
//        mv.visitVarInsn(ASTORE, printUtilsVarIndex);
//        for (int i = 0; i < parameters.size(); i++) {
//            Parameter parameter = parameters.get(i);
//            String name = parameter.name;
//            String desc = parameter.desc;
//            int index = parameter.index;
//            int opcode = Utils.getLoadOpcodeFromDesc(desc);
//            String fullyDesc = String.format("(Ljava/lang/String;%s)Lcom.ng.xerath.utils/ParameterPrinter;", desc);
//            visitPrint(printUtilsVarIndex, index, opcode, name, fullyDesc);
//        }
//        mv.visitVarInsn(ALOAD, printUtilsVarIndex);
//        if (debugMethod) {
//            mv.visitMethodInsn(INVOKEVIRTUAL, "com.ng.xerath.utils/ParameterPrinter", "print", "()V", false);
//        } else if (debugMethodWithCustomLogger) {
//            mv.visitMethodInsn(INVOKEVIRTUAL, "com.ng.xerath.utils/ParameterPrinter", "printWithCustomLogger", "()V", false);
//        }
//        //Timing
//        timingStartVarIndex = newLocal(Type.LONG_TYPE);
//        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
//        mv.visitVarInsn(Opcodes.LSTORE, timingStartVarIndex);
    }

    private void visitPrint(int varIndex, int localIndex, int opcode, String name, String desc) {
        mv.visitVarInsn(ALOAD, varIndex);
        mv.visitLdcInsn(name);
        mv.visitVarInsn(opcode, localIndex);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "com.ng.xerath.utils/ParameterPrinter",
                "append",
                desc, false);
        mv.visitInsn(POP);
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