package com.axp.axpseller.models;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dong on 2016/12/7.
 */
public class PayTypeModels extends BaseModel{

    @SerializedName("payType")
    private String payType;

    @SerializedName("total")
    private String total;

    @SerializedName("adminuserId")
    private String adminuserId;

    public String getPayType() { return payType; }

    public void setPayType(String payType) { this.payType = payType; }

    public String getTotal() { return total; }

    public void setTotal(String total) { this.total = total; }

    public String getAdminuserId() { return adminuserId; }

    public void setAdminuserId(String adminuserId) { this.adminuserId = adminuserId; }
}
