package com.renchunlin.himalaya.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.renchunlin.himalaya.R;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/1.
 * PS: Not easy to write code, please indicate.
 */
public class IndicatorAdapter extends CommonNavigatorAdapter {

    private String[] stringArray;

    public IndicatorAdapter(Context context) {
        stringArray = context.getResources().getStringArray(R.array.indicator_title);
    }

    @Override
    public int getCount() {
        if (stringArray != null) {
            return stringArray.length;
        }
        return 0;
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        //创建View
        SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
        //设置一般情况下的颜色为灰色
        simplePagerTitleView.setNormalColor(Color.parseColor("#aaffffff"));
        //设置选中情况下的颜色为黑色
        simplePagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
        //单位sp
        simplePagerTitleView.setTextSize(18);
        //设置要显示的内容
        simplePagerTitleView.setText(stringArray[index]);
        //设置title的点击事件，这里的话，如果点击title，那么就选中下面的ViewPager到对应的index里面去
        //也就是说，当我们点击了title的时候，下面ViewPager会对应着Index进行切换内容
        simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //切换ViewPager的内容，如果index不一样的话
                //mViewPager.setCurrentItem(index);
            }
        });
        //把这个创建好的view返回回去
        return simplePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
        linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
        linePagerIndicator.setColors(Color.parseColor("#ffffff"));
        return linePagerIndicator;
    }
}
