package com.dyh.customviewlearning.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by dyh on 2017/9/18.
 */

public class DpAndSpChangeUtil {
    public static int dp2px(int dp, Context context){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(int sp, Context context){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp,context.getResources().getDisplayMetrics());
    }

}
