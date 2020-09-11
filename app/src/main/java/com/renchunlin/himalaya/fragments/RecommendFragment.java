package com.renchunlin.himalaya.fragments;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.renchunlin.himalaya.Presenter.RecommendPresenter;
import com.renchunlin.himalaya.R;
import com.renchunlin.himalaya.adapters.RecommendListAdapter;
import com.renchunlin.himalaya.base.BaseFragment;
import com.renchunlin.himalaya.interfaces.IRecommendViewCallback;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

/*
 * class title: 推荐Fragment
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/1.
 * PS: Not easy to write code, please indicate.
 */
public class RecommendFragment extends BaseFragment implements IRecommendViewCallback {

    public static final String TAG = "RecommendFragment";
    private RecyclerView mRecommendRv;
    private RecommendListAdapter recommendListAdapter;
    private RecommendPresenter mRecommendPresenter;

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        //View加载完成
        View rootView = layoutInflater.inflate(R.layout.fragment_recommend, container, false);

        //RecyclerView的使用
        //1.找到控件
        mRecommendRv = rootView.findViewById(R.id.recommend_list);
        //2.设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecommendRv.setLayoutManager(linearLayoutManager);
        //设置RecyclerView item间距
        mRecommendRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = UIUtil.dip2px(view.getContext(), 5);
                outRect.bottom = UIUtil.dip2px(view.getContext(), 5);
                outRect.left = UIUtil.dip2px(view.getContext(), 5);
                outRect.right = UIUtil.dip2px(view.getContext(), 5);
            }
        });
        //3.设置适配器
        recommendListAdapter = new RecommendListAdapter();
        mRecommendRv.setAdapter(recommendListAdapter);

        //获取到逻辑层对象
        mRecommendPresenter = RecommendPresenter.getInstance();
        //先要设置通知接口的注册
        mRecommendPresenter.registerViewCallback(this);
        //获取推荐列表
        mRecommendPresenter.getRecommendList();

        //返回view,给界面显示
        return rootView;
    }

    @Override
    public void onRecommendListLoaded(List<Album> result) {
        //当我们获得到推荐内容的时候，这个方法就会被调用
        //数据回来了就是更新ui

        //把数据s设置给适配器，并且更新ui
        recommendListAdapter.setData(result);
    }

    @Override
    public void onLoaderMore(List<Album> result) {

    }

    @Override
    public void onRefreshMore(List<Album> result) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //取消接口的注册
        if (mRecommendPresenter != null) {
            mRecommendPresenter.unRegisterViewCallback(this);
        }
    }
}
