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
                btn1ClickMethod();
            }
        });
        findViewById(R.id.mBtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn2ClickMethod();
            }
        });
    }

    /**
     * 使用注解统计耗时方法
     */
    @CalculateTime
    private void btn1ClickMethod() {
        println("按钮1 点击了 ...");
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void btn2ClickMethod() {
        println("按钮2 点击了！！！");
    }

    public void println(String s) {
        System.out.println(s);
    }
}