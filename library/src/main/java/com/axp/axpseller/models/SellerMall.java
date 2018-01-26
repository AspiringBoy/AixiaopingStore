package com.axp.axpseller.models;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dong on 2016/12/6.
 */
public class SellerMall extends BaseModel{
    @SerializedName("sellerId")
    private String sellerId;

    @SerializedName("verifyStatus")
    private int verifyStatus;

    @SerializedName("adminuserId")
    private String adminuserId;

    @SerializedName("totalMoney")
    private String totalMoney;

    @SerializedName("todayTotalMoney")
    private String todayTotalMoney;

    @SerializedName("vsitors")
    private String vsitors;

    public String getDirectionUrl() {
        return DirectionUrl;
    }

    public void setDirectionUrl(String directionUrl) {
        DirectionUrl = directionUrl;
    }

    @SerializedName("DirectionUrl")
    private String DirectionUrl;

    @SerializedName("GoodsUrl")
    private String GoodsUrl;

    public String getSellerUrl() {
        return SellerUrl;
    }

    public void setSellerUrl(String sellerUrl) {
        SellerUrl = sellerUrl;
    }

    public String getGoodsUrl() {
        return GoodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        GoodsUrl = goodsUrl;
    }

    @SerializedName("SellerUrl")
    private String SellerUrl;

    @SerializedName("todayOrderNum")
    private String todayOrderNum;

    @SerializedName("news")
    private List<ImageText> news;

    @SerializedName("bottomBanners")
    private List<ImageText> bottomBanners;

    public String getSellerId() { return sellerId; }

    public void setSellerId(String sellerId) { this.sellerId = sellerId; }

    public int getVerifyStatus() { return verifyStatus; }

    public void setVerifyStatus(int verifyStatus) { this.verifyStatus = verifyStatus; }

    public String getAdminuserId() { return adminuserId; }

    public void setAdminuserId(String adminuserId) { this.adminuserId = adminuserId; }

    public String getTotalMoney() { return totalMoney; }

    public void setTotalMoney(String totalMoney) { this.totalMoney = totalMoney; }

    public String getTodayTotalMoney() { return todayTotalMoney; }

    public void setTodayTotalMoney(String todayTotalMoney) { this.todayTotalMoney = todayTotalMoney; }

    public String getVsitors() { return vsitors; }

    public void setVsitors(String vsitors) { this.vsitors = vsitors; }

    public String getTodayOrderNum() { return todayOrderNum; }

    public void setTodayOrderNum(String todayOrderNum) { this.todayOrderNum = todayOrderNum; }

    public List<ImageText> getNews() { return news; }

    public void setNews(List<ImageText> news) { this.news = news; }

    public List<ImageText> getBottomBanners() { return bottomBanners; }

    public void setBottomBanners(List<ImageText> bottomBanners) { this.bottomBanners = bottomBanners; }
}
