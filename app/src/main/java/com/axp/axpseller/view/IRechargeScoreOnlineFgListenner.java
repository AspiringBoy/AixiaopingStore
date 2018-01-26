package com.axp.axpseller.view;

import com.axp.axpseller.models.bean.RechargeScoreOnlineModel;

/**
 * Created by YY on 2018/1/9.
 */
public interface IRechargeScoreOnlineFgListenner {
    void onSuccess(RechargeScoreOnlineModel rechargeScoreOnlineModel);

    void onFailure(String errMsg);
}
