package com.ng.xerathlib.asm.load;

import com.ng.xerathlib.asm.preload.Parameter;
import com.ng.xerathlib.core.XerathHookHelper;
import com.ng.xerathlib.utils.LogUtil;
import com.ng.xerathlib.utils.OpcodesUtils;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

import java.util.List;

import static org.objectweb.asm.Opcodes.ASM5;
import static org.objectweb.asm.Opcodes.ATHROW;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.RETURN;


/**
 * 继承自LocalVariablesSorter 有序遍历素有方法
 */
class XerathMethodAdapter extends LocalVariablesSorter {

    private boolean isAnnotationed;
    private OnChangedListener onChangedListener;
    private String mMethodName;
    private String mOwner;

    public interface OnChangedListener {
        /**
         * 发生了更改
         */
        void onChanged();
    }


    public XerathMethodAdapter(int access, String name, String descriptor, MethodVisitor methodVisitor,
                               String owner, OnChangedListener onChangedListener) {
        super(ASM5, access, descriptor, methodVisitor);
        this.onChangedListener = onChangedListener;
        this.mMethodName = name;
        this.mOwner = owner;
        XerathHookHelper.getInstance().init(access, this, owner, name, descriptor);

    }


    /**
     * 遍历代码的开始 声明一个局部变量
     */
    @Override
    public void visitCode() {
        super.visitCode();
        XerathHookHelper.getInstance().onHookMethodStart(mv);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        super.visitFieldInsn(opcode, owner, name, descriptor);

    }


    @Override
    public void visitIntInsn(int opcode, int operand) {
        super.visitIntInsn(opcode, operand);
    }


    /**
     * 遍历操作码 判断是否是return语句 如果是return 就插入我们的代码
     *
     * @param opcode 操作码
     */
    @Override
    public void visitInsn(int opcode) {
        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
            XerathHookHelper.getInstance().onHookMethodReturn(opcode, mv);
        }
        super.visitInsn(opcode);
    }


    /**
     * @param descriptor 最先执行 判断是否存在注解 如果存在 就进行插桩
     * @param visible
     * @return
     */
    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        isAnnotationed = XerathHookHelper.getInstance().isNeedHook(descriptor);
        if (isAnnotationed && onChangedListener != null) {
            LogUtil.print("正式处理开始(" + mMethodName + ")---");
            onChangedListener.onChanged();
            return new AnnotationVisitor(ASM5) {
                @Override
                public void visit(String name, Object value) {
                    super.visit(name, value);
                    LogUtil.print("注解: " + descriptor + " 附带参数 key:" + name + " value:" + value.toString());
                    XerathHookHelper.getInstance().putAnnotationParams(name, value);
                }
            };
        }
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        XerathHookHelper.getInstance().onHookMethodEnd(mv);
        super.visitMaxs(maxStack, maxLocals);
        if (isAnnotationed) {
            LogUtil.print("正式处理结束(" + mMethodName + ")---");
        }
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        super.visitLineNumber(line, start);
    }


    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        ////LogUtil.print("全局方法替换: owner:" + owner + " name:" + name + " desc:" + descriptor);
        ////实现全局方法替换
        //if ("org/json/JSONObject".equals(owner)) {
        //    if ("put".equals(name)) {
        //        if ("(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;".equals(descriptor)) {
        //            //LogUtil.print("全局方法替换 in");
        //            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        //            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/ng/xerathcore/utils/JsonPrinter", "print", "(Ljava/lang/String;Ljava/lang/Object;)V", false);
        //            onChangedListener.onChanged();
        //            return;
        //        }
        //    }
        //}
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        XerathHookHelper.getInstance().reset();
    }
}