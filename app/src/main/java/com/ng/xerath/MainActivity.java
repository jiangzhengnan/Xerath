package com.ng.xerath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ng.xerath.test.func.FuncMethodUtil;

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
        findViewById(R.id.btn1_layout_fuc).setOnClickListener(v -> FuncMethodUtil.CalculateTimeMethod());
    }

    private void initView() {
        System.out.println("what the fuck");
    }


    private void initData() {
    }


}