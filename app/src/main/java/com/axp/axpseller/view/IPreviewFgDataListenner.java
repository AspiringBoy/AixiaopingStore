package com.axp.axpseller.view;

import com.axp.axpseller.core.RecyclerViewModel;
import com.axp.axpseller.network.DataList;

/**
 * Created by YY on 2018/1/2.
 */
public interface IPreviewFgDataListenner {
    void onSuccess(DataList<RecyclerViewModel> dataList);

    void onFailure(String errMsg);
}
