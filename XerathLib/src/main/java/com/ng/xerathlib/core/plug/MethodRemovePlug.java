package com.ng.xerathlib.core.plug;

import com.ng.xerathlib.core.XerathHookHelper;
import com.ng.xerathlib.core.plug.base.AnnotationPlug;
import com.ng.xerathlib.utils.LogUtil;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.LocalVariablesSorter;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2022/02/16
 * @description :
 */
public class MethodRemovePlug extends AnnotationPlug {
    @Override
    public void init(int access, LocalVariablesSorter adapter, String owner, String name, String methodDesc) {
        super.init(access, adapter, owner, name, methodDesc);
        LogUtil.print("MethodRemovePlug - init");
    }

    @Override
    public void onHookMethodStart(MethodVisitor mv) {

    }

    @Override
    public void onHookMethodReturn(int opcode, MethodVisitor mv) {

    }

    @Override
    public void onHookMethodEnd(MethodVisitor mv) {

    }

    @Override
    public boolean onVisitMethodInsn(MethodVisitor mv, int opcode, String owner, String name, String desc, boolean itf) {
        LogUtil.print("MethodRemovePlug - onVisitMethodInsn");

        String targetOwner = (String) XerathHookHelper.getInstance().getAnnotationParams("owner");
        String targetName = (String) XerathHookHelper.getInstance().getAnnotationParams("name");
        String targetDescriptor = (String) XerathHookHelper.getInstance().getAnnotationParams("descriptor");

        LogUtil.print("targetOwner:" + targetOwner);
        LogUtil.print("targetName:" + targetName);
        LogUtil.print("targetDescriptor:" + targetDescriptor);

        //[实现 方法替换]
        if ("org/json/JSONObject".equals(owner)) {
            String linenumberConst = mLineNumber + "";
            if ("put".equals(name)) {
                if ("(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;".equals(desc)) {
                    LogUtil.print("全局方法替换 in JSONObject");
                    //mv.visitLdcInsn(linenumberConst);
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, targetOwner, targetName, targetDescriptor, false);
                    return true;
                }
            }
        }
        return super.onVisitMethodInsn(mv, opcode, owner, name, desc, itf);
    }

}
