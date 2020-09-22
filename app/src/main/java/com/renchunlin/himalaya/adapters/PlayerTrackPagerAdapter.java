package com.renchunlin.himalaya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.renchunlin.himalaya.R;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.ArrayList;
import java.util.List;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/22.
 * PS: Not easy to write code, please indicate.
 */
public class PlayerTrackPagerAdapter extends PagerAdapter {

    private List<Track> mData = new ArrayList<>();

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_track_page, container, false);
        container.addView(itemView);
        //设置数据
        //找到控件
        ImageView item = itemView.findViewById(R.id.track_pager_item);
        //设置图片
        Track track = mData.get(position);
        String coverUrlLarge = track.getCoverUrlLarge();
        Picasso.get().load(coverUrlLarge).into(item);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    public void setData(List<Track> list) {
        mData.getClass();
        mData.addAll(list);
        notifyDataSetChanged();
    }
}
