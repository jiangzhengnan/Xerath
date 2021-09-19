package com.ng.xerathlib.asm;

import com.ng.xerathlib.core.AnnotationHelper;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.LocalVariablesSorter;

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
        AnnotationHelper.getInstance().init(this,owner,name,descriptor);
    }


    /**
     * 遍历代码的开始 声明一个局部变量
     */
    @Override
    public void visitCode() {
        super.visitCode();
        AnnotationHelper.getInstance().hookMethodStart(mv);
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
            AnnotationHelper.getInstance().hookMethodReturn(mv);
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
        isAnnotationed = AnnotationHelper.getInstance().isNeedHook(descriptor);
        if (isAnnotationed && onChangedListener != null) {
            onChangedListener.onChanged();
        }
        return super.visitAnnotation(descriptor, visible);

    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        AnnotationHelper.getInstance().hookMethodEnd(mv);
        super.visitMaxs(maxStack, maxLocals);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        AnnotationHelper.getInstance().reset();
    }
}