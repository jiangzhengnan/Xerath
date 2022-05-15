package com.ng.xerathlib.asm.core;

import com.ng.xerathlib.hook.XerathHookHelper;
import com.ng.xerathlib.utils.LogUtil;
import javax.annotation.Nullable;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.LocalVariablesSorter;

import static org.objectweb.asm.Opcodes.ASM5;
import static org.objectweb.asm.Opcodes.ATHROW;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.RETURN;


/**
 * 继承自LocalVariablesSorter 有序遍历素有方法
 */
class CoreMethodAdapter extends LocalVariablesSorter {

    private boolean isAnnotationed;
    @Nullable
    private OnChangedListener onChangedListener;
    private String mMethodName;
    private String mOwner;

    public interface OnChangedListener {
        /**
         * 发生了更改
         */
        void onChanged();
    }


    public CoreMethodAdapter(int access, String name, String descriptor, MethodVisitor methodVisitor,
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
        isFinish = false;
        XerathHookHelper.getInstance().onHookMethodStart(mv);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        super.visitFieldInsn(opcode, owner, name, descriptor);
    }

    @Override
    public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
        super.visitLocalVariable(name, descriptor, signature, start, end, index);
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        super.visitIntInsn(opcode, operand);
    }

    boolean isFinish = false;

    @Override
    public void visitVarInsn(int opcode, int var) {
        super.visitVarInsn(opcode, var);
        //        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
        //            isFinish = true;
        //        }
        //
        //        if (!isFinish && opcode == Opcodes.ASTORE) {
        //            LogUtil.print("注入 [临时] 变量: opcode:" + opcode + " var:" + var);
        //            Label l2 = new Label();
        //            mv.visitLabel(l2);
        //            mv.visitVarInsn(Opcodes.ALOAD, var);
        //            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/ng/xerathcore/CoreHelper", "catchTempJsonFiled", "(Ljava/lang/Object;)V", false);
        //        }

        //        // 临时变量
        //        for (int i = 0; i < XerathHookHelper.getInstance().getTempFiledList().size(); i++) {
        //            String[] values = XerathHookHelper.getInstance().getTempFiledList().get(i).split(" ");
        //            //name + " " + desc + " " + index;
        //            String name = values[0];
        //            String desc = values[1];
        //            int localIndex = Integer.parseInt(values[2]);
        //            int localOpcode = OpcodesUtils.getLoadOpcodeFromDesc(desc);
        //            if (opcode == localOpcode && var == localIndex) {
        //                //LogUtil.print("注入 [临时] 变量: name:" + name + " desc:" + desc);
        //                mv.visitVarInsn(localOpcode, localIndex);
        //                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/ng/xerathcore/CoreHelper", "catchTempJsonFiled", "(Ljava/lang/Object;)V", false);
        //            }
        //        }
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
            //            // 成员变量
            //            for (int i = 0; i < XerathHookHelper.getInstance().getFiledList().size(); i++) {
            //                String[] values = XerathHookHelper.getInstance().getFiledList().get(i).split(" ");
            //                mv.visitVarInsn(Opcodes.ALOAD, 0);
            //                mv.visitFieldInsn(Opcodes.GETFIELD, values[0], values[1], values[2]);
            //                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/ng/xerathcore/CoreHelper", "catchJsonFiled", "(Ljava/lang/Object;)V", false);
            //            }
            //            // 类变量
            //            for (int i = 0; i < XerathHookHelper.getInstance().getStaticFiledList().size(); i++) {
            //                String[] values = XerathHookHelper.getInstance().getStaticFiledList().get(i).split(" ");
            //                mv.visitVarInsn(Opcodes.ALOAD, 0);
            //                mv.visitFieldInsn(Opcodes.GETSTATIC, values[0], values[1], values[2]);
            //                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/ng/xerathcore/CoreHelper", "catchJsonFiled", "(Ljava/lang/Object;)V", false);
            //            }
            //
            //            // 收集局部变量
            //            if (XerathHookHelper.getInstance().getTempFiledList().size() > 0) {
            //                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/ng/xerathcore/CoreHelper", "onCatchTempJsonFileFinish", "()V", false);
            //            }
            //
            //
            //            if (XerathHookHelper.getInstance().getFiledList().size() > 0 ||
            //                    XerathHookHelper.getInstance().getStaticFiledList().size() > 0 ||
            //                    XerathHookHelper.getInstance().getTempFiledList().size() > 0) {
            //                LogUtil.print("数量:" + XerathHookHelper.getInstance().getFiledList().size() + " " + XerathHookHelper.getInstance().getStaticFiledList().size() + " " + XerathHookHelper.getInstance().getTempFiledList().size());
            //                onChangedListener.onChanged();
            //            }
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

                @Override
                public AnnotationVisitor visitArray(String name) {
                    //这里比较绕，访问者模式。。
                    String arrayName = name;
                    return new AnnotationVisitor(ASM5) {
                        @Override
                        public void visit(String name, Object value) {
                            LogUtil.print("注解 visitArray: " + descriptor + " 附带参数 key:" + arrayName + " value:" + value);
                            XerathHookHelper.getInstance()
                                            .putAnnotationArrayParams(arrayName, value);
                            super.visit(name, value);
                        }
                    };
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
        XerathHookHelper.getInstance().setLineNumber(line);
        super.visitLineNumber(line, start);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if (XerathHookHelper.getInstance().onVisitMethodInsn(mv, opcode, owner, name, desc, itf)) {
            //是否打断
            return;
        }
        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }

    @Override
    public void visitParameter(String name, int access) {
        super.visitParameter(name, access);
    }


    @Override
    public void visitEnd() {
        super.visitEnd();
        XerathHookHelper.getInstance().resetOnMethodEnd();
    }
}