package com.ng.xerath.ui.fragment;

import android.view.View;

import com.ng.xerath.R;
import com.ng.xerath.func.FuncMethodUtil;
import com.ng.xerath.func.ViewMethodUtil;
import com.ng.xerathcore.CoreHelper;

import org.jetbrains.annotations.NotNull;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2022/03/09
 * @description :
 * 功能模块
 */
public class FunctionFragment extends BaseFragment{
    private CoreHelper.CoreHelperListener mLogListener;

    public void setLogListener(CoreHelper.CoreHelperListener mLogListener) {
        this.mLogListener = mLogListener;
    }

    @Override
    public void initViewsAndEvents(@NotNull View v) {
        initFunc(v);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_func;
    }


    /**
     * 功能
     */
    private void initFunc(View view) {
        //限频调用
        view.findViewById(R.id.btn1_layout_fuc).setOnClickListener(v -> {
            pushNewLine();
            FuncMethodUtil.doubleClick();
        });
        //异常捕获
        view.findViewById(R.id.btn2_layout_fuc).setOnClickListener(v -> {
            pushNewLine();
            FuncMethodUtil.tryCatchMethod();
        });
        //方法移除
        view.findViewById(R.id.btn3_layout_fuc).setOnClickListener(v -> {
            pushNewLine();
            //FuncMethodUtil.tryRemoveMethod(MainActivity.this);
            FuncMethodUtil.tryRemoveCompleteMethod(getActivity());
        });
        //方法替换
        view.findViewById(R.id.btn4_layout_fuc).setOnClickListener(v -> {
            pushNewLine();
            FuncMethodUtil.tryExtendMethod();
        });
        //弹出toast
        view.findViewById(R.id.btn5_layout_fuc).setOnClickListener(v -> {
            pushNewLine();
            ViewMethodUtil.popToast();
        });
    }

    private void pushNewLine() {
        mLogListener.onCatchLog("\n");
    }
}
