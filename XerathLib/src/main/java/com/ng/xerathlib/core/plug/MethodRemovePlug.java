package com.ng.xerathlib.core.plug;

import com.ng.xerathlib.core.XerathHookHelper;
import com.ng.xerathlib.core.plug.base.AnnotationPlug;
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
 */
public class MethodRemovePlug extends AnnotationPlug {
    @Override
    public void init(int access, LocalVariablesSorter adapter, String owner, String name, String methodDesc) {
        super.init(access, adapter, owner, name, methodDesc);
        LogUtil.print("MethodRemovePlug 方法移除 - init");
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
        Object[] removeMethodObjArray = (Object[]) XerathHookHelper.getInstance().getAnnotationParams("removeMethods");
        if (removeMethodObjArray == null) {
            return false;
        }
        String[] removeMethods = copyToStringArray(removeMethodObjArray);

        for (int i = 0; i < removeMethods.length; i++) {
            String[] removeMethodOpts = removeMethods[i].split("\\|");
            if (removeMethodOpts.length != 3) {
                continue;
            }
            LogUtil.print("removeMethodSrc 移除方法: i:" + i + " " + removeMethodOpts[i]);
            String removeOwner = removeMethodOpts[0];
            String removeName = removeMethodOpts[1];
            String removeDescriptor = removeMethodOpts[2];

            if (removeOwner == null || removeName == null || removeDescriptor == null) {
                continue;
            }

            if (removeOwner.equals(owner)) {
                if (removeName.equals(name)) {
                    if (removeDescriptor.equals(desc)) {
                        LogUtil.print("MethodRemovePlug 移除成功:" + removeMethods[i]);
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
