package com.renchunlin.himalaya.base;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/17.
 * PS: Not easy to write code, please indicate.
 */
public interface IBasePresenter<T> {
    /**
     * 注册回调接口
     *
     * @param t
     */
    void registerViewCallback(T t);

    /**
     * 取消注册
     *
     * @param t
     */
    void unRegisterViewCallback(T t);
}
