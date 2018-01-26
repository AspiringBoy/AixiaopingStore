package com.axp.axpseller.presenter;

import android.content.Context;

import com.axp.axpseller.model.IRechargeScoreOnlineFgData;
import com.axp.axpseller.model.IRechargeScoreOnlineFgImpl;
import com.axp.axpseller.models.bean.RechargeScoreOnlineModel;
import com.axp.axpseller.view.IRechargeScoreOnlineFgListenner;
import com.axp.axpseller.view.IShowScoreRechargeFg;

/**
 * Created by YY on 2018/1/9.
 */
public class RechargeOnlineFgPresenter {
    private IShowScoreRechargeFg iShowScoreRechargeFg;
    private IRechargeScoreOnlineFgData iRechargeScoreOnlineFgData;

    public RechargeOnlineFgPresenter(IShowScoreRechargeFg iShowScoreRechargeFg) {
        this.iShowScoreRechargeFg = iShowScoreRechargeFg;
        iRechargeScoreOnlineFgData = new IRechargeScoreOnlineFgImpl();
    }

    public void showFgView(Context context){
        iShowScoreRechargeFg.initView();
        iShowScoreRechargeFg.initData();
        iRechargeScoreOnlineFgData.getData(context, new IRechargeScoreOnlineFgListenner() {
            @Override
            public void onSuccess(RechargeScoreOnlineModel rechargeScoreOnlineModel) {
                iShowScoreRechargeFg.oprateView(rechargeScoreOnlineModel);
            }

            @Override
            public void onFailure(String errMsg) {

            }
        });
    }

}
