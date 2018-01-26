package com.axp.axpseller.model;

import android.content.Context;

import com.axp.axpseller.view.IRechargeScoreOnlineFgListenner;

/**
 * Created by YY on 2018/1/2.
 */
public interface IRechargeScoreOnlineFgData {
    void getData(Context context, IRechargeScoreOnlineFgListenner listenner);
}
