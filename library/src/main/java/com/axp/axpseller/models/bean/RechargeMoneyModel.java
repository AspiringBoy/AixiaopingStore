package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2018/1/9.
 */
public class RechargeMoneyModel extends BaseModel {

    //充值金额
    @SerializedName("rechargeMoney")
    String rechargeMoney;

    //获得积分
    @SerializedName("scoreReceive")
    int rechargeScore;

    public String getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(String rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public int getRechargeScore() {
        return rechargeScore;
    }

    public void setRechargeScore(int rechargeScore) {
        this.rechargeScore = rechargeScore;
    }
}
