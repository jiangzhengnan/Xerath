package com.ng.xerath.asm.visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.ASM6;

/**
 * @author : jiangzhengnan
 * @creation : 2021/08/25
 * @description :
 */
public class TestClassVisitor extends ClassVisitor {
    private String className;

    public TestClassVisitor(ClassVisitor visitor) {
        super(ASM6, visitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;

    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (name.equals("test")) {
            return new TestAdviceAdapter(ASM6, access, descriptor, methodVisitor);
        }
        return methodVisitor;

    }
}
