package com.renchunlin.himalaya.Presenter;

import com.renchunlin.himalaya.interfaces.IAlbumDetailViewCallback;
import com.renchunlin.himalaya.interfaces.IAlbumDetialPresenter;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/15.
 * PS: Not easy to write code, please indicate.
 */
public class AlbumDetailPresenter implements IAlbumDetialPresenter {

    private static AlbumDetailPresenter albumDetailPresenter;
    private Album mTargetAlbum = null;
    private List<IAlbumDetailViewCallback> mCallbacks = new ArrayList<>();

    private AlbumDetailPresenter() {
    }

    public synchronized static AlbumDetailPresenter getInstance() {
        if (albumDetailPresenter == null) {
            albumDetailPresenter = new AlbumDetailPresenter();
        }
        return albumDetailPresenter;
    }

    @Override
    public void pull2RefreshMore() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void getAlbumDetail(int albumId, int page) {

    }

    @Override
    public void registerViewCallback(IAlbumDetailViewCallback callback) {
        if (!mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
            if (mTargetAlbum != null) {
                callback.onAlbumLoaded(mTargetAlbum);
            }
        }
    }

    @Override
    public void unRegisterViewCallback(IAlbumDetailViewCallback callback) {
        mCallbacks.remove(callback);
    }

    public void setTargetAlbum(Album targetAlbum) {
        this.mTargetAlbum = targetAlbum;
    }
}
