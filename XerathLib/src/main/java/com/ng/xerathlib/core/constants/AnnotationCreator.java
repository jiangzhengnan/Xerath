package com.ng.xerathlib.core.constants;

import com.ng.xerathlib.core.plug.CalculateTimePlug;
import com.ng.xerathlib.core.plug.LimitCallPlug;
import com.ng.xerathlib.core.plug.TryCatchPlug;
import com.ng.xerathlib.core.plug.base.IAnnotationPlug;
import com.ng.xerathlib.utils.LogUtil;

/**
 * 描述:
 * 定义注解常量
 *
 * @author Jzn
 * @date 2021/9/14
 */
public class AnnotationCreator {

    private final static String ANNOTATION_PATH = "Lcom/ng/xerathcore/annotation/";

    public final static String CALCULATE_TIME = ANNOTATION_PATH + "Xerath_CalculateTime;";

    public final static String TRY_CATCH = ANNOTATION_PATH + "Xerath_TryCatch;";

    public final static String DOUBLE_CLICK = ANNOTATION_PATH + "Xerath_LimitCall;";

    public static IAnnotationPlug createPlug(String annotationStr) {
        IAnnotationPlug resultPlug = null;
        LogUtil.print("遍历注解:" + annotationStr);
        switch (annotationStr) {
            case AnnotationCreator.CALCULATE_TIME:
                resultPlug = new CalculateTimePlug();
                break;
            case AnnotationCreator.TRY_CATCH:
                resultPlug = new TryCatchPlug();
                break;
            case AnnotationCreator.DOUBLE_CLICK:
                resultPlug = new LimitCallPlug();
                break;
            default:
                break;
        }
        if (resultPlug != null) {
            LogUtil.print("注解:" + annotationStr + " 需要hook");
        }
        return resultPlug;
    }


}
