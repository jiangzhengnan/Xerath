package com.ng.xerathlib.hook;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2022/05/15
 * @description :
 */
public interface HookLifeCycle {
    //class
    boolean onVisitClass(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces);

    void visitClassField(int access, String name, String descriptor, String signature, Object value);

    //class 是否修改class
    boolean isClassChanged();

    //method
    void onVisitMethod(int access, String name, String descriptor, MethodVisitor methodVisitor,
                       String owner);
    //method 是否打断方法
    boolean onVisitMethodInsn(MethodVisitor mv, int opcode, String owner, String name, String desc, boolean itf);

    void visitMethodCode();

    void visitLineNumber(int line, Label start);

    boolean visitMethodAnnotation(String descriptor, boolean visible);

    void onHookMethodReturn(int opcode, MethodVisitor mv);

    void onHookMethodEnd(MethodVisitor mv);

    void visitEnd();

}
