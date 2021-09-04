package com.ng.xerathlib;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class CustomAdviceAdapter extends AdviceAdapter {
    protected CustomAdviceAdapter(MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(Opcodes.ASM9, methodVisitor, access, name, descriptor);
    }
}
