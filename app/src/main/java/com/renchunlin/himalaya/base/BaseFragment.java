package com.renchunlin.himalaya.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/1.
 * PS: Not easy to write code, please indicate.
 */
public abstract class BaseFragment extends Fragment {

    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = onSubViewLoaded(inflater,container);
        return mRootView;
    }

    protected abstract View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container);
}
