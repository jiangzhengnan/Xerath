package com.ng.xerathlib.asm.load;

import com.ng.xerathlib.asm.load.bytex.RemoveMethodCallOptMethodVisitor;
import com.ng.xerathlib.core.XerathHookHelper;
import com.ng.xerathlib.utils.LogUtil;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.ACC_INTERFACE;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
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
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        //成员变量抓取
        if ("Lorg/json/JSONObject;".equals(descriptor)) {
            String temp = owner + " " + name + " " + descriptor;
            if ((access & ACC_STATIC) != 0) {
                LogUtil.print("抓取 [类] 变量: owner:" + owner + " name:" + name + " desc:" + descriptor);
                XerathHookHelper.getInstance().getStaticFiledList().add(temp);
            } else {
                LogUtil.print("抓取 [成员] 变量: owner:" + owner + " name:" + name + " desc:" + descriptor);
                XerathHookHelper.getInstance().getFiledList().add(temp);
            }
        }
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (!isInterface && mv != null && !name.equals("<init>")) {
            //LogUtil.print("方法：" + name + " des:" + descriptor + " ");
            //将 MethodVisitor交由 XerathMethodAdapter 代理
            mv = new XerathMethodAdapter(access, name, descriptor, mv, owner, new XerathMethodAdapter.OnChangedListener() {
                @Override
                public void onChanged() {
                    changed = true;
                }
            });

//            //强行测试接入方法移除
//            if (name.contains("tryRemoveMethod")) {
//                mv = new RemoveMethodCallOptMethodVisitor(mv, access, name, descriptor, signature, exceptions);
//                changed = true;
//            }
        }
        return mv;
    }

}
