package com.axp.axpseller.views.adapters;

import android.widget.BaseAdapter;

/**
 * Created by xu on 2016/6/3.
 * 一个简化的Adapter
 */
public abstract class SimpleAdpater extends BaseAdapter {

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
