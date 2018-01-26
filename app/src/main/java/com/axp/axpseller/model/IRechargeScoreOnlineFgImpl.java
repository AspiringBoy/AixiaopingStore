package com.axp.axpseller.model;

import android.content.Context;

import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.bean.RechargeScoreOnlineModel;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.view.IRechargeScoreOnlineFgListenner;

/**
 * Created by YY on 2018/1/2.
 */
public class IRechargeScoreOnlineFgImpl implements IRechargeScoreOnlineFgData{

    @Override
    public void getData(Context context, IRechargeScoreOnlineFgListenner listenner) {
        Request request = new Request();
        RXUtils.request(context,request,"rechargeOnLine", new SupportSubscriber<Response<RechargeScoreOnlineModel>>() {
            @Override
            public void onNext(Response<RechargeScoreOnlineModel> rechargeScoreOnlineModelResponse) {
                listenner.onSuccess(rechargeScoreOnlineModelResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                listenner.onFailure("数据获取失败");
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                listenner.onFailure(response.getMessage());
            }
        });
    }
}
