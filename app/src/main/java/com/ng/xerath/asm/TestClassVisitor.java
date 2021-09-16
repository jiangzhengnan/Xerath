package com.ng.xerath.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.ASM4;
import static org.objectweb.asm.Opcodes.ASM5;

/**
 * @author : jiangzhengnan
 * @creation : 2021/08/25
 * @description :
 *
 */
public class TestClassVisitor extends ClassVisitor {

    public TestClassVisitor(ClassVisitor visitor) {
        super(ASM5, visitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (name.equals("tryCatchMethod")) {
            return new TestAdviceAdapter(ASM5,access,descriptor,methodVisitor);
        }
        return methodVisitor;
    }
}
