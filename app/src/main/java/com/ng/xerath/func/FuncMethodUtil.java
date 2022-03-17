package com.ng.xerath.func;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ng.xerath.func.chain.ChainA;
import com.ng.xerath.func.chain.ChainB;
import com.ng.xerath.func.chain.ChainC;
import com.ng.xerath.func.chain.ChainD;
import com.ng.xerathcore.CoreHelper;
import com.ng.xerathcore.annotation.Xerath_LimitCall;
import com.ng.xerathcore.annotation.Xerath_MethodCompleteRemove;
import com.ng.xerathcore.annotation.Xerath_MethodRemove;
import com.ng.xerathcore.annotation.Xerath_MethodReplace;
import com.ng.xerathcore.annotation.Xerath_TryCatch;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 描述:
 *
 * @author Jzn
 * @date 2021/9/4
 */
public class FuncMethodUtil {

    /**
     * try-catch void
     */
    @Xerath_TryCatch
    public static void tryCatchMethod() {
        int a = 1 / 0;
    }

    /**
     * try-catch boolean
     */
    @Xerath_TryCatch
    public static boolean tryCatchMethodReturnBoolean() {
        int a = 1 / 0;
        return false;
    }

    /**
     * 限制重复调用 void
     */
    @Xerath_LimitCall(time = 8000L)
    public static void doubleClick() {
        CoreHelper.catchLog("click");
    }

    /**
     * 限制重复调用
     */
    @Xerath_LimitCall(time = 1000L)
    public static boolean doubleClickReturnBoolean() {
        CoreHelper.catchLog("click");
        return false;
    }

    /**
     * 方法替换
     */
    @Xerath_MethodReplace(
            replaceMethods = {"org/json/JSONObject|put|(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;"},
            targetMethods = {"com/ng/xerathcore/utils/JsonPrinter|print|(Ljava/lang/String;Ljava/lang/Object;)V"}
    )
    public static void tryExtendMethod() {
        JSONObject testJson = new JSONObject();
        try {
            testJson.put("name", "jzn");
            testJson.put("age", "26");
            testJson.put("height", "178");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String result = testJson.toString();
        CoreHelper.catchLog(result);
    }

    /**
     * 方法移除
     */
    @Xerath_MethodRemove(
            removeMethods = {
                    "android/util/Log|d|(Ljava/lang/String;Ljava/lang/String;)I",
                    "android/widget/Toast|makeText|(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;",
                    "android/widget/Toast|()V"
            }
    )
    public static void tryRemoveMethod(Context context) {
        Log.d("tag","A");
    }


    public static String TAG = "test";

    private String abc = "abc";
    /**
     * 方法完全移除
     */
    @Xerath_MethodCompleteRemove(
            removeMethods = {
                    "android/util/Log|d|(Ljava/lang/String;Ljava/lang/String;)I",
                    "android/widget/Toast|makeText|(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;",
                    "android/widget/Toast|()V"
            }
    )
    public static void tryRemoveCompleteMethod(Context context) {
        String tag = "tag";
        String value = "pumpkin";
        Log.d(tag,value);
    }

    public static String getName() {
        return "pumpkin";
    }
}
