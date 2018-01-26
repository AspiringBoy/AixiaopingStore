package com.axp.axpseller.models.bean;

import com.google.gson.annotations.SerializedName;
import com.axp.axpseller.core.BaseModel;

/**
 * Created by xu on 2016/7/25.
 * 获取退单列表
 */
public class GetBackOrderListBean extends BaseModel {

    @SerializedName("pageIndex")
    private int pageIndex = 0;

    @SerializedName("sellerId")
    private String sellerId;

    public String getBackType() {
        return backType;
    }

    public void setBackType(String backType) {
        this.backType = backType;
    }

    @SerializedName("backType")
    private String backType;


    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }


    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}
