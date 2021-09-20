package com.ng.xerathlib.core.plug;

import com.ng.xerathlib.core.AnnotationHelper;
import com.ng.xerathlib.core.plug.base.AnnotationPlug;
import com.ng.xerathlib.utils.ASMUtil;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.LocalVariablesSorter;

import javafx.util.Pair;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
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
    public void hookMethodStart(MethodVisitor mv) {
        Long clickTimeL = (Long) AnnotationHelper.getInstance().getParams("time");
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
        Pair<Integer, Integer> defaultVo = ASMUtil.getDefaultByDesc(methodDesc);
        int value = defaultVo.getKey();
        int opcode = defaultVo.getValue();
        if (value >= 0) {
            mv.visitInsn(value);
        }
        mv.visitInsn(opcode);

        mv.visitLabel(elseLabel);
        mv.visitLineNumber(3, elseLabel);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
    }

    @Override
    public void hookMethodReturn(MethodVisitor mv) {
    }

    @Override
    public void hookMethodEnd(MethodVisitor mv) {
    }
}
