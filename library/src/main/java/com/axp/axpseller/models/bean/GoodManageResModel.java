package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YY on 2017/12/6.
 */
public class GoodManageResModel extends BaseModel{

    @SerializedName("pageSize")
    int pageSize;

    @SerializedName("pageItemCount")
    int pageItemCount;

    @SerializedName("pageIndex")
    int pageIndex;

    @SerializedName("statusId")
    int statusId;

    @SerializedName("sellingCount")
    int sellingCount;

    @SerializedName("checkingCount")
    int checkingCount;

    public int getWaitingCount() {
        return waitingCount;
    }

    public void setWaitingCount(int waitingCount) {
        this.waitingCount = waitingCount;
    }

    public int getSellingCount() {
        return sellingCount;
    }

    public void setSellingCount(int sellingCount) {
        this.sellingCount = sellingCount;
    }

    public int getCheckingCount() {
        return checkingCount;
    }

    public void setCheckingCount(int checkingCount) {
        this.checkingCount = checkingCount;
    }

    @SerializedName("waitingCount")
    int waitingCount;

    public List<GoodsManageModel> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<GoodsManageModel> detailList) {
        this.detailList = detailList;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageItemCount() {
        return pageItemCount;
    }

    public void setPageItemCount(int pageItemCount) {
        this.pageItemCount = pageItemCount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @SerializedName("detailList")
    List<GoodsManageModel> detailList;
}
