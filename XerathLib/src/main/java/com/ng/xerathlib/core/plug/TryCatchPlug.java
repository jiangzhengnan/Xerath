package com.ng.xerathlib.core.plug;

import com.ng.xerathlib.core.plug.base.AnnotationPlug;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.ATHROW;
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
        mv.visitLdcInsn("【异常捕获】开始");
        mv.visitMethodInsn(INVOKESTATIC, "com/ng/xerathcore/CoreUtils", "catchLog", "(Ljava/lang/String;)V", false);

        //标志：try块开始位置
        mv.visitLabel(from);
        mv.visitTryCatchBlock(from,
                to,
                target,
                "java/lang/Exception");
    }

    @Override
    public void hookMethodReturn(MethodVisitor mv) {
    }

    @Override
    public void hookMethodEnd(MethodVisitor mv) {
        // visit try block end label
        mv.visitLabel(to);
        // visit normal execution exit block
        //mv.visitJumpInsn(Opcodes.GOTO, exitBlock);
        // visit catch exception block
        mv.visitLabel(target);
        // store the exception
        mv.visitVarInsn(Opcodes.ASTORE, 10);
        // load the exception
        mv.visitVarInsn(Opcodes.ALOAD, 10);
        // call printStackTrace()
        //this.visitInsn(Opcodes.ATHROW);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Exception", "printStackTrace", "()V");
        // exit from this dynamic block
        //mv.visitLabel(exitBlock);

//        //标志：try块结束
//        mv.visitLabel(to);
//        //标志：catch块开始位置
//        mv.visitLabel(target);
//        mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Exception"});
//
//        // 异常信息保存到局部变量
//        //int localException = mAdapter.newLocal(Type.LONG_TYPE);
//        //mv.visitVarInsn(ASTORE, localException);
//
//        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
//        mv.visitInsn(DUP);
//        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
//        mv.visitLdcInsn("【异常捕获】:");
//        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
//       // mv.visitVarInsn(LLOAD, localException);
//        //mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Exception", "getMessage", "()Ljava/lang/String;", false);
//        //mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
//        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
//        mv.visitMethodInsn(INVOKESTATIC, "com/ng/xerathcore/CoreUtils", "catchLog", "(Ljava/lang/String;)V", false);
//
//        // 抛出异常
//        //mv.visitVarInsn(ALOAD, localException);
//        //mv.visitInsn(ATHROW);
    }
}
