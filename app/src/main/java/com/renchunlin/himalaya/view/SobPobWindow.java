package com.renchunlin.himalaya.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.renchunlin.himalaya.R;
import com.renchunlin.himalaya.adapters.PlayListAdapter;
import com.renchunlin.himalaya.base.BaseApplication;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.List;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/24.
 * PS: Not easy to write code, please indicate.
 */
public class SobPobWindow extends PopupWindow {

    private final View mPopView;
    private TextView mCloseBtn;
    private RecyclerView mTrackList;
    private PlayListAdapter mPlayListAdapter;
    private TextView mPlayModeTv;
    private ImageView mPlayModeIv;
    private View mPlayMpdeContainer;
    private PlayListActionListener mPlayModeClickListener = null;
    private View mOrderBtnContainer;
    private ImageView mOrderIcon;
    private TextView mOrderText;

    public SobPobWindow() {
        //设置它的宽高
        super(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //设置setOutsideTouchable之前，要先设置setBackgroundDrawable,否则点击外部无法关闭pop
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //设置外边可点击
        setOutsideTouchable(true);

        //载入View
        mPopView = LayoutInflater.from(BaseApplication.getAppContext()).inflate(R.layout.pop_play_list, null);
        //设置内容
        setContentView(mPopView);

        //设置窗口进去和退出的动画
        setAnimationStyle(R.style.pop_animation);
        initView();
        initEvent();
    }

    private void initEvent() {
        //点击关闭以后，窗口消失
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //在列表中切换播放模式
        mPlayMpdeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //切换播放模式
                if (mPlayModeClickListener != null) {
                    mPlayModeClickListener.onPlayModeClick();
                }
            }
        });

        //切换播放列表为顺序或者逆序
        mOrderBtnContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayModeClickListener != null) {
                    mPlayModeClickListener.onOrderClick();
                }
            }
        });
    }

    private void initView() {
        mCloseBtn = mPopView.findViewById(R.id.play_list_close_btn);
        //先找到控件
        mTrackList = mPopView.findViewById(R.id.play_list_rv);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BaseApplication.getAppContext());
        mTrackList.setLayoutManager(linearLayoutManager);
        //设置适配器
        mPlayListAdapter = new PlayListAdapter();
        mTrackList.setAdapter(mPlayListAdapter);
        //播放模式相关
        mPlayModeTv = mPopView.findViewById(R.id.play_list_play_mode_tv);
        mPlayModeIv = mPopView.findViewById(R.id.play_list_play_mode_iv);
        mPlayMpdeContainer = mPopView.findViewById(R.id.play_list_plat_mode_container);
        mOrderBtnContainer = mPopView.findViewById(R.id.play_list_order_container);
        mOrderIcon = mPopView.findViewById(R.id.play_list_order_iv);
        mOrderText = mPopView.findViewById(R.id.play_list_order_tv);
    }

    /**
     * 给适配器设置数据
     *
     * @param list
     */
    public void setListData(List<Track> list) {
        if (mPlayListAdapter != null) {
            mPlayListAdapter.setData(list);
        }
    }

    //设置播放列表当前播放的位置
    public void setCurrentPlayPosition(int position) {
        if (mPlayListAdapter != null) {
            mPlayListAdapter.setCurrentPlayPosition(position);
            mTrackList.scrollToPosition(position);
        }
    }

    public void setPlayListItemClickListener(PlayListItemClickListener listener) {
        mPlayListAdapter.setOnItemClickListener(listener);
    }

    //更新播放列表的播放模式
    public void updatePlayMode(XmPlayListControl.PlayMode playMode) {
        updatePlayModeBtnImg(playMode);
    }

    /**
     * 根据当前的状态，更新播放模式图标
     */
    private void updatePlayModeBtnImg(XmPlayListControl.PlayMode playMode) {
        int resId = R.drawable.selector_play_mode_list_order;
        int textId = R.string.play_mode_order_text;
        switch (playMode) {
            //列表播放
            case PLAY_MODEL_LIST:
                resId = R.drawable.selector_play_mode_list_order;
                textId = R.string.play_mode_order_text;
                break;
            //随机播放
            case PLAY_MODEL_RANDOM:
                resId = R.drawable.selector_play_mode_random;
                textId = R.string.play_mode_random_text;
                break;
            //列表循环
            case PLAY_MODEL_LIST_LOOP:
                resId = R.drawable.selector_play_mode_list_order_looper;
                textId = R.string.play_mode_list_play_text;
                break;
            //单曲循环播放
            case PLAY_MODEL_SINGLE_LOOP:
                resId = R.drawable.selector_play_mode_single_loop;
                textId = R.string.play_mode_single_play_text;
                break;
        }
        mPlayModeIv.setImageResource(resId);
        mPlayModeTv.setText(textId);
    }

    public interface PlayListItemClickListener {
        void onItemClick(int position);
    }

    public void setPlayListActionListener(PlayListActionListener listener) {
        this.mPlayModeClickListener = listener;
    }

    public interface PlayListActionListener {
        //播放模式被点击了
        void onPlayModeClick();

        //播放逆序或者顺序切换按钮被点击了
        void onOrderClick();
    }

    //更新切换列表顺序和逆序的按钮和文字更新
    public void updateOrderIcon(boolean isOrder){
        mOrderIcon.setImageResource(isOrder?R.drawable.selector_list_reverse_order:R.drawable.selector_list_positive_order);
        mOrderText.setText(BaseApplication.getAppContext().getResources().getString(isOrder?R.string.revers_text:R.string.order_text));
    }
}
