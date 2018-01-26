package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YY on 2018/1/9.
 */
public class RechargeScoreOnlineModel extends BaseModel {

    //余额
    @SerializedName("scoreBanlance")
    String scoreBanlance;

    //金额换取积分比例
    @SerializedName("scoreProportion")
    String scoreProportion;

    public String getActiveImg() {
        return activeImg;
    }

    public void setActiveImg(String activeImg) {
        this.activeImg = activeImg;
    }

    public String getScoreBanlance() {
        return scoreBanlance;
    }

    public void setScoreBanlance(String scoreBanlance) {
        this.scoreBanlance = scoreBanlance;
    }

    public String getScoreProportion() {
        return scoreProportion;
    }

    public void setScoreProportion(String scoreProportion) {
        this.scoreProportion = scoreProportion;
    }

    public List<RechargeMoneyModel> getScoreRechargeList() {
        return scoreRechargeList;
    }

    public void setScoreRechargeList(List<RechargeMoneyModel> scoreRechargeList) {
        this.scoreRechargeList = scoreRechargeList;
    }

    //底部活动图
    @SerializedName("activeImg")
    String activeImg;

    //充值金额列表
    @SerializedName("rechargeMoneyItems")
    List<RechargeMoneyModel> scoreRechargeList;
}
