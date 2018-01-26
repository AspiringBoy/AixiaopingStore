package com.axp.axpseller.models;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dong on 2017/2/28.
 */
public class ShopcategoryInfo extends BaseModel{
    @SerializedName("categoryName")

    private String categoryName;

    @SerializedName("categoryId")

    private String categoryId;

    public String getCategoryName() { return categoryName; }

    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getCategoryId() { return categoryId; }

    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
}
