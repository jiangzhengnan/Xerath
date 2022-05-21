package com.ng.xerathlib.hook.target;

import com.ng.xerathlib.extension.ExtConstant;
import com.ng.xerathlib.hook.annotation.plug.base.AnnotationPlugCreator;
import com.ng.xerathlib.hook.annotation.plug.base.IAnnotationPlug;
import com.ng.xerathlib.hook.target.base.ITargetPlug;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2022/05/15
 * @description :
 */
public class TargetHookHelper {

    /**
     * 判断当前类/方法是否需要修改
     */
    public static ITargetPlug getPlug(String className, String methodName) {
        //todo ExtConstant.sTrackMethodStack.targetClassList

        return null;
    }
}
