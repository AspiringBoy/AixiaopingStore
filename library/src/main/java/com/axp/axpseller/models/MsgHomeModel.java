package com.axp.axpseller.models;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YY on 2017/5/8.
 */
public class MsgHomeModel extends BaseModel {

    @SerializedName("msgTypes")
    List<MsgHomeObModel> msgTypes;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @SerializedName("userId")
    String userId;

    public List<MsgHomeObModel> getMsgTypes() {
        return msgTypes;
    }

    public void setMsgTypes(List<MsgHomeObModel> msgTypes) {
        this.msgTypes = msgTypes;
    }
}
