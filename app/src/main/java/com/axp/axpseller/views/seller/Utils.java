package com.axp.axpseller.views.seller;


import android.content.Context;

import com.axp.axpseller.Application;

public class Utils {
	/**
	 * ��dipת��px
	 * 
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(float dpValue, Context context) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5F);
	}
}
