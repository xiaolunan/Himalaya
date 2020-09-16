package com.renchunlin.himalaya.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.renchunlin.himalaya.R;

/*
 * class title:
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/14.
 * PS: Not easy to write code, please indicate.
 */
public class LoadingView extends androidx.appcompat.widget.AppCompatImageView {

    //旋转角度
    private int rotateDegree = 0;
    private boolean mNeedRotate = false;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置图标
        setImageResource(R.drawable.loading);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mNeedRotate = true;
        //绑定到window的时候
        //post,是去执行一个线程内容，在主线程下面执行的
        post(new Runnable() {
            @Override
            public void run() {
                rotateDegree += 30;
                rotateDegree = rotateDegree <= 360 ? rotateDegree : 0;
                invalidate();   //invalidate就会执行onDraw
                //是否继续旋转
                if (mNeedRotate) {
                    postDelayed(this, 200);
                }
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //从window解绑的时候
        mNeedRotate = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 第一个参数旋转角度
         * 第二个参数旋转的x坐标
         * 第三个参数旋转的y坐标
         */
        canvas.rotate(rotateDegree, getWidth() / 2, getHeight() / 2);
        super.onDraw(canvas);
    }
}
