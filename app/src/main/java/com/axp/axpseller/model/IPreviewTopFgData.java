package com.axp.axpseller.model;

import android.content.Context;

import com.axp.axpseller.view.IPreviewFgDataListenner;

/**
 * Created by YY on 2018/1/2.
 */
public interface IPreviewTopFgData {
    void getGoodData(Context context,String goodId, IPreviewFgDataListenner listenner);
}
