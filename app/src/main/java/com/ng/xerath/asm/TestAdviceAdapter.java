package com.ng.xerath.asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;


/**
 * @author : jiangzhengnan
 * @creation : 2021/08/25
 * @description :
 * 使用 AdviceAdapter 是 MethodVisitor 的子类，功能更全
 */
public class TestAdviceAdapter extends MethodVisitor implements Opcodes {
    private MethodVisitor methodVisitor;
    private List<Label> labelList = new ArrayList<>();


    private List<Parameter> parameters = new ArrayList<>();


    public TestAdviceAdapter(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
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

        //查看入参
        if (!"this".equals(name) && start == labelList.get(0)) {
            Type type = Type.getType(desc);
            if (type.getSort() == Type.OBJECT || type.getSort() == Type.ARRAY) {
                parameters.add(new Parameter(name, "Ljava/lang/Object;", index));
            } else {
                parameters.add(new Parameter(name, desc, index));
            }
        }
        System.out.println("parameters: " + parameters.toString());
        super.visitLocalVariable(name, desc, signature, start, end, index);
    }

    @Override
    public void visitCode() {
        System.out.println("【visitCode】");
    }

    @Override
    public void visitLabel(Label label) {
        System.out.println("【visitLabel】");
        labelList.add(label);
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