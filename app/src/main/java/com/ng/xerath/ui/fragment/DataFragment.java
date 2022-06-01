package com.ng.xerath.ui.fragment;

import java.io.File;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ng.xerath.R;
import com.ng.xerath.func.DataMethodUtil;
import com.ng.xerath.func.member.TestMember1;
import com.ng.xerathcore.CoreHelper;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2022/03/09
 * @description :
 * 功能模块
 */
public class DataFragment extends BaseFragment {

    private CoreHelper.CoreHelperListener mLogListener;

    public void setLogListener(CoreHelper.CoreHelperListener mLogListener) {
        this.mLogListener = mLogListener;
    }

    @Override
    public void initViewsAndEvents(@NotNull View v) {
        initData(v);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_data;
    }

    /**
     * 数据
     */
    private void initData(View view) {
        //耗时统计
        view.findViewById(R.id.btn1_layout_data).setOnClickListener(v -> {
            pushNewLine();
            DataMethodUtil.calculateTimeMethod();
        });
        //参数统计
        view.findViewById(R.id.btn2_layout_data).setOnClickListener(v -> {
            pushNewLine();
            boolean bool = true;
            byte byte_v = 1;
            char char_v = 2;
            short short_v = 3;
            int int_v = 4;
            long long_v = 5;
            float float_v = 6;
            double double_v = 7;
            String string_v = "string_value";
            int[] int_arr = new int[]{1, 2, 3};
            JSONObject testJsonObj = new JSONObject();
            try {
                testJsonObj.put("name", "jzn");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            DataMethodUtil.testParams(bool, byte_v, char_v, short_v, int_v, long_v,
                    float_v, double_v, string_v, int_arr, testJsonObj);
        });
        //全局 [成员,类，局部]变量 抓取
        view.findViewById(R.id.btn3_layout_data).setOnClickListener(v -> {
            pushNewLine();
            if (mLogListener != null) {
                mLogListener.onCatchLog("全局变量抓取");
            }
            TestMember1 testMember1 = new TestMember1();
            testMember1.test();
        });
        //获取对象属性依赖关系
        view.findViewById(R.id.btn4_layout_data).setOnClickListener(v -> {
            pushNewLine();
            DataMethodUtil.testHookParams();
        });
        //获取方法调用链路，耗时分析等
        view.findViewById(R.id.btn5_layout_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                pushNewLine();
                //app
                //DataMethodUtil.testCallChain();

                //api
                Glide.with(getActivity())
                     .downloadOnly()
                     .load("https://github.com/jiangzhengnan/Xerath/raw/master/app/src/main/res/raw/ic_bg.png")
                     .listener(new RequestListener<File>() {
                         @Override
                         public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<File> target, boolean isFirstResource) {
                             Toast.makeText(getContext(), "下载失败", Toast.LENGTH_SHORT).show();
                             return false;
                         }

                         @Override
                         public boolean onResourceReady(File resource, Object model, Target<File> target, DataSource dataSource, boolean isFirstResource) {
                             Toast.makeText(getContext(), "下载成功", Toast.LENGTH_SHORT).show();
                             return false;
                         }
                     })
                     .preload();
            }
        });

    }

    private void pushNewLine() {
        if (mLogListener != null) {
            mLogListener.onCatchLog("\n");
        }
    }
}
