package com.renchunlin.himalaya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.renchunlin.himalaya.R;
import com.renchunlin.himalaya.utils.Wan;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/2.
 * PS: Not easy to write code, please indicate.
 */
public class RecommendListAdapter extends RecyclerView.Adapter<RecommendListAdapter.ViewHolder> {

    private List<Album> mData = new ArrayList<>();
    private static final String TAG = "RecommendListAdapter";
    private OnRecommendItemClickListener mItemClickListener = null;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //这里是加载View
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //这里是设置数据
        holder.itemView.setTag(position);
        holder.setData(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LogUtil.i(TAG, "item-->" + v.getTag());
                if (mItemClickListener != null) {
                    int clickPosition = (int) v.getTag();
                    mItemClickListener.onItemClick(clickPosition, mData.get(clickPosition));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //返回要显示的个数
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setData(List<Album> albumList) {
        if (albumList != null) {
            mData.clear();
            mData.addAll(albumList);
        }
        //更新ui
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(Album album) {
            //找到各个控件
            //专辑封面
            ImageView albumCover = itemView.findViewById(R.id.album_cover);
            //Title
            TextView albumTitleTv = itemView.findViewById(R.id.album_title_tv);
            //描述
            TextView albumDesTv = itemView.findViewById(R.id.album_description_tv);
            //播放数量
            TextView albumPlayCountTv = itemView.findViewById(R.id.album_play_count);
            //专辑内容数量
            TextView albumContentCountTv = itemView.findViewById(R.id.album_content_size);

            albumTitleTv.setText(album.getAlbumTitle());
            albumDesTv.setText(album.getAlbumIntro());
            albumPlayCountTv.setText(Wan.numToWan(album.getPlayCount()));
            albumContentCountTv.setText(album.getIncludeTrackCount() + "集");
            Picasso.get().load(album.getCoverUrlLarge()).into(albumCover);
        }
    }

    public void setRecommendItemClickListener(OnRecommendItemClickListener listener) {
        mItemClickListener = listener;
    }

    public interface OnRecommendItemClickListener {
        void onItemClick(int position, Album album);
    }
}
