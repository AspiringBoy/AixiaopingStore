package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;

/**
 * Created by YY on 2018/1/12.
 */
public class PayBean extends BaseModel {

    private double rechargeMoney;
    private String payType;
    private String adminUserId;

    public double getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(double rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(String adminUserId) {
        this.adminUserId = adminUserId;
    }
}
