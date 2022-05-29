package com.ng.xerathlib.utils;

import com.android.annotations.NonNull;
import com.android.annotations.Nullable;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2022/05/25
 * @description :
 */
public class TxtUtils {

    @NonNull
    public static String getFullClassNameForOwner(@Nullable String owner) {
        if (owner == null || owner.length() == 0) {
            return "";
        }
        if (!owner.contains("/")) {
            return owner;
        }
        String[] tempArray = owner.split("/");
        return owner.replaceAll("/", ".");
    }

    /**
     * 传入格式:
     * owner:
     * com/ng/xerath/func/DataMethodUtil
     * com/ng/xerath/func/DataMethodUtil$TestClass
     * 返回格式:
     * com.ng.xerath.func
     */
    @NonNull
    public static String getPkgNameFromOwner(@Nullable String owner) {
        if (owner == null || owner.length() == 0 || !owner.contains("/")) {
            return "";
        }
        String[] tempArray = owner.split("/");
        int classNameStrLength = tempArray[tempArray.length - 1].length();
        return owner.replaceAll("/", ".")
                    .substring(0, owner.length() - classNameStrLength - 1);
    }

    /**
     * 传入格式:
     * owner: com/ng/xerath/func/DataMethodUtil
     * 返回格式:
     * DataMethodUtil
     */
    @NonNull
    public static String getClassNameFromOwner(@Nullable String owner) {
        if (owner == null || owner.length() == 0 || !owner.contains("/")) {
            return "";
        }
        String[] tempArray = owner.split("/");
        int classNameStrLength = tempArray[tempArray.length - 1].length();
        return owner.replaceAll("/", ".")
                    .substring(owner.length() - classNameStrLength, owner.length());
    }
}
