package com.renchunlin.himalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/11.
 * PS: Not easy to write code, please indicate.
 */
public interface IRecommendViewCallback {
    /**
     * 获取推荐内容的结果
     */
    void onRecommendListLoaded(List<Album> result);

    /**
     * 上拉加载更多
     */
    void onLoaderMore(List<Album> result);

    /**
     * 下拉刷新
     */
    void onRefreshMore(List<Album> result);
}
