package com.renchunlin.himalaya.interfaces;

import android.os.Trace;

import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.List;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/17.
 * PS: Not easy to write code, please indicate.
 */
public interface IPlayerViewCallback {
    /**
     * 开始播放
     */
    void onPlayStart();

    /**
     * 播放暂停
     */
    void onPlayPause();

    /**
     * 播放停止
     */
    void onPlayStop();

    /**
     * 播放错误
     */
    void onPlayError();

    /**
     * 播放下一首
     */
    void onNextPlay(Track track);

    /**
     * 播放上一首
     */
    void onPrePlay(Track track);

    /**
     * 播放列表数据加载完成
     *
     * @param list 播放器类列表数据
     */
    void onListLoaded(List<Trace> list);

    /**
     * 播放模式改变了
     *
     * @param playMode
     */
    void onPlayModeChage(XmPlayListControl.PlayMode playMode);

    /**
     * 进度条的改变
     *
     * @param currentProgress
     * @param total
     */
    void ProgressChange(long currentProgress, long total);

    /**
     * 广告正在加载
     */
    void onAdLoading();

    /**
     * 广告结束
     */
    void onAdFinished();
}
