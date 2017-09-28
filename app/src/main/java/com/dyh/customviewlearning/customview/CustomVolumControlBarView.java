package com.dyh.customviewlearning.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.dyh.customviewlearning.R;
import com.dyh.customviewlearning.util.DpAndSpChangeUtil;

/**
 * Created by dyh on 2017/9/27.
 */

public class CustomVolumControlBarView extends View {
    //第一圈的颜色
    private int mFirstColor;
    //第二圈的颜色
    private int mSeconColor;
    //圈的宽度
    private int mCircleWidth;

    private Paint mPaint;

    //当前进度
    private int mCurrentCount = 3;
    //中间的图片
    private Bitmap mImage;
    //每个块块间的间隙
    private int mSplitSize;
    //个数
    private int mCount;
    private Rect mRect;

    public CustomVolumControlBarView(Context context) {
        this(context, null);
    }

    public CustomVolumControlBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVolumControlBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta  = context.obtainStyledAttributes(attrs, R.styleable.CustomVolumControlBar, defStyleAttr, 0);
        int n = ta.getIndexCount();
        for(int i = 0; i < n; i++){
            int attr = ta.getIndex(i);
            switch(attr){
                case R.styleable.CustomVolumControlBar_firstColor:
                    mFirstColor = ta.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CustomVolumControlBar_secondColor:
                    mSeconColor = ta.getColor(attr, Color.CYAN);
                    break;
                case R.styleable.CustomVolumControlBar_bg:
                    mImage = BitmapFactory.decodeResource(getResources(), ta.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomVolumControlBar_circleWidth:
                    mCircleWidth = ta.getDimensionPixelSize(attr, DpAndSpChangeUtil.sp2px(20, context));
                    break;
                case R.styleable.CustomVolumControlBar_dotCount:
                    mCount = ta.getInt(attr, 20);
                    break;
                case R.styleable.CustomVolumControlBar_splitSize:
                    mSplitSize = ta.getInt(attr, 20);
                    break;
            }
        }

        ta.recycle();
        mPaint = new Paint();
        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);     //设置线段端点形状为圆头
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        int center = getWidth()/2;
        int radius = center - mCircleWidth/2;

        drawOval(canvas, center, radius);

        //计算内切正方形的位置
        int relRadius = radius - mCircleWidth/2;
        mRect.left = (int)(relRadius-Math.sqrt(2)*1.0f/2*relRadius)+mCircleWidth;
        mRect.top = (int)(relRadius - Math.sqrt(2)*1.0f/2*relRadius)+mCircleWidth;
        mRect.right = (int)(mRect.left+Math.sqrt(2)*relRadius);
        mRect.bottom = (int)(mRect.left+Math.sqrt(2)*relRadius);

        //如果图片比较小，那么根据图片的尺寸放到正中心
        if(mImage.getWidth() < Math.sqrt(2) * relRadius){
            mRect.left =(int)(mRect.left + Math.sqrt(2)*relRadius*1.0f/2 - mImage.getWidth()*1.0f/2);
            mRect.top = (int)(mRect.top + Math.sqrt(2)*relRadius*1.0f/2-mImage.getWidth()*1.0f/2);
            mRect.right = (int)(mRect.left + mImage.getWidth());
            mRect.bottom = (int)(mRect.top+mImage.getHeight());
        }
        canvas.drawBitmap(mImage, null, mRect, mPaint);
    }

    /**
     * 画块块
     * @param canvas
     * @param center
     * @param radius
     */
    private void drawOval(Canvas canvas, int center, int radius) {
        //根据需要画的个数以及间隙计算每个块所占的比例*360
        float itemSize = (360*1.0f-mCount*mSplitSize)/mCount;
        //定义圆弧的形状和大小的界限
        RectF oval = new RectF(center - radius, center-radius, center+radius, center+radius);
        mPaint.setColor(mFirstColor);
        for(int i = 0; i < mCount; i++){
            canvas.drawArc(oval, i*(itemSize+mSplitSize), itemSize, false, mPaint);

        }
        mPaint.setColor(mSeconColor);
        for(int i = 0; i < mCurrentCount; i++){
            canvas.drawArc(oval, i*(itemSize+mSplitSize), itemSize, false, mPaint);
        }
    }

    public void up(){
        mCurrentCount++;
        postInvalidate();
    }
    public void down(){
        mCurrentCount--;
        postInvalidate();
    }
    private int xDown, xUp;

    /**
     * 触摸事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xDown = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                xUp = (int)event.getY();
                if(xUp > xDown)//下滑
                {
                    down();
                }else{
                    up();
                }
                break;
        }
        return true;
    }
}
