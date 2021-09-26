package com.ng.xerathlib.core.plug.base;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.LocalVariablesSorter;

/**
 * 描述:
 *
 * @author Jzn
 * @date 2021/9/14
 */
public abstract class AnnotationPlug implements IAnnotationPlug {
    // 方法返回值类型描述符
    protected int mMethodAccess;
    protected String mMethodDesc;
    protected String mOwner;
    protected String mMethodName;
    protected LocalVariablesSorter mAdapter;

    @Override
    public void init(int access, LocalVariablesSorter adapter, String owner, String name, String methodDesc) {
        this.mMethodAccess = access;
        this.mAdapter = adapter;
        this.mOwner = owner;
        this.mMethodName = name;
        this.mMethodDesc = methodDesc;
    }

    protected boolean isStaticMethod() {
        return ((Opcodes.ACC_STATIC & mMethodAccess) != 0);
    }
}
