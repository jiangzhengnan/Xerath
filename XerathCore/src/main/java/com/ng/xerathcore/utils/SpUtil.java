package com.ng.xerathcore.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.content.SharedPreferences.Editor;
import java.util.Iterator;
import java.util.Map;

/**
 * @author : jiangzhengnan.jzn
 * @creation : 2021/09/19
 * @description :
 */
public class SpUtil {

    /**
     * SharedPreferencese文件名
     */
    public static final String DEFAULT_FILE_NAME = "xerath_share_data";

    private static Context sContext;

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    /**
     * 取值时直接使用getStringValue(String key, String defaultValue)即可</br>
     * @param key
     * @param value
     */
    public static void putStringValue(String key, String value){
        putStringValue(DEFAULT_FILE_NAME, key, value);
    }

    public static String getStringValue(String key, String defaultValue){
        return getStringValue(DEFAULT_FILE_NAME, key, defaultValue);
    }

    /**
     * 保存信息到SharedPreferences
     *
     * @param fileName (不用加后缀)
     * @param key
     * @param value
     * @note fileName和key参数最好预先进行md5转换
     */
    public static void putStringValue(String fileName, String key, String value) {
        try {
            SharedPreferences pref = getSharedPreferences(fileName);
            if (pref != null) {
                Editor editor = pref.edit();
                if (editor != null) {
                    editor.putString(key, value);
                    applySharedPreference(editor);
                }
            }
        } catch (Throwable e) {

            logD(e);
        }

    }

    /**
     * 获取SharedPreferences的配置信息(String类型)
     * @param fileName(不用加后缀)
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getStringValue(String fileName, String key, String defaultValue) {
        String value = defaultValue;
        try {
            SharedPreferences pref = getSharedPreferences(fileName);
            if (pref != null) {
                value = pref.getString(key, defaultValue);
            }
        } catch (Throwable e) {
            logD(e);
        }
        return value;
    }


    public static void putBooleanValue(String key, boolean value) {
        putBooleanValue(DEFAULT_FILE_NAME , key, value);
    }

    public static boolean getBooleanValue(String key, boolean value) {
        return getBooleanValue(DEFAULT_FILE_NAME, key, value);
    }

    /**
     * 保存信息到SharedPreferences
     * @param fileName (不用加后缀)
     * @param key
     * @param value
     * @note fileName和key参数最好预先进行md5转换
     */
    public static void putBooleanValue(String fileName, String key, boolean value) {
        try {
            SharedPreferences pref = getSharedPreferences(fileName);
            if (pref != null) {
                Editor editor = pref.edit();
                if (editor != null) {
                    editor.putBoolean(key, value);
                    applySharedPreference(editor);
                }
            }
        } catch (Throwable e) {
            logD(e);
        }
    }

    public static void putBooleanValueSync(String fileName, String key, boolean value) {
        try {
            SharedPreferences pref = getSharedPreferences(fileName);
            if (pref != null) {
                Editor editor = pref.edit();
                if (editor != null) {
                    editor.putBoolean(key, value);
                    editor.commit();
                }
            }
        } catch (Throwable e) {
            logD(e);
        }
    }

    /**
     * 获取SharedPreferences的配置信息(boolean类型)
     * @param fileName(不用加后缀)
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBooleanValue(String fileName, String key, boolean defaultValue) {
        boolean value = defaultValue;
        try {
            SharedPreferences pref = getSharedPreferences(fileName);
            if (pref != null) {
                value = pref.getBoolean(key, defaultValue);
            }
        } catch (Throwable e) {
            logD(e);
        }
        return value;
    }

    public static void putLongValue(String key, long value) {
        putLongValue(DEFAULT_FILE_NAME, key, value);
    }

    public static long getLongValue(String key, long defaultValue) {
        return getLongValue(DEFAULT_FILE_NAME, key, defaultValue);
    }

    /**
     * 保存信息到SharedPreferences(Long类型)
     * @param fileName (不用加后缀)
     * @param key
     * @param value
     * @note fileName和key参数最好预先进行md5转换
     */
    public static void putLongValue(String fileName, String key, long value) {
        putLongValue(fileName, key, value, false);
    }

    public static void putLongValue(String fileName, String key, long value, boolean needSync) {
        try {
            SharedPreferences pref = getSharedPreferences(fileName);
            if (pref != null) {
                Editor editor = pref.edit();
                if (editor != null) {
                    editor.putLong(key, value);
                    if (needSync) {
                        editor.commit();
                    } else {
                        applySharedPreference(editor);
                    }
                }
            }
        } catch (Throwable e) {
            logD(e);
        }
    }

    public static void putLongValueSync(String fileName, String key, long value) {
        putLongValue(fileName, key, value, true);
    }

    /**
     * 获取SharedPreferences的配置信息
     * @param fileName(不用加后缀)
     * @param key
     * @param defaultValue
     * @return
     */
    public static long getLongValue(String fileName, String key, long defaultValue) {
        long value = defaultValue;
        try {
            SharedPreferences pref = getSharedPreferences(fileName);
            if (pref != null) {
                value = pref.getLong(key, defaultValue);
            }
        } catch (Throwable e) {
            logD(e);
        }

        return value;
    }

    public static void putIntValue(String key, int value) {
        putIntValue(DEFAULT_FILE_NAME, key, value);
    }

    public static int getIntValue(String key, int defaultValue) {
        return getIntValue(DEFAULT_FILE_NAME, key, defaultValue);
    }

    /**
     * 保存信息到SharedPreferences(Int类型)
     * @param fileName (不用加后缀)
     * @param key
     * @param value
     * @note fileName和key参数最好预先进行md5转换
     */
    public static void putIntValue(String fileName, String key, int value) {
        try {
            SharedPreferences pref = getSharedPreferences(fileName);
            if (pref != null) {
                Editor editor = pref.edit();
                if (editor != null) {
                    editor.putInt(key, value);
                    applySharedPreference(editor);
                }
            }
        } catch (Throwable e) {
            logD(e);
        }
    }

    public static void putIntValueSync(String fileName, String key, int value) {
        try {
            SharedPreferences pref = getSharedPreferences(fileName);
            if (pref != null) {
                Editor editor = pref.edit();
                if (editor != null) {
                    editor.putInt(key, value);
                    editor.commit();
                }
            }
        } catch (Throwable e) {
            logD(e);
        }
    }

    /**
     * 获取SharedPreferences的配置信息(Int类型)
     * @param fileName(不用加后缀)
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getIntValue(String fileName, String key, int defaultValue) {
        int value = defaultValue;
        try {
            SharedPreferences pref = getSharedPreferences(fileName);
            if (pref != null) {
                value = pref.getInt(key, defaultValue);
            }
        } catch (Throwable e) {
            logD(e);
        }
        return value;
    }

    public static void putFloatValue(String key, float value) {
        putFloatValue(DEFAULT_FILE_NAME, key, value);
    }

    public static float getFloatValue(String key, float defaultValue) {
        return getFloatValue(DEFAULT_FILE_NAME, key, defaultValue);
    }

    /**
     * 保存信息到SharedPreferences(Float类型)
     * @param fileName (不用加后缀)
     * @param key
     * @param value
     * @note fileName和key参数最好预先进行md5转换
     */
    public static void putFloatValue(String fileName, String key, float value) {
        try {
            SharedPreferences pref = getSharedPreferences(fileName);
            if (pref != null) {
                Editor editor = pref.edit();
                if (editor != null) {
                    editor.putFloat(key, value);
                    applySharedPreference(editor);
                }
            }
        } catch (Throwable e) {
            logD(e);
        }
    }

    public static void putFloatValueSync(String fileName, String key, float value) {
        try {
            SharedPreferences pref = getSharedPreferences(fileName);
            if (pref != null) {
                Editor editor = pref.edit();
                if (editor != null) {
                    editor.putFloat(key, value);
                    editor.commit();
                }
            }
        } catch (Throwable e) {
            logD(e);
        }
    }

    /**
     * 获取SharedPreferences的配置信息(Float类型)
     * @param fileName(不用加后缀)
     * @param key
     * @param defaultValue
     * @return
     */
    public static float getFloatValue(String fileName, String key, float defaultValue) {
        float value = defaultValue;
        try {
            SharedPreferences pref = getSharedPreferences(fileName);
            if (pref != null) {
                value = pref.getFloat(key, defaultValue);
            }
        } catch (Throwable e) {
            logD(e);
        }
        return value;
    }

    public static void removeValue(String key) {
        SharedPreferences pref = getSharedPreferences(DEFAULT_FILE_NAME);
        try {
            if (pref != null) {
                Editor editor = pref.edit();
                if (editor != null) {
                    editor.remove(key);
                    editor.commit();
                }
            }
        } catch (Throwable e) {
            logD(e);
        }
    }

    public static void clear(String fileName) {
        SharedPreferences pref = getSharedPreferences(fileName);
        try {
            if (pref != null) {
                Editor editor = pref.edit();
                if (editor != null) {
                    editor.clear();
                    editor.commit();
                }
            }
        } catch (Throwable e) {
            logD(e);
        }
    }

    public static boolean isExistKey(String fileName, String key) {
        boolean ret = false;
        try {
            SharedPreferences pref = getSharedPreferences(fileName);
            if (pref != null) {
                ret = pref.contains(key);
            }
        } catch (Throwable e) {
            logD(e);
        }
        return ret;
    }

    public static void save(String fileName) {
        SharedPreferences pref = getSharedPreferences(fileName);
        try {
            if (pref != null) {
                Editor editor = pref.edit();
                if (editor != null) {
                    editor.commit();
                }
            }
        } catch (Throwable e) {
            logD(e);
        }
    }

    public static void putStringValues(String fileName, Map<String, String> map) {
        if (map == null || map.size() <= 0) {
            return;
        }

        SharedPreferences pref = getSharedPreferences(fileName);
        try {
            if (pref != null) {
                Editor editor = pref.edit();
                if (editor == null) {
                    return;
                }

                Iterator iter = map.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = (String) entry.getKey();
                    String value = (String) entry.getValue();
                    editor.putString(key, value);
                }
                applySharedPreference(editor);
            }
        } catch (Throwable e) {
            logD(e);
        }

    }

    public static int getSize(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return 0;
        }

        int size = 0;
        SharedPreferences pref = getSharedPreferences(fileName);
        try {
            if (pref != null) {
                Map map = pref.getAll();
                if(map != null) {
                    size = map.size();
                }
            }
        } catch (Throwable e) {
            logD(e);
        }
        return size;
    }

    public static void applySharedPreference(Editor editor) {
        if (editor == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 9) {
            try {
                editor.apply();
            } catch (Throwable e) {
                logD(e);
                editor.commit();
            }
        } else {
            editor.commit();
        }
    }


    public static SharedPreferences getSharedPreferences(String fileName) {
        Context context = sContext;
        if (context != null) {
            return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        }
        Log.println(Log.ERROR, "AdqSDK", "getSharedPreferences context is null.");
        return null;
    }

    private static void logD(Throwable throwable) {
        if (throwable != null) {
            throwable.printStackTrace();
        }
    }
}
