package com.ng.xerath.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author : jiangzhengnan
 * @creation : 2021/08/25
 * @description :
 *
 */
public class TestClassVisitor extends ClassVisitor {

    /**
     * @param api asm的api版本
     */
    public TestClassVisitor(int api) {
        super(api);
    }

    public TestClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (name.equals("initView")) {
            return new TestAdviceAdapter(Opcodes.ASM9,methodVisitor,access,name,descriptor);
        }
        return methodVisitor;
    }
}
