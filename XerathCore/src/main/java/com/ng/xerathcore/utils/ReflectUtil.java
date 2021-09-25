package com.ng.xerathcore.utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ng.xerathcore.CoreHelper;
import com.ng.xerathcore.bean.ObjectNode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : jiangzhengnan.jzn
 * @creation : 2021/09/25
 * @description :
 * 反射工具
 */
public class ReflectUtil {
    private static final String TAG = "ReflectUtil";

    /**
     * 递归遍历
     */
    public static void monitor(@NonNull Object object) {
        ObjectNode node = new ObjectNode();
        node.setFieldName(object.getClass().getName());
        node.setSelf(object);
        printJsonObjectClassPath(object, node);
    }

    @Nullable
    private static Field getFieldByName(@NonNull Class clazz, @NonNull String fieldName) {
        Field[] fields = getAllFields(clazz);
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }

        return null;
    }

    private static void printJsonObjectClassPath(@NonNull Object inputObject, @NonNull ObjectNode node) {
        Class clazz = inputObject.getClass();
        String className = clazz.getName();
        // 去掉java和android系统类
        if (className.startsWith("android.") || className.startsWith("com.android.")) {
            return;
        }
        if (!className.equals("java.lang.Object") && className.startsWith("java.")) {
            return;
        }
        // 去掉基本类型
        if (className.equals("int") ||
                className.equals("double") ||
                className.equals("long") ||
                className.equals("String") ||
                className.equals("float") ||
                className.equals("byte") ||
                className.equals("boolean")
        ) {
            return;
        }
        // 去掉枚举,枚举可能会导致死循环
        if (clazz.isEnum()) {
            return;
        }
        Field[] fields = getAllFields(inputObject.getClass());
        for (Field field : fields) {
            field.setAccessible(true);

            //首先过滤json
            if (field.getType().getName().equals(JSONObject.class.getName()) || field.getType().getName().equals(JSONArray.class.getName())) {
                try {
                    Object object = field.get(inputObject);
                    if (object != null) {
                        if (node.isFathersObject(object)) {
                            continue;
                        }
                        ObjectNode childNode = new ObjectNode();
                        childNode.setParent(node);
                        childNode.setSelf(object);
                        childNode.setFieldName(field.getName());
                        node.addChild(childNode);
                        String geneticInfo = childNode.getGeneticInfo();
                        CoreHelper.catchLog("JSONObject genetic info:" + geneticInfo);
                        Object jsonObject = field.get(inputObject);
                        if (jsonObject instanceof JSONObject) {
                            CoreHelper.catchLog("JSONObject content:" + ((JSONObject) jsonObject).toString());
                        } else if (jsonObject instanceof JSONArray) {
                            CoreHelper.catchLog("JSONArray content:" + ((JSONArray) jsonObject).toString());
                        }
                        CoreHelper.catchLog("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                continue;
            }

            String simpleFileName = field.getType().getSimpleName();
            if (simpleFileName.equals("int") || simpleFileName.equals("double") || simpleFileName.equals("Double") || simpleFileName.equals("long")
                    || simpleFileName.equals("String")
                    || simpleFileName.equals("float") || simpleFileName.equals("byte") || simpleFileName.equals("boolean")
            ) {
                try {
                    //输出此时的引用路径
                    //if (field.getName().equals("mOriginJString")){
                    //    Object object = field.get(inputObject);
                    //    ObjectNode childNode = new ObjectNode();
                    //    childNode.setParent(node);
                    //    childNode.setSelf(object);
                    //    childNode.setFieldName(field.getName());
                    //    String geneticInfo = childNode.getGeneticInfo();
                    //    CoreHelper.catchLog( "JSONObject genetic info:" + geneticInfo);
                    //    CoreHelper.catchLog("打印 " + clazz.getSimpleName() + " 内的属性:" + field.getName() + ":" + simpleFileName + " " + field.get(inputObject));
                    //}
                    CoreHelper.catchLog("打印 " + clazz.getSimpleName() + " 内的属性:" + field.getName() + ":" + simpleFileName + " " + field.get(inputObject));
                } catch (IllegalAccessException e) {
                    //CoreHelper.catchLog("报错:" + e.getMessage());
                    e.printStackTrace();
                }
            }

            String name = field.getType().getName();
            if (name.equals("int") || name.equals("double") || name.equals("long")
                    || name.equals("float") || name.equals("byte") || name.equals("boolean")
                    || name.startsWith("android.")
                    || name.startsWith("com.noah.")) {
                continue;
            }

            Class superClass = field.getType().getSuperclass();
            if (superClass != null && superClass.getName().equals("java.lang.Enum")) {
                continue;
            }

            field.setAccessible(true);
            try {
                Object object = field.get(inputObject);
                if (object != null) {
                    if (node.isFathersObject(object)) {
                        continue;
                    }

                    ObjectNode childNode = new ObjectNode();
                    childNode.setParent(node);
                    childNode.setSelf(object);
                    childNode.setFieldName(field.getName());
                    node.addChild(childNode);

                    printJsonObjectClassPath(object, childNode);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static Field[] getAllFields(@NonNull Class clazz) {
        Field[] allFields = clazz.getDeclaredFields();
        Class superClass = clazz.getSuperclass();
        List<Field> fieldList = new ArrayList<>();
        for (Field field : allFields) {
            fieldList.add(field);
        }

        while (superClass != null) {
            Field[] superFileds = superClass.getDeclaredFields();
            for (Field field : superFileds) {
                fieldList.add(field);
            }
            superClass = superClass.getSuperclass();
        }

        Field[] resultFields = new Field[fieldList.size()];
        for (int i = 0; i < fieldList.size(); i++) {
            resultFields[i] = fieldList.get(i);
        }

        return resultFields;
    }
}
