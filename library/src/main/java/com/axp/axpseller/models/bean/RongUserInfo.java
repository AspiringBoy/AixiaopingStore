package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2017/11/6.
 */
public class RongUserInfo extends BaseModel {

    @SerializedName("userId")
    private String userId;

    @SerializedName("name")
    private String name;

    public String getPortraitUri() {
        return portraitUri;
    }

    public void setPortraitUri(String portraitUri) {
        this.portraitUri = portraitUri;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("portraitUri")
    private String portraitUri;

}
