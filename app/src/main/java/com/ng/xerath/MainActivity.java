package com.ng.xerath;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ng.xerath.func.FuncMethodUtil;

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

    private void initFunc() {
        //耗时统计
        findViewById(R.id.btn1_layout_fuc).setOnClickListener(v -> FuncMethodUtil.calculateTimeMethod());
        //限频调用
        findViewById(R.id.btn2_layout_data).setOnClickListener(v -> {
            FuncMethodUtil.doubleClick();
            //System.out.println("输出带参结果:" + FuncMethodUtil.doubleClickReturnBoolean());
        });
        //异常捕获
        findViewById(R.id.btn3_layout_view).setOnClickListener(v -> {
            FuncMethodUtil.tryCatchMethod();
            //System.out.println("输出带参结果:" + FuncMethodUtil.tryCatchMethodReturnBoolean());
        });
    }

    private void initData() {
    }

    private void initView() {
    }

    public void testMethod() {
    }

}