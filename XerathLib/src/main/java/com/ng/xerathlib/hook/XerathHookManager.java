package com.ng.xerathlib.hook;

import java.util.concurrent.ConcurrentHashMap;

import com.android.annotations.NonNull;
import com.android.annotations.Nullable;
import com.ng.xerathlib.utils.LogUtil;

/**
 * 避免多线程紊乱
 * 维护所有的XerathHookHelper
 */
public class XerathHookManager {
    private static XerathHookManager mInstance;

    private XerathHookManager() {
        mXerathHookHelperMap = new ConcurrentHashMap<>();
    }

    public static XerathHookManager getInstance() {
        if (mInstance == null) {
            synchronized (XerathHookManager.class) {
                if (mInstance == null) {
                    mInstance = new XerathHookManager();
                }
            }
        }
        return mInstance;
    }

    @NonNull
    private final ConcurrentHashMap<String, XerathHookHelper> mXerathHookHelperMap;

    @Nullable
    public XerathHookHelper getHelper(@Nullable String owner) {
        if (owner == null || owner.length() == 0) {
            return null;
        }
        if (mXerathHookHelperMap.get(owner) == null) {
            mXerathHookHelperMap.put(owner, new XerathHookHelper());
        }
        return mXerathHookHelperMap.get(owner);
    }

    public void removeHelper(@Nullable String owner) {
        if (owner == null || owner.length() == 0) {
            return;
        }
        mXerathHookHelperMap.remove(owner);
    }
}
