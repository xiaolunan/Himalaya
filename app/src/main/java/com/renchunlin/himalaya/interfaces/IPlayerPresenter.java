package com.renchunlin.himalaya.interfaces;

import com.renchunlin.himalaya.base.IBasePresenter;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/17.
 * PS: Not easy to write code, please indicate.
 */
public interface IPlayerPresenter extends IBasePresenter<IPlayerViewCallback> {
    /**
     * 播放
     */
    void play();

    /**
     * 暂停
     */
    void pause();

    /**
     * 停止
     */
    void stop();

    /**
     * 上一首
     */
    void playPre();

    /**
     * 下一首
     */
    void playNext();

    /**
     * 切换播放模式
     */
    void switchPlayMode(XmPlayListControl.PlayMode mode);

    /**
     * 获得播放列表
     */
    void getPlayList();

    /**
     * 根据节目在列表中的位置进行播放
     */
    void playByIndex(int index);

    /**
     * 切换播放进度
     * @param progress
     */
    void seekTo(int progress);

    /**
     * 判断播放器是否在播放
     */
    boolean isPlay();

    /**
     * 把播放器列表内容反转
     */
    void reversePlayList();
}
