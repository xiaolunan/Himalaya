package com.renchunlin.himalaya;

import android.os.Bundle;

import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.renchunlin.himalaya.adapters.IndicatorAdapter;
import com.renchunlin.himalaya.adapters.MainContentAdapter;
import com.renchunlin.himalaya.base.BaseActivity;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;


public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private MagicIndicator magicIndicator;
    private ViewPager viewPager;
    private IndicatorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    private void initView() {
        magicIndicator = findViewById(R.id.main_indicator);
        magicIndicator.setBackgroundColor(getResources().getColor(R.color.main_color));
        //创建indicator的适配器
        adapter = new IndicatorAdapter(this);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        //平分标题距离
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(adapter);

        //ViewPager
        viewPager = findViewById(R.id.content_pager);
        //创建内容适配器
        MainContentAdapter mainContentAdapter = new MainContentAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(mainContentAdapter);

        //把ViewPager和Indicator绑定到一起
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    private void initEvent() {
        adapter.setOnIndicatorTabClickListener(new IndicatorAdapter.OnIndicatorTabClickListener() {
            @Override
            public void onTabClick(int index) {
                if (viewPager != null) {
                    viewPager.setCurrentItem(index);
                }
            }
        });
    }
}