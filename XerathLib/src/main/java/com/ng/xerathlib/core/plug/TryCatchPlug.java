package com.ng.xerathlib.core.plug;

import com.ng.xerathlib.core.plug.base.AnnotationPlug;
import com.ng.xerathlib.utils.ASMUtil;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import javafx.util.Pair;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;

/**
 * 描述: 统计耗时
 *
 * @author Jzn
 * @date 2021/9/14
 */
public class TryCatchPlug extends AnnotationPlug {
    //自定义异常处理
    private String exceptionHandleClass = "com/ng/xerathcore/CoreUtils";
    private String exceptionHandleMethod = "handleException";

    private Label startLabel = new Label(),   // 开头
            endLabel = new Label(),           // 结尾
            handlerLabel = new Label(),       // 处理
            returnLabel = new Label();        // 返回

    @Override
    public void hookMethodStart(MethodVisitor mv) {
        mv.visitLdcInsn("【异常捕获】开始");
        mv.visitMethodInsn(INVOKESTATIC, "com/ng/xerathcore/CoreUtils", "catchLog", "(Ljava/lang/String;)V", false);
        // 1标志：try块开始位置
        mv.visitTryCatchBlock(startLabel,
                endLabel,
                handlerLabel,
                "java/lang/Exception");
        mv.visitLabel(startLabel);
    }

    @Override
    public void hookMethodReturn(MethodVisitor mv) {
    }

    @Override
    public void hookMethodEnd(MethodVisitor mv) {
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
        Pair<Integer, Integer> defaultVo = ASMUtil.getDefaultByDesc(methodDesc);
        int value = defaultVo.getKey();
        int opcode = defaultVo.getValue();
        if (value >= 0) {
            mv.visitInsn(value);
        }
        mv.visitInsn(opcode);

    }
}
