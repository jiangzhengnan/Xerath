package com.ng.xerathlib.core.plug.base;

import org.objectweb.asm.commons.LocalVariablesSorter;

/**
 * 描述:
 *
 * @author Jzn
 * @date 2021/9/14
 */
public abstract class AnnotationPlug implements IAnnotationPlug {
    protected String mOwner;
    protected String mClassName;
    protected LocalVariablesSorter mAdapter;


    @Override
    public void init(LocalVariablesSorter adapter, String owner, String name) {
        this.mAdapter = adapter;
        this.mOwner = owner;
        this.mClassName = name;
    }
}
