package com.axp.axpseller.models;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dong on 2016/12/12.
 */
public class Withdraws extends BaseModel{
    @SerializedName("name")
    private String name;

    @SerializedName("phone")
    private String phone;

    @SerializedName("code")
    private String code;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }
}
