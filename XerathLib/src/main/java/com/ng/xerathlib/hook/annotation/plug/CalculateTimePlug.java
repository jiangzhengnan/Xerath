package com.ng.xerathlib.hook.annotation.plug;

import com.ng.xerathlib.hook.annotation.plug.base.AnnotationPlug;

import com.ng.xerathlib.utils.LogUtil;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
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
public class CalculateTimePlug extends AnnotationPlug {
    private int time;

    @Override
    public void onHookMethodStart(MethodVisitor mv) {
        LogUtil.print("耗时统计 plug 开始");
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        time = mParams.mAdapter.newLocal(Type.LONG_TYPE);
        mv.visitVarInsn(LSTORE, time);
    }

    @Override
    public void onHookMethodReturn(int opcode,MethodVisitor mv) {
        //这里的代码都可以由ASM插件生成
        //Label可以生成局部变量
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        mv.visitVarInsn(LLOAD, time);
        mv.visitInsn(LSUB);
        mv.visitVarInsn(LSTORE, 3);
        Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLdcInsn(mParams.mOwner);
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        mv.visitLdcInsn("【耗时统计】:func " + mParams.mMethodName + " cost Time:");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(LLOAD, 3);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        //mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitMethodInsn(INVOKESTATIC, "com/ng/xerathcore/CoreHelper", "catchLog", "(Ljava/lang/String;)V", false);

        LogUtil.print("耗时统计 plug 结束");
    }

    @Override
    public void onHookMethodEnd(MethodVisitor mv) {
    }
}
