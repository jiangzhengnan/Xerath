package com.ng.xerathlib.hook.annotation;

import com.ng.xerathlib.extension.ExtConstant;
import com.ng.xerathlib.hook.annotation.plug.base.AnnotationPlugCreator;
import com.ng.xerathlib.hook.annotation.plug.base.IAnnotationPlug;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2022/05/15
 * @description :
 */
public class AnnotationHookHelper {

    public static IAnnotationPlug getPlug(String annotationStr) {
        //注解开关
        if (!ExtConstant.sTransformExt.enableAnnotation) {
            return null;
        }
        IAnnotationPlug plug = AnnotationPlugCreator.createPlug(annotationStr);
        if (plug != null) {
            //LogUtil.print("初始化plug:" + mMethodName + " " + mMethodDesc);
            return plug;
        }
        return null;
    }
}
