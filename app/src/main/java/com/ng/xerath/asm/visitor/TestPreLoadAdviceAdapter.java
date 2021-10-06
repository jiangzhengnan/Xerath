package com.ng.xerath.asm.visitor;

import com.ng.xerath.asm.Parameter;
import com.ng.xerathcore.utils.LogUtil;

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
public class TestPreLoadAdviceAdapter extends MethodVisitor implements Opcodes {
    private static final String TAG = "预加载";

    private MethodVisitor methodVisitor;
    private List<Label> labelList = new ArrayList<>();


    private List<Parameter> parameters = new ArrayList<>();


    public TestPreLoadAdviceAdapter(int api, MethodVisitor methodVisitor) {
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
        //print("【visitLocalVariable】");

        //查看入参
        if (!"this".equals(name) && start == labelList.get(0)) {
            Type type = Type.getType(desc);
            if (type.getSort() == Type.OBJECT || type.getSort() == Type.ARRAY) {
                parameters.add(new Parameter(name, "Ljava/lang/Object;", index));
            } else {
                parameters.add(new Parameter(name, desc, index));
            }
        }
        print("parameters: " + parameters.toString());
        super.visitLocalVariable(name, desc, signature, start, end, index);
    }

    @Override
    public void visitCode() {
        //print("【visitCode】");
    }

    @Override
    public void visitLabel(Label label) {
        //print("【visitLabel】");
        labelList.add(label);
        super.visitLabel(label);
    }


    @Override
    public void visitInsn(int opcode) {
        //print("【visitInsn】");
        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
        }
        super.visitInsn(opcode);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        //print("【visitMaxs】");
        super.visitMaxs(maxStack, maxLocals);
    }

    private void print(String s) {
        System.out.println(TAG + s);
    }
}