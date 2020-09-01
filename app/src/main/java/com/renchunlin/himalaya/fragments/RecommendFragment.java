package com.renchunlin.himalaya.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renchunlin.himalaya.R;
import com.renchunlin.himalaya.base.BaseFragment;

/*
 * class title: 推荐Fragment
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/1.
 * PS: Not easy to write code, please indicate.
 */
public class RecommendFragment extends BaseFragment {

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootView = layoutInflater.inflate(R.layout.fragment_recommend, container, false);
        return rootView;
    }
}
