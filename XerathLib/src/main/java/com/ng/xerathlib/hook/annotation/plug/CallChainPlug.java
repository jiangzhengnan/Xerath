package com.ng.xerathlib.hook.annotation.plug;

import com.ng.xerathlib.hook.annotation.plug.base.AnnotationPlug;
import com.ng.xerathlib.hook.params.HookParams;
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
    public void init(HookParams params) {
        super.init(params);
        LogUtil.print("access:" + mParams.mMethodAccess);
        LogUtil.print("owner:" + mParams.mOwner);
        LogUtil.print("name:" + mParams.mMethodName);
        LogUtil.print("methodDesc:" + mParams.mMethodDesc);
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
