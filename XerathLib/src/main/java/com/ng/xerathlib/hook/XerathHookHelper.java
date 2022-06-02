package com.ng.xerathlib.hook;

import java.util.ArrayList;
import java.util.List;

import com.android.annotations.NonNull;
import com.ng.xerathlib.hook.annotation.AnnotationHookHelper;
import com.ng.xerathlib.hook.params.HookParams;
import com.ng.xerathlib.hook.target.TargetHookHelper;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

/**
 * @author Jzn
 * @date 2021/9/14
 * 描述: 外观入口
 * <p>
 * 参数管理器
 * 负责类和方法出入参收集
 * <p>
 * 执行管理器
 * 通过注解定义(annotation)
 * 通过包类名(normal)
 * <p>
 * 生命周期回调
 * 负责监听/回调 类和方法的各种执行生命周期
 * <p>
 * //测试点
 * 保证以前的功能正常
 */
public class XerathHookHelper implements HookLifeCycle {
    @NonNull
    private final List<HookLifeCycle> mPlugs = new ArrayList<>();

    @NonNull
    public HookParams mParams;

    public boolean mNeedHook;

    public XerathHookHelper() {
        mParams = new HookParams();
        mPlugs.add(new TargetHookHelper(mParams));
        mPlugs.add(new AnnotationHookHelper(mParams));
    }

    @NonNull
    public HookParams getParams() {
        return mParams;
    }

    @Override
    public boolean onVisitClass(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces) {
        //LogUtil.print("XerathHookHelper-onVisitClass-清空");
        for (HookLifeCycle plug : mPlugs) {
            if (plug.onVisitClass(version, access, name, signature, superName, interfaces)) {
                mNeedHook = true;
            }
        }
        return mNeedHook;
    }

    @Override
    public void visitClassField(final int access, final String name, final String descriptor, final String signature, final Object value) {
        for (HookLifeCycle plug : mPlugs) {
            plug.visitClassField(access, name, descriptor, signature, value);
        }
    }

    @Override
    public boolean isClassChanged() {
        boolean changed = false;
        for (HookLifeCycle plug : mPlugs) {
            if (plug.isClassChanged()) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public void onVisitMethod(final int access, final String name, final String descriptor, final MethodVisitor methodVisitor, final String owner) {
        for (HookLifeCycle plug : mPlugs) {
            plug.onVisitMethod(access, name, descriptor, methodVisitor, owner);
        }
    }

    @Override
    public boolean onVisitMethodInsn(final MethodVisitor mv, final int opcode, final String owner, final String name, final String desc, final boolean itf) {
        boolean result = false;
        for (HookLifeCycle plug : mPlugs) {
            if (plug.onVisitMethodInsn(mv, opcode, owner, name, desc, itf)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public void visitMethodCode() {
        for (HookLifeCycle plug : mPlugs) {
            plug.visitMethodCode();
        }
    }

    @Override
    public void visitLineNumber(final int line, final Label start) {
        for (HookLifeCycle plug : mPlugs) {
            plug.visitLineNumber(line, start);
        }
    }

    @Override
    public boolean visitMethodAnnotation(final String descriptor, final boolean visible) {
        boolean needHook = false;
        for (HookLifeCycle plug : mPlugs) {
            if (plug.visitMethodAnnotation(descriptor, visible)) {
                needHook = true;
            }
        }
        return needHook;
    }

    @Override
    public void onHookMethodReturn(final int opcode, final MethodVisitor mv) {
        for (HookLifeCycle plug : mPlugs) {
            plug.onHookMethodReturn(opcode, mv);
        }
    }

    @Override
    public void onHookMethodEnd(final MethodVisitor mv) {
        for (HookLifeCycle plug : mPlugs) {
            plug.onHookMethodEnd(mv);
        }
    }

    @Override
    public void visitEnd() {
        for (HookLifeCycle plug : mPlugs) {
            plug.visitEnd();
        }
        mParams.clearMethodData();
    }

}
