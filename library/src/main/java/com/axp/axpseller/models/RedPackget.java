package com.axp.axpseller.models;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dong on 2016/12/10.
 * 发红包
 */
public class RedPackget extends BaseModel{

    @SerializedName("money")
    private  String money;

    @SerializedName("mun")
    private  String mun;

    @SerializedName("type")
    private  String type;

    @SerializedName("message")
    private  String message;

    public String getMoney() { return money; }

    public void setMoney(String money) { this.money = money; }

    public String getMun() { return mun; }

    public void setMun(String mun) { this.mun = mun; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }
}
