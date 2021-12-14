package com.ng.xerathlib.asm.load;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


/**
 * @author : jiangzhengnan
 * @creation : 2021/08/25
 * @description :
 * 负责注入
 */
public class JarHookMethodVisitor extends MethodVisitor implements Opcodes {

    private int mMethodAccess;
    private boolean isStaticMethod;

    //参数
    private String mClassName;
    private String mMethodName;
    private String mMethodDesc;
    private String mMethodSignature;

    protected JarHookMethodVisitor(int api, int access, String className, String methodName, String descriptor,
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
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        super.visitVarInsn(opcode, var);
        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
            return;
        }

    }


}