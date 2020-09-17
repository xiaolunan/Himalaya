package com.renchunlin.himalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/15.
 * PS: Not easy to write code, please indicate.
 */
public interface IAlbumDetailViewCallback {
    /**
     * 专辑详情内容加载出来了
     *
     * @param tracks
     */
    void onDetailListLoaded(List<Track> tracks);

    /**
     * 把album传UI使用
     *
     * @param album
     */
    void onAlbumLoaded(Album album);

    /**
     * 网络错误
     */
    void noNetworkError(int errorCode, String errorMsg);
}
