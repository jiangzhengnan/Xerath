package com.ng.xerathlib.core.plug.base;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.LocalVariablesSorter;

/**
 * 描述:
 *
 * @author Jzn
 * @date 2021/9/14
 */
public interface IAnnotationPlug {

    void init(LocalVariablesSorter adapter, String owner, String name,String methodDesc);

    void onHookMethodStart(MethodVisitor mv);

    void onHookMethodReturn(int opcode,MethodVisitor mv);

    void onHookMethodEnd(MethodVisitor mv);
}
