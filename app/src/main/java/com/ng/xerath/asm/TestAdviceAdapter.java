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
        mv.visitLdcInsn("【异常统计开始】");
        mv.visitMethodInsn(INVOKESTATIC, "com/ng/xerathcore/CoreUtils", "catchLog", "(Ljava/lang/String;)V", false);

        //标志：try块开始位置
        visitLabel(from);
        visitTryCatchBlock(from,
                to,
                target,
                "java/lang/Exception");
    }

    @Override
    public void visitInsn(int opcode) {
        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
            //标志：try块结束
            mv.visitLabel(to);

            //标志：catch块开始位置
            mv.visitLabel(target);
            mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Exception"});

            // 异常信息保存到局部变量
            int local = newLocal(Type.LONG_TYPE);
            mv.visitVarInsn(ASTORE, local);

            // 抛出异常
            mv.visitVarInsn(ALOAD, local);
            mv.visitInsn(ATHROW);
        }
        super.visitInsn(opcode);
    }


}
