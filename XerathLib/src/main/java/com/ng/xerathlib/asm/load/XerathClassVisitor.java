package com.ng.xerathlib.asm.load;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.ACC_INTERFACE;
import static org.objectweb.asm.Opcodes.ASM5;

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
        super(ASM5, visitor);
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
            //LogUtil.print("");
            //LogUtil.print("方法：" + name + " des:" + descriptor + " ");
            //将 MethodVisitor交由 XerathMethodAdapter 代理
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
