package com.ng.xerathlib.asm.core;

import com.android.annotations.Nullable;
import com.ng.xerathlib.hook.XerathHookHelper;
import com.ng.xerathlib.utils.LogUtil;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.LocalVariablesSorter;

import static org.objectweb.asm.Opcodes.ASM6;
import static org.objectweb.asm.Opcodes.ATHROW;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.RETURN;

/**
 * 继承自LocalVariablesSorter 有序遍历素有方法
 */
class CoreMethodAdapter extends LocalVariablesSorter {
    @Nullable
    public XerathHookHelper mHookHelper;

    public CoreMethodAdapter(@Nullable XerathHookHelper hookHelper, int access, String name, String descriptor, MethodVisitor methodVisitor,
                             String owner) {
        super(ASM6, access, descriptor, methodVisitor);
        this.mHookHelper = hookHelper;
        if (mHookHelper != null) {
            mHookHelper.getParams().init(access, methodVisitor, this, owner, name, descriptor);
            mHookHelper.onVisitMethod(access, name, descriptor, methodVisitor, owner);
        }
    }

    /**
     * 遍历代码的开始 声明一个局部变量
     */
    @Override
    public void visitCode() {
        super.visitCode();
        if (mHookHelper != null) {
            mHookHelper.visitMethodCode();
        }
    }

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
            if (mHookHelper != null) {
                mHookHelper.onHookMethodReturn(opcode, mv);
            }
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
        if (mHookHelper != null) {
            boolean isAnnotationed = mHookHelper.visitMethodAnnotation(descriptor, visible);
            if (isAnnotationed) {
                return new AnnotationVisitor(ASM6) {
                    @Override
                    public void visit(String name, Object value) {
                        super.visit(name, value);
                        LogUtil.print("注解: " + descriptor + " 附带参数 key:" + name + " value:" + value.toString());
                        mHookHelper.getParams().putAnnotationParams(name, value);
                    }

                    @Override
                    public AnnotationVisitor visitArray(String name) {
                        //这里比较绕，访问者模式。。
                        String arrayName = name;
                        return new AnnotationVisitor(ASM6) {
                            @Override
                            public void visit(String name, Object value) {
                                LogUtil.print("注解 visitArray: " + descriptor + " 附带参数 key:" + arrayName + " value:" + value);
                                mHookHelper.getParams().putAnnotationArrayParams(arrayName, value);
                                super.visit(name, value);
                            }
                        };
                    }

                };
            }
        }
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        if (mHookHelper != null) {
            mHookHelper.onHookMethodEnd(mv);
        }
        super.visitMaxs(maxStack, maxLocals);
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        if (mHookHelper != null) {
            mHookHelper.getParams().setLineNumber(line);
        }
        super.visitLineNumber(line, start);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if (mHookHelper != null && mHookHelper.onVisitMethodInsn(mv, opcode, owner, name, desc, itf)) {
            //是否打断
            return;
        }
        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }


    @Override
    public void visitEnd() {
        super.visitEnd();
        if (mHookHelper != null) {
            mHookHelper.visitEnd();
        }
    }
}