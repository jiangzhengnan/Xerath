package com.ng.xerathlib.asm.load;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.ACC_INTERFACE;
import static org.objectweb.asm.Opcodes.ASM6;

/**
 * 处理jar包里的方法
 */
public final class JarClassVisitor extends ClassVisitor {
    private boolean isInterface;

    private String owner;

    JarClassVisitor(final ClassVisitor cv) {
        super(Opcodes.ASM6, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.owner = name;
        isInterface = (access & ACC_INTERFACE) != 0;

    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name,
                                     final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if (!isInterface && mv != null && !name.equals("<init>")
        ) {
            return new JarCatchMethodVisitor(ASM6, access, owner, name, desc, signature, mv);
        }
        return mv;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}