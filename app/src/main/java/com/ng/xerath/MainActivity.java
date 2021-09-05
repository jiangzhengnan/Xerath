package com.ng.xerath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ng.xerath.test.func.FuncMethodUtil;
import com.ng.xerathcore.CoreUtils;
import com.ng.xerathcore.XerathEngine;

/**
 * @author pumpkin
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XerathEngine.getInstance().start();

        initFunc();
        initView();
        initData();

    }

    private void initFunc() {
        //耗时统计
        findViewById(R.id.btn1_layout_fuc).setOnClickListener(v -> FuncMethodUtil.CalculateTimeMethod());
    }

    private void initView() {
        CoreUtils.catchLog("hhh");
    }


    private void initData() {
    }


}