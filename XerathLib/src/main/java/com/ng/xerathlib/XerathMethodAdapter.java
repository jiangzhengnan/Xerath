package com.ng.xerathlib;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

import static org.objectweb.asm.Opcodes.ASM5;
import static org.objectweb.asm.Opcodes.ATHROW;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.LLOAD;
import static org.objectweb.asm.Opcodes.LSTORE;
import static org.objectweb.asm.Opcodes.LSUB;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.RETURN;


/**
 * 继承自LocalVariablesSorter 有序遍历素有方法
 */
class XerathMethodAdapter extends LocalVariablesSorter {
    private String name;
    private boolean isAnnotationed;
    private int time;

    private OnChangedListener onChangedListener;
    private String owner;

    public interface OnChangedListener {
        /**
         * 发生了更改
         */
        void onChanged();
    }

    public XerathMethodAdapter(int access, String name, String descriptor, MethodVisitor methodVisitor,
                               String owner, OnChangedListener onChangedListener) {
        super(ASM5, access, descriptor, methodVisitor);
        this.name = name;
        this.onChangedListener = onChangedListener;
        this.owner = owner;
    }


    /**
     * 遍历代码的开始 声明一个局部变量
     */
    @Override
    public void visitCode() {
        super.visitCode();
        if (isAnnotationed) {
            //Label label0 = new Label();
            //mv.visitLabel(label0);
            //mv.visitLineNumber(35, label0);
            //mv.visitLdcInsn("测试输出～");
            //mv.visitMethodInsn(INVOKESTATIC, "com/ng/xerathcore/CoreUtils", "catchLog", "(Ljava/lang/String;)V", false);

            //mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            //mv.visitLdcInsn("what the fuck");
            //mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            time = newLocal(Type.LONG_TYPE);
            mv.visitVarInsn(LSTORE, time);
        }
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
            if (isAnnotationed) {
                //这里的代码都可以由ASM插件生成
                //Label可以生成局部变量
                Label l1 = new Label();
                mv.visitLabel(l1);
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                mv.visitVarInsn(LLOAD, time);
                mv.visitInsn(LSUB);
                mv.visitVarInsn(LSTORE, 3);
                Label l2 = new Label();
                mv.visitLabel(l2);
                mv.visitLdcInsn(owner);
                mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                mv.visitLdcInsn("耗时统计: func " + name + " cost Time:");
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                mv.visitVarInsn(LLOAD, 3);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                //mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
                mv.visitMethodInsn(INVOKESTATIC, "com/ng/xerathcore/CoreUtils", "catchLog", "(Ljava/lang/String;)V", false);
            }
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
        isAnnotationed = (descriptor.contains("CalculateTime"));
        LogUtil.print("  注解:" + descriptor + " 是否需要更改:" + isAnnotationed);
        if (isAnnotationed && onChangedListener != null) {
            onChangedListener.onChanged();
        }
        return super.visitAnnotation(descriptor, visible);

    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack, maxLocals);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}