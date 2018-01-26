package com.axp.axpseller.models;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dong on 2016/12/13.
 */
public class Apply extends BaseModel{

    @SerializedName("createtime")
    private String createtime;

    @SerializedName("counterFee")
    private String counterFee;

    @SerializedName("bankName")
    private String bankName;

    @SerializedName("totalMoney")
    private String totalMoney;

    @SerializedName("bankCode")
    private String bankCode;

    @SerializedName("phone")
    private String phone;

    @SerializedName("name")
    private String name;

    @SerializedName("code")
    private String code;

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    @SerializedName("payState")
    private String payState;


    public String getCreatetime() { return createtime; }

    public void setCreatetime(String createtime) { this.createtime = createtime; }

    public String getCounterFee() { return counterFee; }

    public void setCounterFee(String counterFee) { this.counterFee = counterFee; }

    public String getBankName() { return bankName; }

    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getTotalMoney() { return totalMoney; }

    public void setTotalMoney(String totalMoney) { this.totalMoney = totalMoney; }

    public String getBankCode() { return bankCode; }

    public void setBankCode(String bankCode) { this.bankCode = bankCode; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }
}
