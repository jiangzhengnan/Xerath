package com.ng.xerathlib.asm.preload;

import com.ng.xerathlib.asm.base.BaseClassVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.ACC_INTERFACE;

/**
 * @author : jiangzhengnan
 * @creation : 2021/08/25
 * @description :
 * 预加载数据 ClassVisitor
 */
public class PreLoadClassVisitor extends BaseClassVisitor {

    public PreLoadClassVisitor(ClassVisitor visitor) {
        super(visitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.owner = name;
        isInterface = (access & ACC_INTERFACE) != 0;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (!isInterface && mv != null && !name.equals("<init>")) {
            //LogUtil.printPre("方法：" + name + " des:" + descriptor + " ");
            //将MethodVisitor交由 XerathPreLoadAdviceAdapter 代理
            mv = new PreLoadMethodAdapter(mHookHelper, access, name, descriptor, mv, owner);
        }
        return mv;
    }
}
