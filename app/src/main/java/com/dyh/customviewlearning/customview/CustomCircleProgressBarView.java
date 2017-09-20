package com.dyh.customviewlearning.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


import com.dyh.customviewlearning.R;
/**
 * Created by dyh on 2017/9/20.
 */

public class CustomCircleProgressBarView extends View {
    private static final String TAG = "CustomCircleProgressBar";
    //第一圈的颜色
    private int mFirstColor;
    //第二圈的颜色
    private int mSecondColor;
    //圈的宽度
    private int mCircleWidth;
    private int mSpeed;
    private int mProgress;

    private Paint mPaint;

    //是否应该开始下一个
    private boolean isNext = false;

    public CustomCircleProgressBarView(Context context) {
        this(context, null);
    }

    public CustomCircleProgressBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCircleProgressBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomCircleProgressBarView, defStyleAttr, 0);
        mFirstColor = ta.getColor(R.styleable.CustomCircleProgressBarView_firstColor, Color.GREEN);
        mSecondColor = ta.getColor(R.styleable.CustomCircleProgressBarView_secondColor, Color.RED);
        mCircleWidth = ta.getDimensionPixelSize(R.styleable.CustomCircleProgressBarView_circleWidth,(int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
        mSpeed = ta.getInt(R.styleable.CustomCircleProgressBarView_speed, 20);
        ta.recycle();
        mPaint = new Paint();
        //绘图线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    mProgress++;
                    if(mProgress == 360){
                        mProgress = 0;
                        if(!isNext)
                            isNext = true;
                        else
                            isNext = false;
                    }
                    postInvalidate();
                    try{
                        Thread.sleep(mSpeed);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth()/2;      //获取圆心的x坐标
        int radius = center - mCircleWidth/2;  //半径
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF oval = new RectF(center-radius, center- radius, center+radius, center+radius);   //用于定义的圆弧的形状和大小的界限
        if(!isNext){
            //第一颜色的圈完整，第二颜色跑
            mPaint.setColor(mFirstColor);  //设置圆环的颜色
            canvas.drawCircle(center, center,radius, mPaint); //画圆环
            mPaint.setColor(mSecondColor);
            canvas.drawArc(oval, -90, mProgress, false, mPaint);  //根据进度画圆弧

        }else{

            mPaint.setColor(mSecondColor);
            canvas.drawCircle(center, center,radius, mPaint); //画圆环
            mPaint.setColor(mFirstColor);  //设置圆环的颜色
            canvas.drawArc(oval, -90, mProgress, false, mPaint);  //根据进度画圆弧
        }
    }
}
