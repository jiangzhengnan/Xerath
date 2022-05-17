package com.ng.xerathlib.hook.annotation.plug.base;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.LocalVariablesSorter;

/**
 * 描述:注解处理器
 *
 * @author Jzn
 * @date 2021/9/14
 */
public interface IAnnotationPlug {

    void init(int access,LocalVariablesSorter adapter, String owner, String name,String methodDesc);

    void onHookMethodStart(MethodVisitor mv);

    void onHookMethodReturn(int opcode,MethodVisitor mv);

    void onHookMethodEnd(MethodVisitor mv);

    boolean onVisitMethodInsn(MethodVisitor mv, int opcode, String owner, String name, String desc, boolean itf);

    void setLineNumber(int lineNumber);
}
