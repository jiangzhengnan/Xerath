package com.ng.xerathlib.core;

import com.ng.xerathlib.core.plug.CalculateTimePlug;
import com.ng.xerathlib.core.plug.CollectParamsPlug;
import com.ng.xerathlib.core.plug.LimitCallPlug;
import com.ng.xerathlib.core.plug.PopToastPlug;
import com.ng.xerathlib.core.plug.TryCatchPlug;
import com.ng.xerathlib.core.plug.base.IAnnotationPlug;

/**
 * 描述:
 * plug构建器
 *
 * @author Jzn
 * @date 2021/9/14
 */
public class AnnotationPlugCreator {

    private final static String ANNOTATION_PATH = "Lcom/ng/xerathcore/annotation/";

    public final static String CALCULATE_TIME = ANNOTATION_PATH + "Xerath_CalculateTime;";

    public final static String TRY_CATCH = ANNOTATION_PATH + "Xerath_TryCatch;";

    public final static String DOUBLE_CLICK = ANNOTATION_PATH + "Xerath_LimitCall;";

    public final static String POP_TOAST = ANNOTATION_PATH + "Xerath_PopToast;";

    public final static String CALCULATE_PARAMS = ANNOTATION_PATH + "Xerath_CollectParams;";

    public static IAnnotationPlug createPlug(String annotationStr) {
        IAnnotationPlug resultPlug = null;
        //LogUtil.print("遍历注解:" + annotationStr);
        switch (annotationStr) {
            case AnnotationPlugCreator.CALCULATE_TIME:
                resultPlug = new CalculateTimePlug();
                break;
            case AnnotationPlugCreator.TRY_CATCH:
                resultPlug = new TryCatchPlug();
                break;
            case AnnotationPlugCreator.DOUBLE_CLICK:
                resultPlug = new LimitCallPlug();
                break;
            case AnnotationPlugCreator.POP_TOAST:
                resultPlug = new PopToastPlug();
                break;
            case AnnotationPlugCreator.CALCULATE_PARAMS:
                resultPlug = new CollectParamsPlug();
                break;
            default:
                break;
        }
        if (resultPlug != null) {
            //LogUtil.print("     注解:" + annotationStr + " 需要hook");
        }
        return resultPlug;
    }


}
