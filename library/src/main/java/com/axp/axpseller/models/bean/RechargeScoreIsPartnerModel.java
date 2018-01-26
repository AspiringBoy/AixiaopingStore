package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2018/1/13.
 */
public class RechargeScoreIsPartnerModel extends BaseModel {

    @SerializedName("isCareerPartner")
    boolean isCareerPartner;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isCareerPartner() {
        return isCareerPartner;
    }

    public void setCareerPartner(boolean careerPartner) {
        isCareerPartner = careerPartner;
    }

    @SerializedName("desc")
    String desc;

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    @SerializedName("totalScore")
    String totalScore;
}
