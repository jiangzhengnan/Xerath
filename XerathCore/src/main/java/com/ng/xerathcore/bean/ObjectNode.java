package com.ng.xerathcore.bean;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/*
 * Copyright (C) 2004 - 2021 UCWeb Inc. All Rights Reserved.
 * Description : 描述当前文件的功能和使用范围
 * Attention: 如果是公共类，仔细说明使用方法和注意事项：特别是一些设计上的职责边界
 *
 * Created by ksl on 2021/3/30
 */
public class ObjectNode {
    private Object mSelf;
    private ObjectNode mParent;
    private String mFieldName;
    private List<ObjectNode> mChilds = new ArrayList<>();

    public void setSelf(@NonNull Object self) {
        mSelf = self;
    }

    public Object getSelf() {
        return mSelf;
    }

    public void setParent(@NonNull ObjectNode parent) {
        mParent = parent;
    }

    public Object getParent() {
        return mParent;
    }

    public void addChild(@NonNull ObjectNode child) {
        mChilds.add(child);
    }

    public List<ObjectNode> getChilds() {
        return mChilds;
    }

    public boolean isFathersObject(@NonNull Object object) {
        if (mSelf == object) {
            return true;
        } else if (mParent == null){
            return false;
        } else {
            return mParent.isFathersObject(object);
        }
    }

    public void setFieldName(@NonNull String fieldName) {
        mFieldName = fieldName;
    }

    public String getFieldName() {
        return mFieldName;
    }

    public String getGeneticInfo() {
        if (mParent == null) {
            return mFieldName;
        } else {
            return mParent.getGeneticInfo() + "->" + mFieldName;
        }
    }
}
