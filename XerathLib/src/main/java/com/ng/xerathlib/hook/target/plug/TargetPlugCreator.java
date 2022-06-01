package com.ng.xerathlib.hook.target.plug;

import com.android.annotations.NonNull;
import com.android.annotations.Nullable;
import com.ng.xerathlib.hook.target.base.ITargetPlug;
import com.ng.xerathlib.utils.LogUtil;
import org.apache.http.util.TextUtils;

/**
 * 描述:
 * plug构建规则
 */
public final class TargetPlugCreator {

    @Nullable
    public static ITargetPlug getPlug(String className) {
        if (TextUtils.isEmpty(className)) {
            return null;
        }
        return TargetPlugCreator.createPlug(className);
    }

    /**
     * 判断当前类/(方法待做)是否需要修改
     * [ Xerath ] --- 开始 hook className: com.ng.xerath.func.DataMethodUtil
     * [ Xerath ] XerathHookHelper-onVisitClass-清空
     * <p>
     * [ Xerath ] className: com/ng/xerath/func/DataMethodUtil
     * <p>
     * [ Xerath ] targetClassList: [DataMethodUtil]
     * [ Xerath ] targetPackageList: [com.ng.xerath.asm.func]
     */
    @Nullable
    public static ITargetPlug createPlug(@NonNull String owner) {
        //LogUtil.print("TargetPlugCreator createPlug, owner:" + owner);
        ITargetPlug resultPlug = null;
        if (TargetPlugFilter.isNeedTrackMethodStack(owner)) {
            resultPlug = new TrackMethodStackPlug();
            //LogUtil.print("TargetPlugCreator 符合条件:" + owner);
        }
        return resultPlug;
    }


}
