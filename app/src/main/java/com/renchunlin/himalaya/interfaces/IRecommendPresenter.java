package com.renchunlin.himalaya.interfaces;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/11.
 * PS: Not easy to write code, please indicate.
 */
public interface IRecommendPresenter {
    /**
     * 获取推荐内容
     */
    void getRecommendList();

    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();

    /**
     * 这个方法用于注册ui的对调
     */
    void registerViewCallback(IRecommendViewCallback callback);

    /**
     * 取消注册ui对调
     */
    void unRegisterViewCallback(IRecommendViewCallback callback);
}
