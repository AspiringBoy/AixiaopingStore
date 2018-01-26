package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2018/1/11.
 */
public class SendScoreDtListModel extends BaseModel {
    public String getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(String scoreNum) {
        this.scoreNum = scoreNum;
    }

    private String scoreNum;

    public String getPresenterName() {
        return presenterName;
    }

    public void setPresenterName(String presenterName) {
        this.presenterName = presenterName;
    }

    private String presenterName;

    //(1:代表爱小屏用户;2:代表爱小屏商家)
    @SerializedName("presenterType")
    String presenterType;

    //收取者id
    @SerializedName("presenterId")
    String presenterId;

    //收取者账号

    public String getPresenterPhone() {
        return presenterPhone;
    }

    public void setPresenterPhone(String presenterPhone) {
        this.presenterPhone = presenterPhone;
    }

    public String getPresenterType() {
        return presenterType;
    }

    public void setPresenterType(String presenterType) {
        this.presenterType = presenterType;
    }

    public String getPresenterId() {
        return presenterId;
    }

    public void setPresenterId(String presenterId) {
        this.presenterId = presenterId;
    }

    public String getPresenterNickName() {
        return presenterNickName;
    }

    public void setPresenterNickName(String presenterNickName) {
        this.presenterNickName = presenterNickName;
    }

    public String getScoreDuration() {
        return scoreDuration;
    }

    public void setScoreDuration(String scoreDuration) {
        this.scoreDuration = scoreDuration;
    }

    @SerializedName("presenterPhone")
    String presenterPhone;

    //昵称
    @SerializedName("presenterNickName")
    String presenterNickName;

    //积分有效期
    @SerializedName("scoreDuration")
    String scoreDuration;

}
