package com.axp.axpseller.models;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dong on 2016/12/8.
 */
public class AssetM extends BaseModel{

    @SerializedName("totalMoney")
    private double totalMoney;

    @SerializedName("moneyList")
    private List<Assets> moneyList;

    @SerializedName("redpaperList")
    private List<Assets> redpaperList;

    public double getTotalMoney() { return totalMoney; }

    public void setTotalMoney(double totalMoney) { this.totalMoney = totalMoney; }

    public List<Assets> getMoneyList() { return moneyList; }

    public void setMoneyList(List<Assets> moneyList) { this.moneyList = moneyList; }

    public List<Assets> getRedpaperList() { return redpaperList; }

    public void setRedpaperList(List<Assets> redpaperList) { this.redpaperList = redpaperList; }
}
