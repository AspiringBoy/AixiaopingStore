package com.axp.axpseller.models;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dong on 2016/12/8.
 */
public class Assets extends BaseModel{
    @SerializedName("name")
    private String name;

    @SerializedName("date")
    private String date;

    @SerializedName("money")
    private String money;

    @SerializedName("num")
    private String num;

    @SerializedName("createtime")
    private String createtime;

    @SerializedName("receiveQuantity")
    private int receiveQuantity;

    @SerializedName("totalQuantity")
    private int totalQuantity;

    @SerializedName("type")
    private int type;

    @SerializedName("remark")
    private String remark;

    @SerializedName("totalMoney")
    private String totalMoney;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getMoney() { return money; }

    public void setMoney(String money) { this.money = money; }

    public String getNum() { return num; }

    public void setNum(String num) { this.num = num; }

    public String getCreatetime() { return createtime; }

    public void setCreatetime(String createtime) { this.createtime = createtime; }

    public int getReceiveQuantity() { return receiveQuantity; }

    public void setReceiveQuantity(int receiveQuantity) { this.receiveQuantity = receiveQuantity; }

    public int getTotalQuantity() { return totalQuantity; }

    public void setTotalQuantity(int totalQuantity) { this.totalQuantity = totalQuantity; }

    public int getType() { return type; }

    public void setType(int type) { this.type = type; }

    public String getRemark() { return remark; }

    public void setRemark(String remark) { this.remark = remark; }

    public String getTotalMoney() { return totalMoney; }

    public void setTotalMoney(String totalMoney) { this.totalMoney = totalMoney; }
}
