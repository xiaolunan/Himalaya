package com.renchunlin.himalaya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.renchunlin.himalaya.R;
import com.renchunlin.himalaya.base.BaseApplication;
import com.renchunlin.himalaya.view.SobPobWindow;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.ArrayList;
import java.util.List;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/25.
 * PS: Not easy to write code, please indicate.
 */
public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.InnerHolder> {

    private List<Track> mData=new ArrayList<>();
    private View mItemView;
    private int playingIndex=0;
    private SobPobWindow.PlayListItemClickListener mItemClickListener=null;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_play_list, parent, false);
        return new InnerHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, final int position) {
        //设置数据
        Track track = mData.get(position);
        TextView trackTitleTv = holder.itemView.findViewById(R.id.track_title_tv);
        //设置字体颜色
        trackTitleTv.setTextColor(BaseApplication.getAppContext().getResources().getColor(playingIndex==position?R.color.main_color
                :R.color.play_list_text_color));
        trackTitleTv.setText(track.getTrackTitle());
        //找播放状态的图标
        View playingIconView=holder.itemView.findViewById(R.id.play_icon_iv);
        playingIconView.setVisibility(playingIndex==position?View.VISIBLE:View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Track> list) {
        //设置数据，更新列表
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    //设置播放列表当前播放的位置
    public void setCurrentPlayPosition(int position) {
        playingIndex=position;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(SobPobWindow.PlayListItemClickListener listener) {
        this.mItemClickListener=listener;
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
