package com.renchunlin.himalaya.Presenter;

import com.renchunlin.himalaya.interfaces.IPlayerPresenter;
import com.renchunlin.himalaya.interfaces.IPlayerViewCallback;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/17.
 * PS: Not easy to write code, please indicate.
 */
public class PlayerPresenter implements IPlayerPresenter {

    private static PlayerPresenter sPlayerPresenter;

    private PlayerPresenter() {
    }

    public synchronized static PlayerPresenter getInstance() {
        if (sPlayerPresenter == null) {
            sPlayerPresenter = new PlayerPresenter();
        }
        return sPlayerPresenter;
    }

    @Override
    public void play() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void playPre() {

    }

    @Override
    public void playNext() {

    }

    @Override
    public void switchPlayMode(XmPlayListControl.PlayMode mode) {

    }

    @Override
    public void getPlayList() {

    }

    @Override
    public void playByIndex(int index) {

    }

    @Override
    public void seekTo(int progress) {

    }

    @Override
    public void registerViewCallback(IPlayerViewCallback iPlayerViewCallback) {

    }

    @Override
    public void unRegisterViewCallback(IPlayerViewCallback iPlayerViewCallback) {

    }
}
