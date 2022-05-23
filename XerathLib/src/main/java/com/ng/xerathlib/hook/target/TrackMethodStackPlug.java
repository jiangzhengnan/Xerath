package com.ng.xerathlib.hook.target;

import com.ng.xerathlib.hook.target.base.TargetPlug;
import org.objectweb.asm.MethodVisitor;

public class TrackMethodStackPlug extends TargetPlug {

    @Override
    public void onHookMethodStart(final MethodVisitor mv) {
        super.onHookMethodStart(mv);
    }
}
