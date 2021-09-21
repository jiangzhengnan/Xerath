package com.ng.xerathcore;

import android.content.Context;
import android.widget.Toast;

import com.ng.xerathcore.utils.SpUtil;

/**
 * 描述:
 * 数据收集 框架
 *
 * @author Jzn
 * @date 2021/9/5
 */
public class XerathEngine {
    private Context mContext;

    private static XerathEngine mInstance;

    private XerathEngine() {
    }

    public static XerathEngine getInstance() {
        if (mInstance == null) {
            synchronized (XerathEngine.class) {
                if (mInstance == null) {
                    mInstance = new XerathEngine();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        mContext = context;
        SpUtil.init(mContext);
    }

    public Context getContext() {
        return mContext;
    }

    public void showToast(String str) {
        if (mContext != null) {
            Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
        }
    }
}
