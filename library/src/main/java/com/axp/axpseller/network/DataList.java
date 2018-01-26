package com.axp.axpseller.network;

import com.google.gson.annotations.SerializedName;
import com.axp.axpseller.core.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xu on 2016/6/8.
 * 数据响应载体，用于适应列表数据
 */
public class DataList<T> extends BaseModel {

    @SerializedName("dataList")
    private List<T> dataList = new ArrayList<>();

    @SerializedName("pageSize")
    private int pageSize = 0;

    @SerializedName("pageIndex")
    private int pageIndex = 0;

    @SerializedName("pageItemCount")
    private int pageItemCount = 10;

    @SerializedName("score")
    private String score;
    @SerializedName("totalMoney")
    private String totalMoneys;

    @SerializedName("unConfirmedMoney")
    private String unConfirmedMoney;

    @SerializedName("ischeck")
    private String ischeck;

    @SerializedName("message")
    private String message;

    @SerializedName("minMoney")
    private String minMoney;

    private String recyclerViewStatus = "RECYCLER_VIEW_STATUS_SUCCESS";


    /** 数据列表 */
    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }


    /**
     * 最大页数，一般用于分页显示
     */
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getScore() { return score; }

    public void setScore(String score) { this.score = score; }

    /** 当前返回数据的页索引 */
    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    /** 当前一页返回的数据条数 */
    public int getPageItemCount() {
        return pageItemCount;
    }

    public void setPageItemCount(int pageItemCount) {
        this.pageItemCount = pageItemCount;
    }

    public String getTotalMoneys() { return totalMoneys; }

    public void setTotalMoneys(String totalMoney) { this.totalMoneys = totalMoney; }

    public String getUnConfirmedMoney() { return unConfirmedMoney; }

    public void setUnConfirmedMoney(String unConfirmedMoney) { this.unConfirmedMoney = unConfirmedMoney; }

    public String getIscheck() { return ischeck; }

    public void setIscheck(String ischeck) { this.ischeck = ischeck; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getMinMoney() { return minMoney; }

    public void setMinMoney(String minMoney) { this.minMoney = minMoney; }

    /** recyclerView的值状态 SuperRecyclerView中有定义 默认正在加载 */
    public String getRecyclerViewStatus() {
        return recyclerViewStatus;
    }

    public void setRecyclerViewStatus(String recyclerViewStatus) { this.recyclerViewStatus = recyclerViewStatus; }
}
