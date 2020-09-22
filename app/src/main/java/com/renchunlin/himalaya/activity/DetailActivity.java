package com.renchunlin.himalaya.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.renchunlin.himalaya.Presenter.AlbumDetailPresenter;
import com.renchunlin.himalaya.Presenter.PlayerPresenter;
import com.renchunlin.himalaya.R;
import com.renchunlin.himalaya.adapters.DetailListAdapter;
import com.renchunlin.himalaya.base.BaseActivity;
import com.renchunlin.himalaya.interfaces.IAlbumDetailViewCallback;
import com.renchunlin.himalaya.utils.ImageBlur;
import com.renchunlin.himalaya.utils.LogUtil;
import com.renchunlin.himalaya.view.UILoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/15.
 * PS: Not easy to write code, please indicate.
 */
public class DetailActivity extends BaseActivity implements IAlbumDetailViewCallback, UILoader.OnRetryClickListener, DetailListAdapter.ItemClickListener {

    private static final String TAG = "DetailActivity";
    private AlbumDetailPresenter mAlbumDetailPresenter;
    private ImageView iv_large_cover, viv_small_cover;
    private TextView tv_album_title, tv_album_author;
    private int mCurrentPage = 1;
    private RecyclerView mDetailList;
    private DetailListAdapter mDetailListAdapter;
    private FrameLayout mDetailListContainer;
    private UILoader mUiLoader;
    private int mCurrentId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        initView();

        mAlbumDetailPresenter = AlbumDetailPresenter.getInstance();
        mAlbumDetailPresenter.registerViewCallback(this);
    }

    private void initView() {
        mDetailListContainer = findViewById(R.id.detail_list_container);

        if (mUiLoader == null) {
            mUiLoader = new UILoader(this) {
                @Override
                protected View getSuccessView(ViewGroup container) {
                    return createSuccessView(container);
                }
            };

            //添加到DetailListContainer里面去
            //添加之前先干掉所有的
            mDetailListContainer.removeAllViews();
            mDetailListContainer.addView(mUiLoader);

            mUiLoader.setOnRetryClickListener(this);
        }

        iv_large_cover = findViewById(R.id.iv_large_cover);
        viv_small_cover = findViewById(R.id.viv_small_cover);
        tv_album_title = findViewById(R.id.tv_album_title);
        tv_album_author = findViewById(R.id.tv_album_author);
    }

    private View createSuccessView(ViewGroup container) {
        View detailListView = LayoutInflater.from(this).inflate(R.layout.item_detail_list, container, false);
        mDetailList = detailListView.findViewById(R.id.album_detail_list);
        //RecyclerView的使用步骤
        //第一步：设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //第二步：设置适配器
        mDetailList.setLayoutManager(layoutManager);
        mDetailListAdapter = new DetailListAdapter();
        mDetailList.setAdapter(mDetailListAdapter);
        //设置item的上下间距
        mDetailList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = UIUtil.dip2px(view.getContext(), 2);
                outRect.bottom = UIUtil.dip2px(view.getContext(), 2);
                outRect.left = UIUtil.dip2px(view.getContext(), 2);
                outRect.right = UIUtil.dip2px(view.getContext(), 2);
            }
        });
        mDetailListAdapter.setItemClickListener(this);
        return detailListView;
    }

    @Override
    public void onDetailListLoaded(List<Track> tracks) {
        //判断数据结果，根据结果控制ui显示
        if (tracks != null) {
            if (tracks.size() == 0) {
                if (mUiLoader != null) {
                    mUiLoader.updateStatus(UILoader.UIStatus.EMPTY);
                }
            } else {
                if (mUiLoader != null) {
                    mUiLoader.updateStatus(UILoader.UIStatus.SUCCESS);
                }
            }
            //更新/设置ui数据
            mDetailListAdapter.setData(tracks);
        }
    }

    @Override
    public void onAlbumLoaded(Album album) {
        long id = album.getId();
        mCurrentId = (int) id;
        //获取专辑的详情内容
        if (mAlbumDetailPresenter != null) {
            mAlbumDetailPresenter.getAlbumDetail((int) id, mCurrentPage);
        }
        //拿到数据显示Loading状态
        if (mUiLoader != null) {
            mUiLoader.updateStatus(UILoader.UIStatus.LOADING);
        }
        if (tv_album_title != null) {
            tv_album_title.setText(album.getAlbumTitle());
        }
        if (tv_album_author != null) {
            tv_album_author.setText(album.getAnnouncer().getNickname());
        }
        if (viv_small_cover != null) {
            Picasso.get().load(album.getCoverUrlLarge()).into(viv_small_cover);
        }

        //做毛玻璃效果
        if (iv_large_cover != null) {
            Picasso.get().load(album.getCoverUrlLarge()).into(iv_large_cover, new Callback() {
                @Override
                public void onSuccess() {
                    //到这里才说明有图片
                    Drawable drawable = iv_large_cover.getDrawable();
                    if (drawable != null) {
                        ImageBlur.makeBlur(iv_large_cover, DetailActivity.this);
                    }
                }

                @Override
                public void onError(Exception e) {
                    LogUtil.i(TAG, e.toString());
                }
            });

        }
    }

    @Override
    public void noNetworkError(int errorCode, String errorMsg) {
        //请求发生错误时，显示网络异常状态
        if (mUiLoader != null) {
            mUiLoader.updateStatus(UILoader.UIStatus.NETWORK_ERROR);
        }
    }

    @Override
    public void onRetryClick() {
        //这里面表示用户网络不佳的时候，去点击重新加载
        if (mAlbumDetailPresenter != null) {
            mAlbumDetailPresenter.getAlbumDetail(mCurrentId, mCurrentPage);
        }
    }

    @Override
    public void onItemClick(List<Track> mDetailData, int position) {
        //设置播放器数据
        PlayerPresenter.getInstance().setPlayList(mDetailData, position);
        Intent intent = new Intent(this, PlayerActivity.class);
        startActivity(intent);
    }
}
