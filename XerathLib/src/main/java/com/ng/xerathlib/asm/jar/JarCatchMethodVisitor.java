package com.ng.xerathlib.asm.jar;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;


/**
 * @author : jiangzhengnan
 * @creation : 2021/08/25
 * @description :
 * 负责分析
 */
public class JarCatchMethodVisitor extends MethodVisitor implements Opcodes {
    private int mMethodAccess;
    private boolean isStaticMethod;

    //参数
    private String mClassName;
    private String mMethodName;
    private String mMethodDesc;
    private String mMethodSignature;

    private List<Integer> varList = new ArrayList<>();

    protected JarCatchMethodVisitor(int api, int access, String className, String methodName, String descriptor,
                                    String signature, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
        mClassName = className;
        mMethodName = methodName;
        mMethodDesc = descriptor;
        mMethodSignature = signature;
        mMethodAccess = access;
        isStaticMethod = ((Opcodes.ACC_STATIC & access) != 0);
    }

    @Override
    public void visitCode() {
        super.visitCode();
        varList = new ArrayList<>();
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        super.visitVarInsn(opcode, var);
        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
            return;
        }

    }


    @Override
    public void visitEnd() {
        varList.clear();
        super.visitEnd();
    }

    @Override
    public void visitInsn(int opcode) {
        // 收集局部变量
        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
            //mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/noah/sdk/business/monitor/HookCatchHelper", "onCatchTempJsonFileFinish", "()V", false);
            //mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/noah/sdk/business/monitor/HookCatchHelper", "catchTest", "()V", false);
        }
        super.visitInsn(opcode);
    }

}