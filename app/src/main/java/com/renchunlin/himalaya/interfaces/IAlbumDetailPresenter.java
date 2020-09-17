package com.renchunlin.himalaya.interfaces;

import com.renchunlin.himalaya.base.IBasePresenter;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/15.
 * PS: Not easy to write code, please indicate.
 */
public interface IAlbumDetailPresenter extends IBasePresenter<IAlbumDetailViewCallback> {
    /**
     * 下拉刷新
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();

    /**
     * 获取专辑详情
     *
     * @param albumId
     * @param page
     */
    void getAlbumDetail(int albumId, int page);

    //注册UI通知接口
    //void registerViewCallback(IAlbumDetailViewCallback callback);

    // 删除UI通知
    //void unRegisterViewCallback(IAlbumDetailViewCallback callback);
}
