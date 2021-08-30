package com.ng.xerath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;


/**
 * 1.基于注解统计方法耗时
 * 2.hook所有点击
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.mBtn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                println("按钮1 点击了！！！");
            }
        });

        findViewById(R.id.mBtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                println("按钮2 点击了！！！");

            }
        });
        initView();
    }

    /**
     * 使用注解统计耗时方法
     */
    @CalculateTime
    private void initView() {
        println("initView");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void test() {
        System.out.println("test");
    }

    public void println(String s) {
        System.out.println(s);
    }
}