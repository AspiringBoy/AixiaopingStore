package com.axp.axpseller.models;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dong on 2016/12/12.
 */
public class Bank extends BaseModel{
    @SerializedName("adminuserName")
    private String adminuserName;

    @SerializedName("bankAddress")
    private String bankAddress;

    @SerializedName("bankName")
    private String bankName;

    @SerializedName("cardNo")
    private String cardNo;

    @SerializedName("bankId")
    private String bankId;
    @SerializedName("isDefault")
    private String isDefault;

    @SerializedName("money")
    private String money;

    @SerializedName("counterFee")
    private String counterFee;



    public String getAdminuserName() {
        return adminuserName;
    }

    public void setAdminuserName(String adminuserName) {
        this.adminuserName = adminuserName;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getMoney() { return money; }

    public void setMoney(String money) { this.money = money; }

    public String getCounterFee() { return counterFee; }

    public void setCounterFee(String counterFee) { this.counterFee = counterFee; }
}
