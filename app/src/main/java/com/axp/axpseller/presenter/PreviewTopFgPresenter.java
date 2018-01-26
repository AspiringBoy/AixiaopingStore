package com.axp.axpseller.presenter;

import android.content.Context;

import com.axp.axpseller.core.RecyclerViewModel;
import com.axp.axpseller.model.IPreviewTopFgData;
import com.axp.axpseller.model.IPreviewTopFgDataImpl;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.view.IPreviewFgDataListenner;
import com.axp.axpseller.view.IShowPreviewTopFg;

/**
 * Created by YY on 2018/1/2.
 */
public class PreviewTopFgPresenter {
    private final Context mContext;
    private IShowPreviewTopFg iShowPreviewTopFg;
    private IPreviewTopFgData iPreviewTopFgData;

    public PreviewTopFgPresenter(Context context, String goodId, IShowPreviewTopFg iShowPreviewTopFg) {
        mContext = context;
        this.iShowPreviewTopFg = iShowPreviewTopFg;
        iPreviewTopFgData = new IPreviewTopFgDataImpl();
    }

    public void showViewData(String goodId){
        iShowPreviewTopFg.initView();
        iShowPreviewTopFg.initData();
        iPreviewTopFgData.getGoodData(mContext, goodId, new IPreviewFgDataListenner() {
            @Override
            public void onSuccess(DataList<RecyclerViewModel> dataList) {
                iShowPreviewTopFg.showView(dataList);
            }

            @Override
            public void onFailure(String errMsg) {

            }
        });
    }
}
