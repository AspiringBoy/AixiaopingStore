package com.axp.axpseller.presenter;

import android.content.Context;

import com.axp.axpseller.core.RecyclerViewModel;
import com.axp.axpseller.model.IMyScoreFgData;
import com.axp.axpseller.model.IMyScoreFgDataImpl;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.view.IPreviewFgDataListenner;
import com.axp.axpseller.view.IShowPreviewTopFg;

/**
 * Created by YY on 2018/1/10.
 */
public class MyScorePresenter {
    private IShowPreviewTopFg iShowMyScoreFg;
    private IMyScoreFgData iMyScoreFgData;
    private Context context;

    public MyScorePresenter(IShowPreviewTopFg iShowMyScoreFg) {
        this.iShowMyScoreFg = iShowMyScoreFg;
        iMyScoreFgData = new IMyScoreFgDataImpl();
    }

    public void showFgView(Context context){
        this.context = context;
        iShowMyScoreFg.initView();
        iShowMyScoreFg.initData();
        /*iMyScoreFgData.getData(context, new IPreviewFgDataListenner() {
            @Override
            public void onSuccess(DataList<RecyclerViewModel> dataList) {
                iShowMyScoreFg.showView(dataList);
            }

            @Override
            public void onFailure(String errMsg) {

            }
        });*/
    }

    public void refreshData(){
        iMyScoreFgData.refreshData(context, new IPreviewFgDataListenner() {
            @Override
            public void onSuccess(DataList<RecyclerViewModel> dataList) {
                iShowMyScoreFg.showView(dataList);
            }

            @Override
            public void onFailure(String errMsg) {

            }
        });
    }

    public void loadMore(){
        iMyScoreFgData.loadMore(context, new IPreviewFgDataListenner() {
            @Override
            public void onSuccess(DataList<RecyclerViewModel> dataList) {
                iShowMyScoreFg.showView(dataList);
            }

            @Override
            public void onFailure(String errMsg) {

            }
        });
    }
}
