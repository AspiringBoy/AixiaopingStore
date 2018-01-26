package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YY on 2018/1/10.
 */
public class MyScoreResModel extends BaseModel {

    @SerializedName("pageSize")
    int pageSize;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    @SerializedName("pageIndex")
    int pageIndex;

    //账户总积分
    @SerializedName("totalScore")
    String totalScore;

    //判断是否是合伙人

    public RechargeScoreIsPartnerModel getPartner() {
        return partner;
    }

    public void setPartner(RechargeScoreIsPartnerModel partner) {
        this.partner = partner;
    }

    @SerializedName("partner")
    RechargeScoreIsPartnerModel partner;

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @SerializedName("scoreMap")
    List<MyScoreListModel> scoreMap;

    public List<MyScoreListModel> getScoreMap() {
        return scoreMap;
    }

    public void setScoreMap(List<MyScoreListModel> scoreMap) {
        this.scoreMap = scoreMap;
    }
}
