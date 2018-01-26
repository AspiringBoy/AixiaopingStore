package com.axp.axpseller.views.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Dong on 2016/7/24.
 */
public class SuperGridView extends GridView{
    public SuperGridView(Context context, AttributeSet attrs) {
            super(context, attrs);

           }

    public SuperGridView(Context context) {
              super(context);

           }

    public SuperGridView(Context context, AttributeSet attrs, int defStyle) {
              super(context, attrs, defStyle);

            }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

               int expandSpec = MeasureSpec.makeMeasureSpec(
                                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
              super.onMeasure(widthMeasureSpec, expandSpec);
          }

}
