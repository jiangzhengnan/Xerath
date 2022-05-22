package com.ng.xerathlib.asm.preload;

import java.util.ArrayList;
import java.util.List;

import com.ng.xerathlib.hook.XerathHookHelper;
import com.ng.xerathlib.utils.Parameter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author : jiangzhengnan
 * @creation : 2021/08/25
 * @description :
 * 参数提前收集
 */
public class PreLoadMethodAdapter extends MethodVisitor implements Opcodes {

    private List<Label> labelList = new ArrayList<>();

    private List<Parameter> parameters = new ArrayList<>();

    //存储方法参数的key，方法名+返回值
    private String mMethodKey;
    private String mMethodName;
    private String mOwner;

    public PreLoadMethodAdapter(int access, String name, String descriptor, MethodVisitor methodVisitor,
                                String owner) {
        super(Opcodes.ASM5, methodVisitor);
        mMethodKey = name + descriptor;
        mMethodName = name;
        this.mOwner = owner;
        XerathHookHelper.getInstance().getParams().init(access, owner, name, descriptor);

    }

    @Override
    public void visitCode() {
        super.visitCode();
        parameters = new ArrayList<>();
    }

    @Override
    public void visitEnd() {
        //表示方法扫码完毕
        super.visitEnd();
        if (parameters.size() > 0) {
            XerathHookHelper.getInstance().getParams().putMethodParams(mMethodKey, parameters);
            //LogUtil.printPre(" 参数列表:" + XerathHookHelper.getInstance().getMethodParamsMap().toString());
        }
    }

    /**
     * 获得方法参数列表定义
     */
    @Override
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        //查看入参
        if (!"this".equals(name) && start == labelList.get(0)) {
            Type type = Type.getType(desc);
            if (type.getSort() == Type.OBJECT || type.getSort() == Type.ARRAY) {
                parameters.add(new Parameter(name, "Ljava/lang/Object;", index));
            } else {
                parameters.add(new Parameter(name, desc, index));
            }
        }
        //存储局部变量表
        String temp = mMethodName + "|" + name + "|" + desc + "|" + index;
        //LogUtil.print("抓取 [临时] 变量: temp:" + temp + "           size:" + XerathHookHelper.getInstance().getTempFiledList().size());
        XerathHookHelper.getInstance().getParams().getTempFiledList().add(temp);
        super.visitLocalVariable(name, desc, signature, start, end, index);
    }

    @Override
    public void visitLabel(Label label) {
        labelList.add(label);
        super.visitLabel(label);
    }


}