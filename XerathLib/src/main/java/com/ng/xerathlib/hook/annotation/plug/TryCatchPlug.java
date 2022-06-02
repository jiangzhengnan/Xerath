package com.ng.xerathlib.hook.annotation.plug;

import com.ng.xerathlib.hook.annotation.plug.base.AnnotationPlug;
import com.ng.xerathlib.hook.params.HookParams;
import com.ng.xerathlib.utils.ASMUtil;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.LocalVariablesSorter;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;

import com.ng.xerathlib.utils.Pair;

/**
 * 描述: 统计耗时
 *
 * @author Jzn
 * @date 2021/9/14
 */
public class TryCatchPlug extends AnnotationPlug {
    //自定义异常处理类
    private static String exceptionHandleClass;
    private static String exceptionHandleMethod;

    // 开头
    private final Label startLabel = new Label();
    // 结尾
    private final Label endLabel = new Label();
    // 处理
    private final Label handlerLabel = new Label();
    // 返回
    private final Label returnLabel = new Label();

    @Override
    public void init(HookParams params) {
        super.init(params);
        //增加自定义异常处理类
        exceptionHandleClass = "com/ng/xerathcore/CoreHelper";
        exceptionHandleMethod = "handleException";
    }

    @Override
    public void onHookMethodStart(MethodVisitor mv) {
        mv.visitLdcInsn("【异常捕获】开始");
        mv.visitMethodInsn(INVOKESTATIC, "com/ng/xerathcore/CoreHelper", "catchLog", "(Ljava/lang/String;)V", false);
        // 1标志：try块开始位置
        mv.visitTryCatchBlock(startLabel,
                endLabel,
                handlerLabel,
                "java/lang/Exception");
        mv.visitLabel(startLabel);
    }

    @Override
    public void onHookMethodReturn(int opcode, MethodVisitor mv) {
    }

    @Override
    public void onHookMethodEnd(MethodVisitor mv) {
        // 2标志：try块结束
        mv.visitLabel(endLabel);

        // 3标志：catch块开始位置
        mv.visitLabel(handlerLabel);
        mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Exception"});
        // 0代表this， 1 第一个参数，异常信息保存到局部变量
        mv.visitVarInsn(ASTORE, 1);
        // 从local variables取出局部变量到operand stack
        mv.visitVarInsn(ALOAD, 1);
        // 自定义异常处理
        if (exceptionHandleClass != null && exceptionHandleMethod != null) {
            mv.visitMethodInsn(INVOKESTATIC, exceptionHandleClass,
                    exceptionHandleMethod, "(Ljava/lang/Exception;)V", false);
        } else {
            // 没提供处理类就直接抛出异常
            mv.visitInsn(Opcodes.ATHROW);
        }
        // catch结束，方法返回默认值收工
        Pair defaultVo = ASMUtil.getDefaultByDesc(mParams.mMethodDesc);
        int value = defaultVo.key;
        int opcode = defaultVo.value;
        if (value >= 0) {
            mv.visitInsn(value);
        }
        mv.visitInsn(opcode);

    }
}
