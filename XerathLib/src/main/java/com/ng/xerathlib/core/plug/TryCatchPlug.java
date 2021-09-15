package com.ng.xerathlib.core.plug;

import com.ng.xerathlib.core.plug.base.AnnotationPlug;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.LLOAD;
import static org.objectweb.asm.Opcodes.LSTORE;
import static org.objectweb.asm.Opcodes.LSUB;
import static org.objectweb.asm.Opcodes.NEW;

/**
 * 描述: 统计耗时
 *
 * @author Jzn
 * @date 2021/9/14
 */
public class TryCatchPlug extends AnnotationPlug {
    private Label from = new Label();
    private Label to = new Label();
    private Label target = new Label();

    @Override
    public void hookMethodStart(MethodVisitor mv) {
        //标志：try块开始位置
        mv.visitLabel(from);
        mv.visitTryCatchBlock(from,
                to,
                target,
                "java/lang/Exception");
    }

    @Override
    public void hookMethodEnd(MethodVisitor mv) {
        //标志：try块结束
        mv.visitLabel(to);

        //标志：catch块开始位置
        mv.visitLabel(target);
        mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Exception"});

        // 异常信息保存到局部变量
        int local = mAdapter.newLocal(Type.LONG_TYPE);
        mv.visitVarInsn(Opcodes.ASTORE, local);

        // 抛出异常
        mv.visitVarInsn(Opcodes.ALOAD, local);
        mv.visitInsn(Opcodes.ATHROW);
    }
}
