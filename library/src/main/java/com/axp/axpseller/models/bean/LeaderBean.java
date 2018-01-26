package com.axp.axpseller.models.bean;

import com.google.gson.annotations.SerializedName;
import com.axp.axpseller.core.BaseModel;

/**
 * Created by Dong on 2016/7/7.
 */
public class LeaderBean extends BaseModel{
    @SerializedName("name")
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
