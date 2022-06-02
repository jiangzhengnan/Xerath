package com.ng.xerathlib.asm.base;

import com.android.annotations.Nullable;
import com.ng.xerathlib.hook.XerathHookHelper;
import com.ng.xerathlib.hook.XerathHookManager;
import org.objectweb.asm.ClassVisitor;

import static org.objectweb.asm.Opcodes.ACC_ABSTRACT;
import static org.objectweb.asm.Opcodes.ACC_INTERFACE;
import static org.objectweb.asm.Opcodes.ASM6;

/**
 * @Project ASMCostTime
 * @date 2020/6/22
 * @describe
 */
public class BaseClassVisitor extends ClassVisitor {

    public boolean changed;
    public String owner;
    public boolean isInterface;
    public boolean isAbstract;

    @Nullable
    public XerathHookHelper mHookHelper;

    public BaseClassVisitor(ClassVisitor visitor) {
        super(ASM6, visitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        mHookHelper = XerathHookManager.getInstance().getHelper(name);
        cv.visit(version, access, name, signature, superName, interfaces);
        owner = name;
        isInterface = (access & ACC_INTERFACE) != 0;
        isAbstract = (access & ACC_ABSTRACT) != 0;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }

    public boolean changed() {
        return mHookHelper.isClassChanged();
    }
}
