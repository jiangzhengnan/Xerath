package com.ng.xerathlib.hook.target.plug;

import java.util.List;

import com.android.annotations.NonNull;
import com.android.annotations.Nullable;
import com.ng.xerathlib.extension.ExtConstant;
import com.ng.xerathlib.hook.target.base.ITargetPlug;
import com.ng.xerathlib.utils.LogUtil;
import com.ng.xerathlib.utils.TxtUtils;
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
        ITargetPlug plug = TargetPlugCreator.createPlug(className);
        if (plug != null) {
            return plug;
        }
        return null;
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
        LogUtil.print("TargetPlugCreator createPlug, owner:" + owner);
        ITargetPlug resultPlug = null;

        String fullClassName = TxtUtils.getFullClassNameForOwner(owner);
        List<String> targetPkgList = ExtConstant.sTrackMethodStack.targetPackageList;
        List<String> targetClassList = ExtConstant.sTrackMethodStack.targetClassList;

        //暂时只适配 track_method_stack
        boolean needTrackMethodStack = false;
        if (targetPkgList != null) {
            LogUtil.print("targetPkgList: " + targetPkgList);
            for (String pkgName : targetPkgList) {
                if (!TextUtils.isEmpty(pkgName) && fullClassName.startsWith(pkgName)) {
                    needTrackMethodStack = true;
                    break;
                }
            }
        }
        if (!needTrackMethodStack && targetClassList != null) {
            LogUtil.print("targetClassList: " + targetClassList);
            for (String className : targetClassList) {
                //包含内部类
                if (!TextUtils.isEmpty(className) && fullClassName.startsWith(className)) {
                    needTrackMethodStack = true;
                    break;
                }
            }
        }
        if (needTrackMethodStack) {
            resultPlug = new TrackMethodStackPlug();
        }
        return resultPlug;
    }


}
