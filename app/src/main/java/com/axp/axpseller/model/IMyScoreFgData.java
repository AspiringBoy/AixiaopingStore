package com.axp.axpseller.model;

import android.content.Context;

import com.axp.axpseller.view.IPreviewFgDataListenner;

/**
 * Created by YY on 2018/1/2.
 */
public interface IMyScoreFgData {
    void getData(Context context, IPreviewFgDataListenner listenner);

    void refreshData(Context context, IPreviewFgDataListenner listenner);

    void loadMore(Context context, IPreviewFgDataListenner listenner);
}
