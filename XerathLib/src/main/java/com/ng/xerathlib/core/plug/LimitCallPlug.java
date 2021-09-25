package com.ng.xerathlib.core.plug;

import com.ng.xerathlib.core.XerathHookHelper;
import com.ng.xerathlib.core.plug.base.AnnotationPlug;
import com.ng.xerathlib.utils.ASMUtil;
import com.ng.xerathlib.utils.Pair;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.LocalVariablesSorter;


/**
 * @author : jiangzhengnan.jzn
 * @creation : 2021/09/19
 * @description :
 */
public class LimitCallPlug extends AnnotationPlug {
    //判断语句
    private final Label judgeLabel = new Label();

    //else
    private final Label elseLabel = new Label();

    //if
    private final Label ifLabel = new Label();

    private long clickTime = 0;

    @Override
    public void init(LocalVariablesSorter adapter, String owner, String name, String methodDesc) {
        super.init(adapter, owner, name, methodDesc);
    }

    @Override
    public void onHookMethodStart(MethodVisitor mv) {
        Long clickTimeL = (Long) XerathHookHelper.getInstance().getAnnotationParams("time");
        if (clickTimeL != null) {
            clickTime = clickTimeL;
        }
        mv.visitLabel(judgeLabel);
        mv.visitLineNumber(0, judgeLabel);


        mv.visitLdcInsn(clickTime);

        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/ng/xerathcore/utils/CoreUtils",
                "needLimitCall", "(J)Z", false);

        mv.visitJumpInsn(Opcodes.IFEQ, elseLabel);

        mv.visitLabel(ifLabel);
        mv.visitLineNumber(1, ifLabel);
        mv.visitLdcInsn("阻止频繁调用");
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/ng/xerathcore/CoreHelper", "catchLog", "(Ljava/lang/String;)V", false);

        //方法返回默认值
        Pair defaultVo = ASMUtil.getDefaultByDesc(mMethodDesc);
        int value = defaultVo.key;
        int opcode = defaultVo.value;
        if (value >= 0) {
            mv.visitInsn(value);
        }
        mv.visitInsn(opcode);

        mv.visitLabel(elseLabel);
        mv.visitLineNumber(3, elseLabel);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
    }

    @Override
    public void onHookMethodReturn(int opcode, MethodVisitor mv) {
    }

    @Override
    public void onHookMethodEnd(MethodVisitor mv) {
    }
}
