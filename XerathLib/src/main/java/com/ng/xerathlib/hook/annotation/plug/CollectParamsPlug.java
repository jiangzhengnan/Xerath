package com.ng.xerathlib.hook.annotation.plug;

import java.util.List;

import com.ng.xerathlib.hook.XerathHookHelper;
import com.ng.xerathlib.hook.annotation.plug.base.AnnotationPlug;
import com.ng.xerathlib.utils.LogUtil;
import com.ng.xerathlib.utils.OpcodesUtils;
import com.ng.xerathlib.utils.Parameter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author : jiangzhengnan.jzn
 * @creation : 2021/09/20
 * @description :
 * 收集入参和出参
 */
public class CollectParamsPlug extends AnnotationPlug {
    //当前方法对应的参数列表
    private List<Parameter> mParameters;

    //进行入参统计
    @Override
    public void onHookMethodStart(MethodVisitor mv) {
        //获取参数列表
        loadParams();
        int printUtilsVarIndex = mParams.mAdapter.newLocal(Type.getObjectType("com/ng/xerathcore/utils/ParameterPrinter"));
        mv.visitTypeInsn(Opcodes.NEW, "com/ng/xerathcore/utils/ParameterPrinter");
        mv.visitInsn(Opcodes.DUP);
        mv.visitLdcInsn(mParams.mOwner);
        mv.visitLdcInsn(mParams.mMethodName);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/ng/xerathcore/utils/ParameterPrinter", "<init>", "(Ljava/lang/String;Ljava/lang/String;)V", false);
        mv.visitVarInsn(Opcodes.ASTORE, printUtilsVarIndex);
        if (mParameters != null) {
            for (int i = 0; i < mParameters.size(); i++) {
                Parameter parameter = mParameters.get(i);
                String name = parameter.name;
                String desc = parameter.desc;
                int index = parameter.index;
                int opcode = OpcodesUtils.getLoadOpcodeFromDesc(desc);
                String fullyDesc = String.format("(Ljava/lang/String;%s)Lcom/ng/xerathcore/utils/ParameterPrinter;", desc);
                visitPrint(mv, printUtilsVarIndex, index, opcode, name, fullyDesc);
            }
        }
        mv.visitVarInsn(Opcodes.ALOAD, printUtilsVarIndex);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/ng/xerathcore/utils/ParameterPrinter", "print", "()V", false);

    }

    //访问打印参数方法
    private void visitPrint(MethodVisitor mv, int varIndex, int localIndex, int opcode, String name, String desc) {
        mv.visitVarInsn(Opcodes.ALOAD, varIndex);
        mv.visitLdcInsn(name);
        mv.visitVarInsn(opcode, localIndex);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                           "com/ng/xerathcore/utils/ParameterPrinter",
                           "append",
                           desc, false);
        mv.visitInsn(Opcodes.POP);
    }

    //加载参数列表
    private void loadParams() {
        String key = mParams.mMethodName + mParams.mMethodDesc;
        LogUtil.print("CollectParamsPlug key:" + key);
        mParameters = mParams.getMethodParams(key);
        if (mParameters != null) {
            LogUtil.print("CollectParamsPlug 参数列表:" + mParameters.toString());
        } else {
            LogUtil.print("CollectParamsPlug 参数列表 null");
        }
    }

    //进行出参统计
    @Override
    public void onHookMethodReturn(int opcode, MethodVisitor mv) {
        Type returnType = Type.getReturnType(mParams.mMethodDesc);
        String returnDesc = mParams.mMethodDesc.substring(mParams.mMethodDesc.indexOf(")") + 1);
        if (returnDesc.startsWith("[") || returnDesc.startsWith("L")) {
            returnDesc = "Ljava/lang/Object;";
        }
        //store origin return value
        int resultTempValIndex = -1;
        if (returnType != Type.VOID_TYPE || opcode == Opcodes.ATHROW) {
            if (opcode == Opcodes.ATHROW) {
                returnType = Type.getType("Ljava/lang/Object;");
            }
            resultTempValIndex = mParams.mAdapter.newLocal(returnType);
            int storeOpcocde = OpcodesUtils.getStoreOpcodeFromType(returnType);
            if (opcode == Opcodes.ATHROW) {
                storeOpcocde = Opcodes.ASTORE;
            }
            mv.visitVarInsn(storeOpcocde, resultTempValIndex);
        }

        mv.visitLdcInsn(mParams.mMethodName);
        //parameter 4
        if (returnType != Type.VOID_TYPE || opcode == Opcodes.ATHROW) {
            int loadOpcode = OpcodesUtils.getLoadOpcodeFromType(returnType);
            if (opcode == Opcodes.ATHROW) {
                loadOpcode = Opcodes.ALOAD;
                returnDesc = "Ljava/lang/Object;";
            }
            mv.visitVarInsn(loadOpcode, resultTempValIndex);
            String formatDesc = String.format("(Ljava/lang/String;%s)V", returnDesc);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/ng/xerathcore/utils/ResultPrinter", "print", formatDesc, false);
            mv.visitVarInsn(loadOpcode, resultTempValIndex);
        } else {
            mv.visitLdcInsn("void");
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/ng/xerathcore/utils/ResultPrinter", "print", "(Ljava/lang/String;Ljava/lang/String;JLjava/lang/Object;)V", false);
        }
    }

    @Override
    public void onHookMethodEnd(MethodVisitor mv) {

    }
}
