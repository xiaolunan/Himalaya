package com.renchunlin.himalaya.Presenter;

import com.renchunlin.himalaya.interfaces.IRecommendPresenter;
import com.renchunlin.himalaya.interfaces.IRecommendViewCallback;
import com.renchunlin.himalaya.utils.Constants;
import com.renchunlin.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/11.
 * PS: Not easy to write code, please indicate.
 */
public class RecommendPresenter implements IRecommendPresenter {

    private static RecommendPresenter recommendPresenter;
    public static final String TAG = "RecommendPresenter";
    private List<IRecommendViewCallback> mCallbacks = new ArrayList<>();

    private RecommendPresenter() {

    }

    /**
     * 获取单例对象
     *
     * @return
     */
    public synchronized static RecommendPresenter getInstance() {
        if (recommendPresenter == null) {
            recommendPresenter = new RecommendPresenter();
        }
        return recommendPresenter;
    }

    @Override
    public void getRecommendList() {
        //获取推荐内容
        //封装参数
        Map<String, String> map = new HashMap<String, String>();
        //这个参数表示一页数据返回多少条
        map.put(DTransferConstants.LIKE_COUNT, Constants.RECOMMEND_COUNT + "");
        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(GussLikeAlbumList gussLikeAlbumList) {
                //数据获取成功
                if (gussLikeAlbumList != null) {
                    List<Album> albumList = gussLikeAlbumList.getAlbumList();
                    if (albumList != null) {
                        //数据回来我们要去更新ui
                        //upRecommendUI(albumList);
                        handlerRecommendResult(albumList);
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                //数据获取失败
                LogUtil.e(TAG, "error---->" + i);
                LogUtil.e(TAG, "errorMsg---->" + s);
            }
        });
    }

    private void handlerRecommendResult(List<Album> albumList) {
        //通知ui更新
        if (mCallbacks != null) {
            for (IRecommendViewCallback callback : mCallbacks) {
                callback.onRecommendListLoaded(albumList);
            }
        }
    }

    @Override
    public void pull2RefreshMore() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void registerViewCallback(IRecommendViewCallback callback) {
        if (mCallbacks != null && !mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
    }

    @Override
    public void unRegisterViewCallback(IRecommendViewCallback callback) {
        if (mCallbacks != null) {
            mCallbacks.remove(callback);
        }
    }
}
