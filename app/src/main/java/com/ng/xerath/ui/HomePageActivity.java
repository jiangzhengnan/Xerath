package com.ng.xerath.ui;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.ng.xerath.R;
import com.ng.xerath.func.DataMethodUtil;
import com.ng.xerath.func.FuncMethodUtil;
import com.ng.xerath.func.ViewMethodUtil;
import com.ng.xerath.func.member.TestMember1;
import com.ng.xerath.ui.adapter.ItemInfo;
import com.ng.xerath.ui.adapter.MyViewPagerAdapter;
import com.ng.xerath.ui.fragment.DataFragment;
import com.ng.xerath.ui.fragment.FunctionFragment;
import com.ng.xerathcore.CoreHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pumpkin
 */
public class HomePageActivity extends AppCompatActivity implements CoreHelper.CoreHelperListener {
    private ViewPager mFunVp;
    private TextView mShowTv;
    private PagerTabStrip mPts;
    private MyViewPagerAdapter mAdapter;
    private List<ItemInfo> mLayoutList;
    private FunctionFragment mFunctionFragment;
    private DataFragment mDataFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        CoreHelper.onCoreHelperListenerList.add(this);
        initView();
    }

    private void initView() {
        mLayoutList = new ArrayList<>();
        mFunctionFragment = new FunctionFragment();
        mFunctionFragment.setLogListener(this);
        mDataFragment = new DataFragment();
        mDataFragment.setLogListener(this);
        mLayoutList.add(new ItemInfo("功能", mFunctionFragment));
        mLayoutList.add(new ItemInfo("数据", mDataFragment));
        mPts = findViewById(R.id.pts_main);
        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mLayoutList);
        mFunVp = findViewById(R.id.vp_main);
        mFunVp.setAdapter(mAdapter);
        mFunVp.setCurrentItem(0);
        mShowTv = findViewById(R.id.tv_show);
        mShowTv.setText("初始化日志:");
        mShowTv.setMovementMethod(ScrollingMovementMethod.getInstance());
        findViewById(R.id.tv_clean).setOnClickListener(v -> {
            mShowTv.setText("");
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

}