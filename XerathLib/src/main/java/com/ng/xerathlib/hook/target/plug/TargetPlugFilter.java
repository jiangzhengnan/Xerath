package com.ng.xerathlib.hook.target.plug;

import java.util.List;

import com.android.annotations.NonNull;
import com.ng.xerathlib.extension.ExtConstant;
import com.ng.xerathlib.utils.TxtUtils;
import org.apache.http.util.TextUtils;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2022/05/31
 * @description :
 */
public class TargetPlugFilter {

    public static boolean isNeedHack(@NonNull String owner) {
        return isNeedTrackMethodStack(owner);
    }

    public static boolean isNeedTrackMethodStack(@NonNull String owner) {
        String fullClassName = TxtUtils.getFullClassNameForOwner(owner);
        List<String> targetPkgList = ExtConstant.sTrackMethodStack.targetPackageList;
        List<String> targetClassList = ExtConstant.sTrackMethodStack.targetClassList;

        boolean needTrackMethodStack = false;
        if (targetPkgList != null) {
            //LogUtil.print("targetPkgList: " + targetPkgList);
            for (String pkgName : targetPkgList) {
                if (!TextUtils.isEmpty(pkgName) && fullClassName.startsWith(pkgName)) {
                    needTrackMethodStack = true;
                    break;
                }
            }
        }
        if (!needTrackMethodStack && targetClassList != null) {
            //LogUtil.print("targetClassList: " + targetClassList);
            for (String className : targetClassList) {
                //包含内部类
                if (!TextUtils.isEmpty(className) && fullClassName.startsWith(className)) {
                    needTrackMethodStack = true;
                    break;
                }
            }
        }
        return needTrackMethodStack;
    }

}
