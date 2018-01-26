package com.axp.axpseller.models;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YY on 2017/5/5.
 */
public class OrderMsgModel extends BaseModel {
    @SerializedName("msgList")
    List<OrderMsgDtOb> msgList;

    @SerializedName("typeId")
    String typeId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @SerializedName("userId")
    String userId;

    public List<OrderMsgDtOb> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<OrderMsgDtOb> msgList) {
        this.msgList = msgList;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
