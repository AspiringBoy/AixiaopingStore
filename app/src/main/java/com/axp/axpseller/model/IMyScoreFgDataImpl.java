package com.axp.axpseller.model;

import android.content.Context;

import com.axp.axpseller.core.RecyclerViewModel;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.bean.MyScoreListModel;
import com.axp.axpseller.models.bean.MyScoreResModel;
import com.axp.axpseller.models.bean.RechargeScoreIsPartnerModel;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.view.IPreviewFgDataListenner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2018/1/2.
 */
public class IMyScoreFgDataImpl implements IMyScoreFgData{

    private DataList<RecyclerViewModel> mDataList = new DataList<>();
    private ArrayList<RecyclerViewModel> mList = new ArrayList<>();
    private int pageIndex = 1;
    private int loadType = 1;

    @Override
    public void getData(Context context, IPreviewFgDataListenner listenner) {
        Request<MyScoreResModel> request = new Request();
        MyScoreResModel resModel = new MyScoreResModel();
        resModel.setPageIndex(pageIndex);
        request.setData(resModel);
        RXUtils.request(context, request,"getIntegralList", new SupportSubscriber<Response<MyScoreResModel>>() {
            @Override
            public void onNext(Response<MyScoreResModel> myScoreResModelResponse) {
                handleResData(myScoreResModelResponse);
                listenner.onSuccess(mDataList);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
            }
        });
    }

    @Override
    public void refreshData(Context context,IPreviewFgDataListenner listenner){
        pageIndex = 1;
        mList.clear();
        loadType = 1;
        getData(context,listenner);
    }

    @Override
    public void loadMore(Context context,IPreviewFgDataListenner listenner){
        pageIndex++;
        loadType = 2;
        getData(context,listenner);
    }


    private void handleResData(Response<MyScoreResModel> myScoreResModelResponse) {
        if (loadType == 1) {
            //头部view
            RecyclerViewModel headerModel = new RecyclerViewModel();
            RechargeScoreIsPartnerModel partnerModel = new RechargeScoreIsPartnerModel();
            headerModel.setItemType(0);
            String totalScore = myScoreResModelResponse.getData().getTotalScore();
            partnerModel.setTotalScore(totalScore);
            partnerModel.setCareerPartner(myScoreResModelResponse.getData().getPartner().isCareerPartner());
            partnerModel.setDesc(myScoreResModelResponse.getData().getPartner().getDesc());
            headerModel.setData(partnerModel);
            mList.add(headerModel);
        }

        //积分列表
        List<MyScoreListModel> scoreMap = myScoreResModelResponse.getData().getScoreMap();
        if (scoreMap != null && scoreMap.size() > 0) {
            for (MyScoreListModel model : scoreMap) {
                RecyclerViewModel scoreListModel = new RecyclerViewModel();
                scoreListModel.setItemType(1);
                scoreListModel.setData(model);
                mList.add(scoreListModel);
            }
        }
        mDataList.setDataList(mList);
        mDataList.setPageIndex(pageIndex);
        mDataList.setPageSize(myScoreResModelResponse.getData().getPageSize());
    }
}
