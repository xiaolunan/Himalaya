package com.renchunlin.himalaya;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.renchunlin.himalaya.adapters.IndicatorAdapter;
import com.renchunlin.himalaya.adapters.MainContentAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;


public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";
    private MagicIndicator magicIndicator;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        magicIndicator = findViewById(R.id.main_indicator);
        magicIndicator.setBackgroundColor(getResources().getColor(R.color.main_color));
        //创建indicator的适配器
        IndicatorAdapter adapter = new IndicatorAdapter(this);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(adapter);

        //ViewPager
        viewPager = findViewById(R.id.content_pager);
        //创建内容适配器
        MainContentAdapter mainContentAdapter = new MainContentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mainContentAdapter);

        //把ViewPager和Indicator绑定到一起
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }
}