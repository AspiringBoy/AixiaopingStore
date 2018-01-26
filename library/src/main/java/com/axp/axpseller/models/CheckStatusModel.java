package com.axp.axpseller.models;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2017/8/10.
 */
public class CheckStatusModel extends BaseModel {

    @SerializedName("adminuserId")
    String adminuserId;

    @SerializedName("sellerId")
    String sellerId;

    @SerializedName("userId")
    String userId;

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public String getAdminuserId() {
        return adminuserId;
    }

    public void setAdminuserId(String adminuserId) {
        this.adminuserId = adminuserId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @SerializedName("verifyStatus")
    String verifyStatus;
}
