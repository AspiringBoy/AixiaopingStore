package com.axp.axpseller.models;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dong on 2017/3/1.
 */
public class UpdateStoreInfo extends BaseModel{

    @SerializedName("message")
    private String message;
    @SerializedName("userId")
    private int userId;
    @SerializedName("tips")
    private String tips;
    @SerializedName("remind")
    private String remind;
    @SerializedName("verifyStatus")
    private int verifyStatus;

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public String getTips() { return tips; }

    public void setTips(String tips) { this.tips = tips; }

    public String getRemind() { return remind; }

    public void setRemind(String remind) { this.remind = remind; }

    public int getVerifyStatus() { return verifyStatus; }

    public void setVerifyStatus(int verifyStatus) { this.verifyStatus = verifyStatus; }
}
