package com.dyh.customviewlearning.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dyh on 2017/9/12.
 */

public class CustomBaseView extends View {

    //创建一个画笔
    private Paint mPaint;

    public CustomBaseView(Context context) {
        super(context);
    }

    public CustomBaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    //初始化画笔
    private void initPaint(){
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL); //设置画笔模式为填充
        mPaint.setStrokeWidth(5f);        //设置画笔宽度为10px
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制点
        canvas.drawPoint(2, 2, mPaint);  //在坐标（200，200）处画一个点
        canvas.drawPoints(new float[]{       //绘制一组点
                10,2,
                10,10,
                10,20
        }, mPaint);

        //绘制直线
        canvas.drawLine(100,10,150,50, mPaint); // 在坐标(300,300)(500,600)之间绘制一条直线
        canvas.drawLines(new float[]{              // 绘制一组线 每四数字(两个点的坐标)确定一条线
                40,10,80,10,
                40,30,80,30
        }, mPaint);

        //绘制矩形,三种画法都是一样的结果
        //第一种画法
        canvas.drawRect(160, 10, 260, 40, mPaint);

        //第二中画法
        Rect rect = new Rect(160,10,260,40);
        canvas.drawRect(rect, mPaint);
        //第三种画法
        RectF rectF = new RectF(160,10,260,40);
        canvas.drawRect(rectF, mPaint);

        //绘制圆角矩形
        RectF rectF1 = new RectF(320,10,380,50);
        //30,30是椭圆的半径
        canvas.drawRoundRect(rectF1, 30,30, mPaint);

        //绘制椭圆
        RectF rectF2 = new RectF(100,50, 200,100);
        canvas.drawOval(rectF2, mPaint);

        //绘制圆形
        canvas.drawCircle(200,200, 100, mPaint);

        //绘制圆弧
        RectF rectF3 = new RectF(100,320, 300, 400);
        //绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF3, mPaint);
        //绘制圆弧
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF3, 0, 90, false, mPaint);

        RectF rectF4 = new RectF(100,420, 300, 500);
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF4, mPaint);
        //绘制圆弧
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF4, 0, 90, true, mPaint);
    }
}
