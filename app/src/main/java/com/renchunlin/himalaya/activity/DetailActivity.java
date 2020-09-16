package com.renchunlin.himalaya.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.renchunlin.himalaya.Presenter.AlbumDetailPresenter;
import com.renchunlin.himalaya.R;
import com.renchunlin.himalaya.base.BaseActivity;
import com.renchunlin.himalaya.interfaces.IAlbumDetailViewCallback;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/15.
 * PS: Not easy to write code, please indicate.
 */
public class DetailActivity extends BaseActivity implements IAlbumDetailViewCallback {

    private AlbumDetailPresenter mAlbumDetailPresenter;
    private ImageView iv_large_cover, viv_small_cover;
    private TextView tv_album_title, tv_album_author;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        initView();

        mAlbumDetailPresenter = AlbumDetailPresenter.getInstance();
        mAlbumDetailPresenter.registerViewCallback(this);
    }

    private void initView() {
        iv_large_cover = findViewById(R.id.iv_large_cover);
        viv_small_cover = findViewById(R.id.viv_small_cover);
        tv_album_title = findViewById(R.id.tv_album_title);
        tv_album_author = findViewById(R.id.tv_album_author);
    }

    @Override
    public void onDetailListLoaded(List<Track> tracks) {

    }

    @Override
    public void onAlbumLoaded(Album album) {
        if (tv_album_title != null) {
            tv_album_title.setText(album.getAlbumTitle());
        }
        if (tv_album_author != null) {
            tv_album_author.setText(album.getAnnouncer().getNickname());
        }
        if (viv_small_cover != null) {
            Picasso.get().load(album.getCoverUrlLarge()).into(viv_small_cover);
        }
        if (iv_large_cover != null) {
            Picasso.get().load(album.getCoverUrlLarge()).into(iv_large_cover);
        }
    }
}
