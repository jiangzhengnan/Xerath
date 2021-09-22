package com.ng.xerath;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ng.xerath.func.FuncMethodUtil;
import com.ng.xerath.func.ViewMethodUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author pumpkin
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFunc();
        initView();
        initData();
    }

    /**
     * 功能
     */
    private void initFunc() {
        //耗时统计
        findViewById(R.id.btn1_layout_fuc).setOnClickListener(v -> FuncMethodUtil.calculateTimeMethod());
        //限频调用
        findViewById(R.id.btn2_layout_fuc).setOnClickListener(v -> {
            FuncMethodUtil.doubleClick();
            //System.out.println("输出带参结果:" + FuncMethodUtil.doubleClickReturnBoolean());
        });
        //异常捕获
        findViewById(R.id.btn3_layout_fuc).setOnClickListener(v -> {
            FuncMethodUtil.tryCatchMethod();
            //System.out.println("输出带参结果:" + FuncMethodUtil.tryCatchMethodReturnBoolean());
        });

        //参数统计
        findViewById(R.id.btn4_layout_fuc).setOnClickListener(v -> {
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
            FuncMethodUtil.testParams(bool, byte_v, char_v, short_v, int_v, long_v,
                    float_v, double_v, string_v, int_arr, testJsonObj);
        });
    }

    private void initData() {
        //弹出toast
        findViewById(R.id.btn1_layout_view).setOnClickListener(v -> {
            ViewMethodUtil.popToast();
        });
    }

    private void initView() {
    }

    public void testMethod() {
    }

}