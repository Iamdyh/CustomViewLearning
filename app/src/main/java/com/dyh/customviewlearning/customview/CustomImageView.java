package com.dyh.customviewlearning.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dyh.customviewlearning.R;

/**
 * Created by dyh on 2017/9/18.
 */

public class CustomImageView extends View {

    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;



    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.CustomImageView, defStyleAttr,0);

    }
}
