package com.dyh.customviewlearning.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.dyh.customviewlearning.bean.PieData;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by dyh on 2017/9/12.
 */

public class CustomPieView extends View {
    // 颜色表 (注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    // 饼状图初始绘制角度
    private float mStartAngle = 0;
    // 数据
    private ArrayList<PieData> mData;
    // 宽高
    private int mWidth, mHeight;
    // 画笔
    private Paint mPaint = new Paint();
    //文字画笔
    private Paint mTextPaint;
    //容纳文字最小角度
    private int minAngle = 30;
    //格式化小数点
    private DecimalFormat dff;

    //半径
    private float mRadius;

    public int getMinAngle() {
        return minAngle;
    }

    public void setMinAngle(int minAngle) {
        this.minAngle = minAngle;
    }







    public CustomPieView(Context context) {
        this(context, null);
    }

    public CustomPieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint.setStyle(Paint.Style.FILL);
        //设置抗锯齿
        mPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        dff = new DecimalFormat("0.0");
    }

    //确定View大小
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(null == mData)
            return;
        float currentStartAngle = mStartAngle;
        canvas.translate(mWidth/2, mHeight/2);
        mRadius = (float)(Math.min(mWidth, mHeight)/2 * 0.8);
        RectF rectF = new RectF(-mRadius, -mRadius, mRadius, mRadius);

        for(int i = 0; i < mData.size(); i++){
            PieData pie = mData.get(i);
            mPaint.setColor(pie.getColor());

            canvas.drawArc(rectF, currentStartAngle, pie.getAngle(), true, mPaint);

            String drawAngle = dff.format(pie.getAngle()/360 * 100);
            String name = pie.getName()+"," + drawAngle+"%";
            float textAngle = pie.getAngle()/2 + currentStartAngle;
            drawText(canvas, textAngle, name, pie.getAngle());


            currentStartAngle += pie.getAngle();
        }
    }

    /**
     * 画文字
     * 当某个扇形角度，不够文字的宽度时，我们就会将文字画在圆的外面。
     * 所以这个我们得添加一个属性minAngle,角度最小值，添加getter和setter方法，让使用者去设置。
     * 当needDrawAngle小于这个角度值时，就画在外面。
     * 大于这个值就画在扇形中央。
     * @param canvas
     * @param textAngle
     * @param name
     * @param needDrawAngle
     */
    private void drawText(Canvas canvas, float textAngle, String name, float needDrawAngle){
        Rect rect = new Rect();
        mTextPaint.setTextSize(sp2px(15));
        mTextPaint.getTextBounds(name, 0, name.length(), rect);
        if(textAngle >= 0 && textAngle <= 90){//画布坐标系第一象限(数学坐标系第四象限)
            if(needDrawAngle < minAngle){//如果小于某个度数,就把文字画在饼状图外面
                canvas.drawText(name,(float)(mRadius * 1.2 * Math.cos(Math.toRadians(textAngle))),
                        (float)(mRadius * 1.2 * Math.sin(Math.toRadians(textAngle)))+ rect.height()/2, mTextPaint);
            }else{
                canvas.drawText(name,(float)(mRadius * 0.75 * Math.cos(Math.toRadians(textAngle))),
                        (float)(mRadius * 0.75 * Math.sin(Math.toRadians(textAngle)))+ rect.height()/2, mTextPaint);
            }
        }else if(textAngle > 90 && textAngle <= 180){//画布坐标系第二象限(数学坐标系第三象限)
            if(needDrawAngle < minAngle){//如果小于某个度数,就把文字画在饼状图外面
                canvas.drawText(name,(float)(-mRadius * 1.2 * Math.cos(Math.toRadians(180-textAngle))),
                        (float)(mRadius * 1.2 * Math.sin(Math.toRadians(180-textAngle)))+ rect.height()/2, mTextPaint);
            }else{
                canvas.drawText(name,(float)(-mRadius * 0.75 * Math.cos(Math.toRadians(180-textAngle))),
                        (float)(mRadius * 0.75 * Math.sin(Math.toRadians(180-textAngle)))+ rect.height()/2, mTextPaint);
            }
        }else if(textAngle > 180 && textAngle <= 270){//画布坐标系第三象限(数学坐标系第二象限)
            if(needDrawAngle < minAngle){//如果小于某个度数,就把文字画在饼状图外面
                canvas.drawText(name,(float)(-mRadius * 1.2 * Math.cos(Math.toRadians(textAngle - 180))),
                        (float)(-mRadius * 1.2 * Math.sin(Math.toRadians(textAngle - 180)))+ rect.height()/2, mTextPaint);
            }else{
                canvas.drawText(name,(float)(-mRadius * 0.75 * Math.cos(Math.toRadians(textAngle - 180))),
                        (float)(-mRadius * 0.75 * Math.sin(Math.toRadians(textAngle - 180)))+ rect.height()/2, mTextPaint);
            }
        }else{//画布坐标系第四象限(数学坐标系第一象限)
            if(needDrawAngle < minAngle){//如果小于某个度数,就把文字画在饼状图外面
                canvas.drawText(name,(float)(mRadius * 1.2 * Math.cos(Math.toRadians(360-textAngle))),
                        (float)(-mRadius * 1.2 * Math.sin(Math.toRadians(360-textAngle)))+ rect.height()/2, mTextPaint);
            }else{
                canvas.drawText(name,(float)(mRadius * 0.75 * Math.cos(Math.toRadians(360-textAngle))),
                        (float)(-mRadius * 0.75 * Math.sin(Math.toRadians(360-textAngle)))+ rect.height()/2, mTextPaint);
            }
        }
    }



    /**
     * 设置起始角度
     * @param mStartAngle
     */
    public void setStartAngle(int mStartAngle){
        this.mStartAngle = mStartAngle;
        invalidate();       //刷新
    }

    /**
     * 设置数据
     * @param mData
     */
    public void setData(ArrayList<PieData>  mData){
        this.mData = mData;
        initData(mData);
        invalidate();     //刷新
    }

    /**
     * 初始化数据
     * @param mData
     */
    private void initData(ArrayList<PieData> mData){
        if(mData == null || mData.size() == 0)
            return;
        float sumValue = 0;
        for(int i = 0; i < mData.size(); i++){
            PieData pie = mData.get(i);
            sumValue += pie.getValue();
            int j = i % mColors.length;
            pie.setColor(mColors[j]);
        }

        float sumAngle = 0;
        for(int i = 0; i < mData.size(); i++){
            PieData pie = mData.get(i);
            float percentage = pie.getValue()/sumValue;
            float angle = percentage*360;
            pie.setPercentage(percentage);
            pie.setAngle(angle);

            sumAngle += angle;
            Log.i("angle", "initData: " + pie.getAngle());
        }
    }

    private int dp2px(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                dp, getResources().getDisplayMetrics());
    }

    private int sp2px(int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp, getResources().getDisplayMetrics());
    }
}
