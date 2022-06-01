package com.ng.xerathlib.asm.base;

import org.objectweb.asm.ClassVisitor;

import static org.objectweb.asm.Opcodes.ACC_INTERFACE;
import static org.objectweb.asm.Opcodes.ASM6;

/**
 * @Project ASMCostTime
 * @date 2020/6/22
 * @describe
 */
public class BaseClassVisitor extends ClassVisitor {

    /**
     * 是否被修改过
     */
    public boolean changed;
    public String owner;
    public boolean isInterface;

    public BaseClassVisitor(ClassVisitor visitor) {
        super(ASM6, visitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        cv.visit(version, access, name, signature, superName, interfaces);
        owner = name;
        isInterface = (access & ACC_INTERFACE) != 0;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}
