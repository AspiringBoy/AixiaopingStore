package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2018/1/10.
 */
public class MyScoreListModel extends BaseModel {

    //单笔积分
    @SerializedName("detailScore")
    int detailScore;

    //积分消费说明
    @SerializedName("datailRemark")
    String datailRemark;

    //积分消费时间

    public String getDetailTime() {
        return detailTime;
    }

    public void setDetailTime(String detailTime) {
        this.detailTime = detailTime;
    }

    public int getDetailScore() {
        return detailScore;
    }

    public void setDetailScore(int detailScore) {
        this.detailScore = detailScore;
    }

    public String getDatailRemark() {
        return datailRemark;
    }

    public void setDatailRemark(String datailRemark) {
        this.datailRemark = datailRemark;
    }

    @SerializedName("detailTime")
    String detailTime;
}
