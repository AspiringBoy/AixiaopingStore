package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dong on 2016/12/12.
 */
public class BankBean extends BaseModel{
    @SerializedName("pageIndex")
    /** 分页页码，从1开始 */
    private int pageIndex = 0;

    public int getPageIndex() { return pageIndex; }

    public void setPageIndex(int pageIndex) { this.pageIndex = pageIndex; }
}
