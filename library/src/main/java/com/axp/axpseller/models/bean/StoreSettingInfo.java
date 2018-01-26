package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YY on 2017/11/30.
 */
public class StoreSettingInfo extends BaseModel {

    @SerializedName("phone")
    String phone;

    @SerializedName("remark")
    String remark;

    @SerializedName("beginTime")
    String beginTime;

    @SerializedName("endTime")
    String endTime;

    @SerializedName("lng")
    String lng;

    @SerializedName("sellerName")
    String sellerName;

    @SerializedName("sellerLogo")
    String sellerLogo;

    @SerializedName("bottomAds")
    StoreSettingImgModel bottomAds;

    @SerializedName("address")
    String address;

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @SerializedName("zone")
    String zone;

    @SerializedName("lat")
    String lat;

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    @SerializedName("hasVideo")
    boolean hasVideo;

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerMainPageId() {
        return sellerMainPageId;
    }

    public void setSellerMainPageId(String sellerMainPageId) {
        this.sellerMainPageId = sellerMainPageId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerLogo() {
        return sellerLogo;
    }

    public void setSellerLogo(String sellerLogo) {
        this.sellerLogo = sellerLogo;
    }

    public StoreSettingImgModel getBottomAds() {
        return bottomAds;
    }

    public void setBottomAds(StoreSettingImgModel bottomAds) {
        this.bottomAds = bottomAds;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<StoreSettingImgModel> getTopAds() {
        return topAds;
    }

    public void setTopAds(List<StoreSettingImgModel> topAds) {
        this.topAds = topAds;
    }

    public StoreSettingImgModel getSellerView() {
        return sellerView;
    }

    public void setSellerView(StoreSettingImgModel sellerView) {
        this.sellerView = sellerView;
    }

    @SerializedName("sellerId")
    String sellerId;

    @SerializedName("sellerMainPageId")
    String sellerMainPageId;

    @SerializedName("topAds")
    List<StoreSettingImgModel> topAds;

    @SerializedName("sellerView")
    StoreSettingImgModel sellerView;
}
