package com.ng.xerathlib.asm;

import com.ng.xerathlib.utils.LogUtil;

import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

/**
 * @Project ASMCostTime
 * @date 2020/6/22
 * @describe
 */
public class XerathClassVisitor extends ClassVisitor {
    /**
     * 是否被修改过
     */
    public boolean changed;
    private String owner;
    private boolean isInterface;

    public XerathClassVisitor(ClassVisitor visitor) {
        super(ASM4, visitor);
    }

    public XerathClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        cv.visit(version, access, name, signature, superName, interfaces);
        owner = name;
        isInterface = (access & ACC_INTERFACE) != 0;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (!isInterface && mv != null && !name.equals("<init>")) {
            LogUtil.print("方法：" + name + " des:" + descriptor + " ");
            //将MethodVisitor交由CostTimeMethodAdapter代理
            mv = new XerathMethodAdapter(access, name, descriptor, mv, owner, new XerathMethodAdapter.OnChangedListener() {
                @Override
                public void onChanged() {
                    changed = true;
                }
            });
        }
        return mv;
    }



}
