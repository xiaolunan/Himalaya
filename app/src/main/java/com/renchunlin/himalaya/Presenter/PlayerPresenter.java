package com.renchunlin.himalaya.Presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.renchunlin.himalaya.base.BaseApplication;
import com.renchunlin.himalaya.interfaces.IPlayerPresenter;
import com.renchunlin.himalaya.interfaces.IPlayerViewCallback;
import com.renchunlin.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.advertis.Advertis;
import com.ximalaya.ting.android.opensdk.model.advertis.AdvertisList;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.advertis.IXmAdsStatusListener;
import com.ximalaya.ting.android.opensdk.player.constants.PlayerConstants;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_LIST;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_RANDOM;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE_LOOP;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/17.
 * PS: Not easy to write code, please indicate.
 */
public class PlayerPresenter implements IPlayerPresenter, IXmAdsStatusListener, IXmPlayerStatusListener {

    private static final String TAG = "PlayerPresenter";
    private static PlayerPresenter sPlayerPresenter;
    private XmPlayerManager mXmPlayerManager;
    private List<IPlayerViewCallback> mIPlayerViewCallback = new ArrayList<>();
    private Track mCurrentTrack;
    private int mCurrentIndex = 0;
    private final SharedPreferences mPlayMode;
    private static final int PLAY_MODEL_LIST_INT = 0;
    private static final int PLAY_MODEL_LIST_LOOP_INT = 1;
    private static final int PLAY_MODEL_RANDOM_INT = 2;
    private static final int PLAY_MODEL_SINGLE_LOOP_INT = 3;
    //sp's key and final name
    public static final String PLAY_MODE_SP_NAME = "PlayMode";
    public static final String PLAY_MODE_SP_KEY = "currentPlayMode";
    private XmPlayListControl.PlayMode mCurrentPlayMode = PLAY_MODEL_LIST;
    private boolean mIsReverse=false;

    private PlayerPresenter() {
        mXmPlayerManager = XmPlayerManager.getInstance(BaseApplication.getAppContext());
        //监听广告播放状态接口
        mXmPlayerManager.addAdsStatusListener(this);
        //注册播放状态相关的接口
        mXmPlayerManager.addPlayerStatusListener(this);
        //需要记住当前的播放模式
        mPlayMode = BaseApplication.getAppContext().getSharedPreferences(PLAY_MODE_SP_NAME, Context.MODE_PRIVATE);
    }

    public synchronized static PlayerPresenter getInstance() {
        if (sPlayerPresenter == null) {
            sPlayerPresenter = new PlayerPresenter();
        }
        return sPlayerPresenter;
    }

    private boolean isPlayListSet = false;

    //设置播放列表数据
    public void setPlayList(List<Track> list, int playIndex) {
        if (mXmPlayerManager != null) {
            mXmPlayerManager.setPlayList(list, playIndex);
            isPlayListSet = true;
            mCurrentTrack = list.get(playIndex);
            mCurrentIndex = playIndex;
        } else {
            LogUtil.i(TAG, "mXmPlayerManager is null");
            isPlayListSet = false;
        }
    }

    @Override
    public void play() {
        if (isPlayListSet) {
            mXmPlayerManager.play();
        }
    }

    @Override
    public void pause() {
        if (mXmPlayerManager != null) {
            mXmPlayerManager.pause();
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void playPre() {
        //播放上一个节目
        if (mXmPlayerManager != null) {
            mXmPlayerManager.playPre();
        }
    }

    @Override
    public void playNext() {
        //播放下一个节目
        if (mXmPlayerManager != null) {
            mXmPlayerManager.playNext();
        }
    }

    @Override
    public void switchPlayMode(XmPlayListControl.PlayMode mode) {
        if (mXmPlayerManager != null) {
            mCurrentPlayMode = mode;
            mXmPlayerManager.setPlayMode(mode);
            //通知ui更新播放模式
            for (IPlayerViewCallback iPlayerViewCallback : mIPlayerViewCallback) {
                iPlayerViewCallback.onPlayModeChange(mode);
            }
        }
        //保存到sp里头去
        SharedPreferences.Editor edit = mPlayMode.edit();
        edit.putInt(PLAY_MODE_SP_KEY, getIntByPlayMode(mode));
        edit.commit();
    }

    //XmPlayListControl.PlayMode枚举 转int
    public int getIntByPlayMode(XmPlayListControl.PlayMode mode) {
        switch (mode) {
            case PLAY_MODEL_LIST:
                return PLAY_MODEL_LIST_INT;
            case PLAY_MODEL_LIST_LOOP:
                return PLAY_MODEL_LIST_LOOP_INT;
            case PLAY_MODEL_RANDOM:
                return PLAY_MODEL_RANDOM_INT;
            case PLAY_MODEL_SINGLE_LOOP:
                return PLAY_MODEL_SINGLE_LOOP_INT;
        }
        return PLAY_MODEL_LIST_INT;
    }

    //int转 XmPlayListControl.PlayMode枚举
    public XmPlayListControl.PlayMode getModeByInt(int index) {
        switch (index) {
            case PLAY_MODEL_LIST_INT:
                return PLAY_MODEL_LIST;
            case PLAY_MODEL_LIST_LOOP_INT:
                return PLAY_MODEL_LIST_LOOP;
            case PLAY_MODEL_RANDOM_INT:
                return PLAY_MODEL_RANDOM;
            case PLAY_MODEL_SINGLE_LOOP_INT:
                return PLAY_MODEL_SINGLE_LOOP;
        }
        return PLAY_MODEL_LIST;
    }

    @Override
    public void getPlayList() {
        if (mXmPlayerManager != null) {
            List<Track> playList = mXmPlayerManager.getPlayList();
            for (IPlayerViewCallback iPlayerViewCallback : mIPlayerViewCallback) {
                iPlayerViewCallback.onListLoaded(playList);
            }
        }
    }

    @Override
    public void playByIndex(int index) {
        //切换播放器到第index的位置进行播放
        if (mXmPlayerManager != null) {
            mXmPlayerManager.play(index);
        }
    }

    @Override
    public void seekTo(int progress) {
        //更新播放器的进度
        if (mXmPlayerManager != null) {
            mXmPlayerManager.seekTo(progress);
        }
    }

    @Override
    public boolean isPlay() {
        //返回当前是否正在播放
        return mXmPlayerManager.isPlaying();
    }

    @Override
    public void reversePlayList() {
        //把播放列表反转
        List<Track> playList = mXmPlayerManager.getPlayList();
        //反转List
        Collections.reverse(playList);
        mIsReverse=!mIsReverse;
        //把反转的list设置回去  第一个参数是播放列表，第二个参数是开始播放的下标
        //切换播放列表的顺序以后，下标由1->8==>新的下标=总的内容个数-1-当前下标
        mCurrentIndex = playList.size() - 1 - mCurrentIndex;    //修改下标
        mXmPlayerManager.setPlayList(playList,mCurrentIndex);
        //更新ui
        mCurrentTrack= (Track) mXmPlayerManager.getCurrSound();

        for (IPlayerViewCallback iPlayerViewCallback : mIPlayerViewCallback) {
            iPlayerViewCallback.onListLoaded(playList);
            iPlayerViewCallback.onTrackUpdate(mCurrentTrack,mCurrentIndex);
            iPlayerViewCallback.updateListOrder(mIsReverse);
        }
    }

    @Override
    public void registerViewCallback(IPlayerViewCallback iPlayerViewCallback) {
        if (!mIPlayerViewCallback.contains(iPlayerViewCallback)) {
            mIPlayerViewCallback.add(iPlayerViewCallback);

            iPlayerViewCallback.onTrackUpdate(mCurrentTrack, mCurrentIndex);

            //从sp里头拿
            int modeIndex = mPlayMode.getInt(PLAY_MODE_SP_KEY, PLAY_MODEL_LIST_INT);
            mCurrentPlayMode = getModeByInt(modeIndex);
            iPlayerViewCallback.onPlayModeChange(mCurrentPlayMode);
        }
    }

    @Override
    public void unRegisterViewCallback(IPlayerViewCallback iPlayerViewCallback) {
        mIPlayerViewCallback.remove(iPlayerViewCallback);
    }

    //=============广告相关回调方法 start======================
    @Override
    public void onStartGetAdsInfo() {
        LogUtil.i(TAG, "onStartGetAdsInfo");
    }

    @Override
    public void onGetAdsInfo(AdvertisList advertisList) {
        LogUtil.i(TAG, "onGetAdsInfo");
    }

    @Override
    public void onAdsStartBuffering() {
        LogUtil.i(TAG, "onAdsStartBuffering");
    }

    @Override
    public void onAdsStopBuffering() {
        LogUtil.i(TAG, "onAdsStopBuffering");
    }

    @Override
    public void onStartPlayAds(Advertis advertis, int i) {
        LogUtil.i(TAG, "onStartPlayAds");
    }

    @Override
    public void onCompletePlayAds() {
        LogUtil.i(TAG, "onCompletePlayAds");
    }

    @Override
    public void onError(int what, int extra) {
        LogUtil.i(TAG, "onError what = >" + what + " extra = >" + extra);
    }
    //=============广告相关回调方法 end=================

    //=============播放器相关的回调方法 start=================
    @Override
    public void onPlayStart() {
        LogUtil.i(TAG, "onPlayStart");
        for (IPlayerViewCallback iPlayerViewCallback : mIPlayerViewCallback) {
            iPlayerViewCallback.onPlayStart();
        }
    }

    @Override
    public void onPlayPause() {
        LogUtil.i(TAG, "onPlayPause");
        for (IPlayerViewCallback iPlayerViewCallback : mIPlayerViewCallback) {
            iPlayerViewCallback.onPlayPause();
        }
    }

    @Override
    public void onPlayStop() {
        LogUtil.i(TAG, "onPlayStop");
        for (IPlayerViewCallback iPlayerViewCallback : mIPlayerViewCallback) {
            iPlayerViewCallback.onPlayStop();
        }
    }

    @Override
    public void onSoundPlayComplete() {
        LogUtil.i(TAG, "onSoundPlayComplete");
    }

    @Override
    public void onSoundPrepared() {
        LogUtil.i(TAG, "onSoundPrepared");
        //设置播放模式
        if (mXmPlayerManager != null) {
            mXmPlayerManager.setPlayMode(mCurrentPlayMode);
        }
        if (mXmPlayerManager.getPlayerStatus() == PlayerConstants.STATE_PREPARED) {
            //播放器准备完了，可以去播放了
            mXmPlayerManager.play();
        }
    }

    @Override
    public void onSoundSwitch(PlayableModel lastModel, PlayableModel curModel) {
        if (lastModel != null) {
            LogUtil.i("PlayableModel", " lastModel == >" + lastModel.getKind());
        }
        LogUtil.i("PlayableModel", " curModel == > " + curModel.getKind());
        //curModel代表当前播放的内容
        //通过getKind()方法来获取它是什么类型
        //track表示是track类型
        //第一种写法：不推荐
        /*if ("track".equals(curModel.getKind())) {
            Track currentTrack = (Track) curModel;
            LogUtil.i("PlayableModel", " title == > " + currentTrack.getTrackTitle());
        }*/

        mCurrentIndex = mXmPlayerManager.getCurrentIndex();
        //第二种写法
        if (curModel instanceof Track) {
            mCurrentTrack = (Track) curModel;
            LogUtil.i("PlayableModel", " title == > " + mCurrentTrack.getTrackTitle());
            //更新ui播放页面标题
            for (IPlayerViewCallback iPlayerViewCallback : mIPlayerViewCallback) {
                iPlayerViewCallback.onTrackUpdate(mCurrentTrack, mCurrentIndex);
            }
        }
    }

    @Override
    public void onBufferingStart() {
        LogUtil.i(TAG, "onBufferingStart");
    }

    @Override
    public void onBufferingStop() {
        LogUtil.i(TAG, "onBufferingStop");
    }

    @Override
    public void onBufferProgress(int progress) {
        LogUtil.i(TAG, "onBufferProgress" + progress);
    }

    @Override
    public void onPlayProgress(int currPos, int duration) {
        //单位是毫秒
        LogUtil.i(TAG, " currPos == > " + currPos + " duration ==> " + duration);
        for (IPlayerViewCallback iPlayerViewCallback : mIPlayerViewCallback) {
            iPlayerViewCallback.ProgressChange(currPos, duration);
        }
    }

    @Override
    public boolean onError(XmPlayerException e) {
        LogUtil.i(TAG, "onError e --> " + e);
        return false;
    }
    //=============播放器相关的回调方法 end=================
}
