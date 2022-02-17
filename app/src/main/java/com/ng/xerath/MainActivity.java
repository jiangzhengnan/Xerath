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
import com.ng.xerath.func.member.TestMember1;
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
        initData();

        CoreHelper.onCoreHelperListener = this;
        mShowTv = findViewById(R.id.tv_show);
        mShowTv.setText("初始化日志:");
        mShowTv.setMovementMethod(ScrollingMovementMethod.getInstance());

        findViewById(R.id.tv_clean).setOnClickListener(v -> {
            mShowTv.setText("");
        });
    }

    /**
     * 功能
     */
    private void initFunc() {
        //限频调用
        findViewById(R.id.btn1_layout_fuc).setOnClickListener(v -> {
            pushNewLine();
            FuncMethodUtil.doubleClick();
        });
        //异常捕获
        findViewById(R.id.btn2_layout_fuc).setOnClickListener(v -> {
            pushNewLine();
            FuncMethodUtil.tryCatchMethod();
        });
        //方法移除
        findViewById(R.id.btn3_layout_fuc).setOnClickListener(v -> {
            pushNewLine();
            FuncMethodUtil.tryRemoveMethod(MainActivity.this);
            CoreHelper.catchLog("只有这一句，说明成功了");
        });
        //方法替换
        findViewById(R.id.btn4_layout_fuc).setOnClickListener(v -> {
            pushNewLine();
            FuncMethodUtil.tryExtendMethod();
        });
        //弹出toast
        findViewById(R.id.btn5_layout_fuc).setOnClickListener(v -> {
            pushNewLine();
            ViewMethodUtil.popToast();
        });
    }

    /**
     * 数据
     */
    private void initData() {
        //耗时统计
        findViewById(R.id.btn1_layout_data).setOnClickListener(v -> {
            pushNewLine();
            DataMethodUtil.calculateTimeMethod();
        });
        //参数统计
        findViewById(R.id.btn2_layout_data).setOnClickListener(v -> {
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
        findViewById(R.id.btn3_layout_data).setOnClickListener(v -> {
            pushNewLine();
            onCatchLog("全局变量抓取");
            TestMember1 testMember1 = new TestMember1();
            testMember1.test();
        });
        //获取调用链
        findViewById(R.id.btn4_layout_data).setOnClickListener(v -> {
            pushNewLine();
            DataMethodUtil.testCallChain();
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
        }
    }

    private void pushNewLine() {
        onCatchLog("\n");
    }

}