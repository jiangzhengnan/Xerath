package com.ng.xerathlib.asm.tree;

import com.ng.xerathlib.asm.base.BaseClassVisitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 * @Project ASMCostTime
 * @date 2020/6/22
 * @describe
 */
public class TreeClassVisitor extends BaseClassVisitor {

    public TreeClassVisitor(ClassVisitor visitor) {
        super(visitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (!isInterface && mv != null && !name.equals("<init>")) {
            //LogUtil.print("方法：" + name + " des:" + descriptor + " ");
            //强行测试接入方法移除 todo 改为注解
            if (name.contains("tryRemoveMethod")) {
                mv = new RemoveMethodCallOptMethodVisitor(mv, access, name, descriptor, signature, exceptions);
                changed = true;
            }
        }
        return mv;
    }

}
