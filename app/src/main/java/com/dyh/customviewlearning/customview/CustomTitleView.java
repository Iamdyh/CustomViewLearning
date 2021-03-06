package com.dyh.customviewlearning.customview;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.dyh.customviewlearning.R;
import com.dyh.customviewlearning.util.DpAndSpChangeUtil;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by dyh on 2017/9/12.
 */

public class CustomTitleView extends View{

    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;

    //绘制时控制文本绘制的范围
    private Rect mBound;
    private Paint mPaint;

    private static final String TAG = "CustomTitleView";

    /**
     * 一般在直接new一个View的时候调用
     * @param context
     */
    public CustomTitleView(Context context) {
        
        this(context, null);

    }

    /**
     * 一般在layout文件中使用的时候会调用，关于它的所有属性(包括自定义属性)都会包含在attrs中传递进来。
     * @param context
     * @param attrs
     */
    public CustomTitleView(Context context, @Nullable AttributeSet attrs) {
        //默认的布局文件调用的是两个参数的构造方法，所以记得让所有的构造调用我们的三个参数的构造，
        // 我们在三个参数的构造中获得自定义属性。
        this(context, attrs, 0);

    }

    /**
     * 获得自定义的样式属性
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustomTitleView(Context context, @Nullable AttributeSet attrs, @Nullable int defStyle){
        super(context, attrs, defStyle);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyle, 0);
        int n = ta.getIndexCount();
        //遍历ta,获取自定义属性
        for(int i = 0; i < n; i++){
            int attr = ta.getIndex(i);
            switch (attr){
                case R.styleable.CustomTitleView_titleText:
                    mTitleText = ta.getString(attr);
                    break;
                case R.styleable.CustomTitleView_titleTextColor:
                    //默认设置为黑色
                    mTitleTextColor = ta.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomTitleView_titleTextSize:
                    //设置默认为16sp, TypedValue也可以把sp转化为px
                    mTitleTextSize = ta.getDimensionPixelOffset(attr, DpAndSpChangeUtil.sp2px(16, context));
                    break;
            }

        }
        //回收资源
        ta.recycle();
        //另一种获取自定义属性的方式
            /*TypedArray  a = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyle, 0);
            mTitleText = a.getString(R.styleable.CustomTitleView_titleText);
            mTitleTextColor = a.getColor(R.styleable.CustomTitleView_titleTextColor, Color.BLACK);
            mTitleTextSize = a.getDimensionPixelOffset(R.styleable.CustomTitleView_titleTextSize,
                    (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
            a.recycle();*/
        init();

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mTitleText = randomText();
                postInvalidate();   //用在子线程向UI线程发送界面刷新消息请求
            }
        });
    }
    private String randomText(){
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while(set.size()<4){
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i:set) {
            sb.append("" + i);
        }
        return sb.toString();
    }


    private void init(){
        //获得绘制文本的宽和高
        mPaint = new Paint();
        //设置画笔的字体大小
        mPaint.setTextSize(mTitleTextSize);
        //设置线宽，float型，如2.5f，默认绘文本无需设置（默认值好像为0），但假如设置了，再绘制文本的时候一定要恢复到0
        //mPaint.setStrokeWidth(0);
        mBound = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
    }

    /**
     * 测量View的大小
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int width;
        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else{
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(),mBound);
            float textWidth = mBound.width();
            int desired = (int)(getPaddingLeft()+ textWidth + getPaddingRight());
            width = desired;
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height;
        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else{
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int)(getPaddingBottom() + textHeight + getPaddingTop());
            height = desired;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTitleTextColor);
        canvas.drawText(mTitleText, getWidth()/2- mBound.width()/2, getHeight()/2 + mBound.height()/2, mPaint);
    }
}
