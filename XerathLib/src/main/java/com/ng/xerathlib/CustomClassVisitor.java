package com.ng.xerathlib;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Arrays;

/**
 *
 */
public class CustomClassVisitor extends ClassVisitor {

    private String mClassName;
    private String[] mInterfaces;

    public CustomClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM9, classVisitor);
    }

    /**
     * @param version
     * @param access
     * @param name
     * @param signature
     * @param superName  父类名
     * @param interfaces
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        mClassName = name;
        mInterfaces = interfaces;
        System.out.println("[Pumpkin] visit:version: " + version);
        System.out.println("[Pumpkin] visit:name: " + name);
        System.out.println("[Pumpkin] visit:signature: " + signature);
        System.out.println("[Pumpkin] visit:superName: " + superName);
        System.out.println("[Pumpkin] visit:mInterfaces: " + Arrays.toString(mInterfaces));

    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println("[Pumpkin] visitMethod:抓方法");
        System.out.println("[Pumpkin] visitMethod:access: " + access);
        System.out.println("[Pumpkin] visitMethod:name: " + name);
        System.out.println("[Pumpkin] visitMethod:descriptor: " + descriptor);
        System.out.println("[Pumpkin] visitMethod:signature: " + signature);

        MethodVisitor methodVisitor = cv.visitMethod(access, name, descriptor, signature, exceptions);


        //hook 所有OnClickListener
        if (isMatchOnClickListener(name)) {
            System.out.println("实现了该接口的类： " + name);
            methodVisitor = new CustomAdviceAdapter(methodVisitor, access, name, descriptor) {
                Label label0 = new Label();
                private boolean isNeedCalculateTime = false;

                @Override
                public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                    System.out.println("[Pumpkin] 注解!!: " + descriptor);
                    if (descriptor.contains("CalculateTime")) {
                        isNeedCalculateTime = true;
                    } else {
                        isNeedCalculateTime = false;
                    }
                    return super.visitAnnotation(descriptor, visible);
                }

                @Override
                protected void onMethodEnter() {
                    super.onMethodEnter();
                    if (isNeedCalculateTime) {
                        System.out.println("[Pumpkin] 注解 onMethodEnter: " + descriptor);
                        mv.visitLabel(label0);
                        mv.visitLineNumber(53, label0);
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
                        mv.visitVarInsn(ASTORE, 1);
                    }
                }

                @Override
                protected void onMethodExit(int opcode) {
                    if (isNeedCalculateTime) {
                        System.out.println("[Pumpkin] 注解 onMethodExit: " + descriptor);
                        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                        mv.visitLdcInsn("点击结束 finish");
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                        Label label1 = new Label();
                        mv.visitLabel(label1);
                        mv.visitLineNumber(54, label1);
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
                        mv.visitVarInsn(ASTORE, 2);
                        Label label2 = new Label();
                        mv.visitLabel(label2);
                        mv.visitLineNumber(55, label2);
                        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
                        mv.visitInsn(DUP);
                        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                        mv.visitLdcInsn("\u8017\u65f6:");
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                        mv.visitVarInsn(ALOAD, 2);
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J", false);
                        mv.visitVarInsn(ALOAD, 1);
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J", false);
                        mv.visitInsn(LSUB);
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                        Label label3 = new Label();
                        mv.visitLabel(label3);
                        mv.visitLineNumber(56, label3);
                        mv.visitInsn(RETURN);
                        Label label4 = new Label();
                        mv.visitLabel(label4);
                        mv.visitLocalVariable("this", "Lcom/ng/xerath/MainActivity;", null, label0, label4, 0);
                        mv.visitLocalVariable("START_TIME", "Ljava/lang/Long;", null, label1, label4, 1);
                        mv.visitLocalVariable("END_TIME", "Ljava/lang/Long;", null, label2, label4, 2);
                        mv.visitMaxs(6, 3);
                        mv.visitEnd();
                    }
                    super.onMethodExit(opcode);
                }
            };
        }

        //找到所有注解方法，统计耗时
        return methodVisitor;
    }


    private boolean isMatchOnClickListener(String methodName) {
        return true;
        //if (mInterfaces == null) {
        //    return false;
        //}
        //for (String mInterface : mInterfaces) {
        //    if (mInterface.equals("android/view/View$OnClickListener") && "onClick".equals(methodName)) {
        //        return true;
        //    }
        //}
        //return false;
    }
}
