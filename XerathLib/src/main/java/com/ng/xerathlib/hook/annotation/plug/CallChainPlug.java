package com.ng.xerathlib.hook.annotation.plug;

import com.ng.xerathlib.hook.annotation.plug.base.AnnotationPlug;
import com.ng.xerathlib.utils.LogUtil;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.LocalVariablesSorter;

/**
 * @author : jiangzhengnan.jzn
 * @creation : 2021/09/20
 * @description :
 * 完整链路hook
 */
public class CallChainPlug extends AnnotationPlug {

    @Override
    public void init(int access, LocalVariablesSorter adapter, String owner, String name, String methodDesc) {
        super.init(access, adapter, owner, name, methodDesc);
        LogUtil.print("access:" + access);
        LogUtil.print("owner:" + owner);
        LogUtil.print("name:" + name);
        LogUtil.print("methodDesc:" + methodDesc);
    }

    @Override
    public void onHookMethodStart(MethodVisitor mv) {
        if (isStaticMethod()) {
            //防止静态方法下造成的crash
            return;
        }
        Label label0 = new Label();
        mv.visitLabel(label0);
        mv.visitLineNumber(19, label0);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/ng/xerathcore/CoreHelper", "catchCallChain", "(Ljava/lang/Object;)V", false);
    }

    @Override
    public void onHookMethodReturn(int opcode, MethodVisitor mv) {

    }

    @Override
    public void onHookMethodEnd(MethodVisitor mv) {

    }
}
