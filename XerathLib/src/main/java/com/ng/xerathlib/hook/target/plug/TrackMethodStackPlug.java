package com.ng.xerathlib.hook.target.plug;

import com.ng.xerathlib.extension.ExtConstant;
import com.ng.xerathlib.hook.target.base.TargetPlug;
import com.ng.xerathlib.utils.LogUtil;
import org.apache.http.util.TextUtils;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;

public class TrackMethodStackPlug extends TargetPlug {

    @Override
    public void onHookMethodStart(final MethodVisitor mv) {
        LogUtil.print("目标插件 实施注入 onHookMethodStart" + mOwner + " " + mMethodName + " " + mMethodDesc);

        String log = "";
        if (ExtConstant.sTrackMethodStack != null && !TextUtils.isEmpty(ExtConstant.sTrackMethodStack.logTag)) {
            log = ExtConstant.sTrackMethodStack.logTag;
        }
        mv.visitLdcInsn(log + "-[方法调用]-" + mOwner + "-" + mMethodName + "-" + mMethodDesc);
        mv.visitMethodInsn(INVOKESTATIC, "com/ng/xerathcore/func/TrackMethodStackHelper", "callMethod", "(Ljava/lang/String;)V", false);
    }
}
