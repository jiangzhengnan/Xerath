package com.ng.xerathlib.core.plug.base;

import org.objectweb.asm.commons.LocalVariablesSorter;

/**
 * 描述:
 *
 * @author Jzn
 * @date 2021/9/14
 */
public abstract class AnnotationPlug implements IAnnotationPlug {
    // 方法返回值类型描述符
    protected String mMethodDesc;
    protected String mOwner;
    protected String mMethodName;
    protected LocalVariablesSorter mAdapter;


    @Override
    public void init(LocalVariablesSorter adapter, String owner, String name,String methodDesc) {
        this.mAdapter = adapter;
        this.mOwner = owner;
        this.mMethodName = name;
        this.mMethodDesc = methodDesc;
    }
}
