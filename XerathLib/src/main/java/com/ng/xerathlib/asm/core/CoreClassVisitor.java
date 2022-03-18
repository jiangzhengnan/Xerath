package com.ng.xerathlib.asm.core;

import com.ng.xerathlib.asm.base.BaseClassVisitor;
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
public class CoreClassVisitor extends BaseClassVisitor {

    public CoreClassVisitor(ClassVisitor visitor) {
        super(visitor);
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
            mv = new CoreMethodAdapter(access, name, descriptor, mv, owner, new CoreMethodAdapter.OnChangedListener() {
                @Override
                public void onChanged() {
                    changed = true;
                }
            });
        }
        return mv;
    }

}
