package com.axp.axpseller.utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

/**
 * Created by YY on 2017/11/30.
 */
public class ScreenConfigUtil {
    public static void setBackgroundAlpha(Context mContext, float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity)mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity)mContext).getWindow().setAttributes(lp);
    }
}
