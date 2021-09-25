package com.ng.xerath;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ng.xerath.func.DataMethodUtil;
import com.ng.xerath.func.FuncMethodUtil;
import com.ng.xerath.func.ViewMethodUtil;
import com.ng.xerath.func.chain.ChainD;
import com.ng.xerathcore.CoreHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author pumpkin
 */
public class MainActivity extends AppCompatActivity implements CoreHelper.CoreHelperListener {
    private TextView mShowTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFunc();
        initView();
        initData();

        CoreHelper.onCoreHelperListener = this;
        mShowTv = findViewById(R.id.tv_show);
        mShowTv.setText("初始化日志---");
        mShowTv.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    /**
     * 功能
     */
    private void initFunc() {
        //限频调用
        findViewById(R.id.btn1_layout_fuc).setOnClickListener(v -> {
            FuncMethodUtil.doubleClick();
            //CoreHelper.catchLog("输出带参结果:" + FuncMethodUtil.doubleClickReturnBoolean());
        });
        //异常捕获
        findViewById(R.id.btn2_layout_fuc).setOnClickListener(v -> {
            FuncMethodUtil.tryCatchMethod();
            //CoreHelper.catchLog("输出带参结果:" + FuncMethodUtil.tryCatchMethodReturnBoolean());
        });
        //方法扩展
        findViewById(R.id.btn4_layout_fuc).setOnClickListener(v -> {

        });
        //获取调用链
        findViewById(R.id.btn5_layout_fuc).setOnClickListener(v -> {
            CoreHelper.catchLog("调用链路测试开始");
            ChainD chainD = new ChainD();
            chainD.getCallChain();
        });
    }

    /**
     * 数据
     */
    private void initData() {
        //耗时统计
        findViewById(R.id.btn1_layout_data).setOnClickListener(v -> DataMethodUtil.calculateTimeMethod());
        //参数统计
        findViewById(R.id.btn2_layout_data).setOnClickListener(v -> {
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
    }

    /**
     * 视图
     */
    private void initView() {
        //弹出toast
        findViewById(R.id.btn1_layout_view).setOnClickListener(v -> {
            ViewMethodUtil.popToast();
        });
    }

    @Override
    public void onCatchLog(@Nullable String s) {
        if (s != null) {
            mShowTv.append("\n");
            mShowTv.append(s, 0, s.length());
            int offset = mShowTv.getLineCount() * mShowTv.getLineHeight();
            if (offset > mShowTv.getHeight()) {
                mShowTv.scrollTo(0, offset - mShowTv.getHeight());
            }
            mShowTv.append("\n");
        }
    }

}