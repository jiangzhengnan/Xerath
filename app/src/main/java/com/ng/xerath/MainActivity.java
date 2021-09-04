package com.ng.xerath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ng.xerath.utils.LogUtil;
import com.ng.xerathcore.CalculateTime;
import com.ng.xerathcore.CoreUtils;

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
    }


    private void initData() {
    }


}