package com.ng.xerathlib.scene;


import com.ng.xerathlib.utils.LogUtil;

/**
 * 描述:
 *
 * @author Jzn
 * @date 2021/9/14
 */
public class AnalyseHelper {
    //场景
    private TransformExtension transformExtension;

    public void setTransformExtension(TransformExtension transformExtension) {
        this.transformExtension = transformExtension;
        LogUtil.print("场景判断参数:" + transformExtension.toString());
    }

    public TransformExtension getNoahTransformExtension() {
        return transformExtension;
    }


    public static AnalyseHelper getInstance() {
        if (mInstance == null) {
            synchronized (AnalyseHelper.class) {
                if (mInstance == null) {
                    mInstance = new AnalyseHelper();
                }
            }
        }
        return mInstance;
    }

    private AnalyseHelper() {
    }

    private static AnalyseHelper mInstance;


}
