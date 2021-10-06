package com.ng.xerath.asm.visitor;

import com.ng.xerathcore.utils.LogUtil;

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


    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        //LogUtil.print("全局方法扩展 0: owner:" + owner + " name:" + name + " desc:" + descriptor);
        System.out.println("【visitMethodInsn】 owner:" + owner + " name:" + name + " desc:" + descriptor);
        //全局方法扩展
        if ("org/json/JSONObject".equals(owner)) {
            //String linenumberConst = lineNumber + "";
            if ("put".equals(name)) {
                if ("(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;".equals(descriptor)) {
                    LogUtil.print("全局方法扩展 33~");
                    //mv.visitLdcInsn(linenumberConst);
                    //mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/ng/xerathcore/utils/JsonPrinter", "print", "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;)V", false);


                    mv.visitLdcInsn("【异常捕获】开始");
                    mv.visitMethodInsn(INVOKESTATIC, "com/ng/xerathcore/CoreHelper", "catchLog", "(Ljava/lang/String;)V", false);
                }
            }
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }

}