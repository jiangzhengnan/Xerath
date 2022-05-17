package com.ng.xerathlib.hook.annotation.plug;

import com.ng.xerathlib.hook.XerathHookHelper;
import com.ng.xerathlib.hook.annotation.plug.base.AnnotationPlug;
import com.ng.xerathlib.hook.params.HookParams;
import com.ng.xerathlib.utils.LogUtil;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.LocalVariablesSorter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2022/02/16
 * @description :
 * 方法替换组件
 */
public class MethodReplacePlug extends AnnotationPlug {

    @Override
    public void init(HookParams params) {
        super.init(params);
        LogUtil.print("MethodReplacePlug - init");
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
        LogUtil.print("MethodReplacePlug - onVisitMethodInsn");
        Object[] targetMethodObjArray = (Object[]) XerathHookHelper.getInstance().getParams().getAnnotationParams("targetMethods");
        Object[] replaceMethodObjArray = (Object[]) XerathHookHelper.getInstance().getParams().getAnnotationParams("replaceMethods");
        if (targetMethodObjArray == null || replaceMethodObjArray == null) {
            return false;
        }
        String[] targetMethods = copyToStringArray(targetMethodObjArray);
        String[] replaceMethods = copyToStringArray(replaceMethodObjArray);

        int loopSize = Math.min(targetMethods.length, replaceMethods.length);

        for (int i = 0; i < loopSize; i++) {
            String[] targetMethodOpts = targetMethods[i].split("\\|");
            String[] replaceMethodMethodOpts = replaceMethods[i].split("\\|");
            if (targetMethodOpts.length != 3 || replaceMethodMethodOpts.length != 3) {
                continue;
            }

            LogUtil.print("targetMethodSrc:" + targetMethods[i]);
            LogUtil.print("replaceMethodMethodSrc:" + replaceMethods[i]);

            String replaceOwner = replaceMethodMethodOpts[0];
            String replaceName = replaceMethodMethodOpts[1];
            String replaceDescriptor = replaceMethodMethodOpts[2];

            if (replaceOwner == null || replaceName == null || replaceDescriptor == null) {
                continue;
            }

            String targetOwner = targetMethodOpts[0];
            String targetName = targetMethodOpts[1];
            String targetDescriptor = targetMethodOpts[2];

            if (targetOwner == null || targetName == null || targetDescriptor == null) {
                continue;
            }

            if (replaceOwner.equals(owner)) {
                if (replaceName.equals(name)) {
                    if (replaceDescriptor.equals(desc)) {
                        LogUtil.print("MethodReplacePlug 注入成功:" + targetOwner + " " + targetName + " " + targetDescriptor);
                        mv.visitMethodInsn(Opcodes.INVOKESTATIC, targetOwner, targetName, targetDescriptor, false);
                        return true;
                    }
                }
            }
        }

        return true;
    }

    private String[] copyToStringArray(Object[] objArray) {
        List<String> resultList = new ArrayList<>();
        if (objArray == null || objArray.length == 0) {
            return null;
        }
        for (Object temp : objArray) {
            if (temp instanceof String) {
                resultList.add((String) temp);
            }
        }
        return resultList.toArray(new String[resultList.size()]);
    }


}
