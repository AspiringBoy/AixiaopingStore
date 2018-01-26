package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YY on 2018/1/11.
 */
public class SendScoreDtResModel extends BaseModel {

    //收取者列表

    public List<SendScoreDtListModel> getUsersInfos() {
        return usersInfos;
    }

    public void setUsersInfos(List<SendScoreDtListModel> usersInfos) {
        this.usersInfos = usersInfos;
    }

    public String getScoreBanlance() {
        return scoreBanlance;
    }

    public void setScoreBanlance(String scoreBanlance) {
        this.scoreBanlance = scoreBanlance;
    }

    @SerializedName("usersInfos")
    List<SendScoreDtListModel> usersInfos;

    //积分余额
    @SerializedName("scoreBanlance")
    String scoreBanlance;

    public String getPresenterName() {
        return presenterName;
    }

    public void setPresenterName(String presenterName) {
        this.presenterName = presenterName;
    }

    private String presenterName;

    private String presenterId;

    public String getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(String scoreNum) {
        this.scoreNum = scoreNum;
    }

    public String getPresenterId() {
        return presenterId;
    }

    public void setPresenterId(String presenterId) {
        this.presenterId = presenterId;
    }

    private String scoreNum;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;
}
