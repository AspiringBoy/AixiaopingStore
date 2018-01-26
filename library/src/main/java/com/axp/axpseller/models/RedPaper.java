package com.axp.axpseller.models;

import com.google.gson.annotations.SerializedName;
import com.axp.axpseller.core.BaseModel;

/**
 * Created by xu on 2016/8/10.
 * 红包
 */
public class RedPaper extends BaseModel {

    @SerializedName("settingId")
    /** 红包id */
    private String settingId;


    public String getSettingId() { return settingId; }

    public void setSettingId(String settingId) { this.settingId = settingId; }
}
