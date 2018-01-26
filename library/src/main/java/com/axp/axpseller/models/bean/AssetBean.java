package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dong on 2016/12/8.
 */
public class AssetBean extends BaseModel{

    @SerializedName("pageIndex")
    /** 分页页码，从1开始 */
    private int pageIndex = 0;

    @SerializedName("type")
    private String type;

    public int getPageIndex() { return pageIndex; }

    public void setPageIndex(int pageIndex) { this.pageIndex = pageIndex; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }
}
