package com.renchunlin.himalaya.utils;

import com.renchunlin.himalaya.base.BaseFragment;
import com.renchunlin.himalaya.fragments.HistoryFragment;
import com.renchunlin.himalaya.fragments.RecommendFragment;
import com.renchunlin.himalaya.fragments.SubscriptionFragment;

import java.util.HashMap;
import java.util.Map;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/1.
 * PS: Not easy to write code, please indicate.
 */
public class FragmentCreator {
    public static final int INDEX_RECOMMEND = 0;
    public static final int INDEX_SUBSCRIPTION = 1;
    public static final int INDEX_HISTORY = 2;

    public final static int PAGE_COUNT = 3;

    private static Map<Integer, BaseFragment> sCache = new HashMap<>();

    public static BaseFragment getFragment(int index) {
        BaseFragment baseFragment = sCache.get(index);
        if (baseFragment != null) {
            return baseFragment;
        }

        switch (index) {
            case INDEX_RECOMMEND:
                baseFragment = new RecommendFragment();
                break;
            case INDEX_HISTORY:
                baseFragment = new SubscriptionFragment();
                break;
            case INDEX_SUBSCRIPTION:
                baseFragment = new HistoryFragment();
                break;
        }

        sCache.put(index, baseFragment);
        return baseFragment;
    }
}
