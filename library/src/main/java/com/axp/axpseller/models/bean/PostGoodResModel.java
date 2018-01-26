package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YY on 2017/12/8.
 */
public class PostGoodResModel extends BaseModel {

    //goodId
    @SerializedName("baseGoodsId")
    String baseGoodsId;

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    //goodId
    @SerializedName("statusId")
    String statusId;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    //goodId
    @SerializedName("typeId")
    String typeId;

    public String getGoodsOrder() {
        return goodsOrder;
    }

    public void setGoodsOrder(String goodsOrder) {
        this.goodsOrder = goodsOrder;
    }

    //goodId
    @SerializedName("goodsOrder")
    String goodsOrder;

    //推广类别

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @SerializedName("type")
    String type;

    //配送方式
    @SerializedName("transportationType")
    int transportationType;

    //权益保障

    public boolean isHasSpecStr() {
        return hasSpecStr;
    }

    public void setHasSpecStr(boolean hasSpecStr) {
        this.hasSpecStr = hasSpecStr;
    }

    public List<Integer> getRightsProtect() {
        return rightsProtect;

    }

    public void setRightsProtect(List<Integer> rightsProtect) {
        this.rightsProtect = rightsProtect;
    }

    @SerializedName("rightsProtect")
    List<Integer> rightsProtect;

    //是否有规格
    @SerializedName("hasSpecStr")
    boolean hasSpecStr;

    //是否是换货商品

    public boolean isChang() {
        return isChang;
    }

    public void setChang(boolean chang) {
        isChang = chang;
    }

    @SerializedName("isChang")
    boolean isChang;

    //邮费
    @SerializedName("transportationPrice")
    double transportationPrice;

    //轮播图
    @SerializedName("topPics")
    List<String> topPics;

    //商品名称
    @SerializedName("goodsName")
    String goodsName;

    //商品类型id
    @SerializedName("categoryId")
    String categoryId;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    //商品类型名称
    @SerializedName("categoryName")
    String categoryName;

    //原价
    @SerializedName("orieignPrice")
    String orieignPrice;

    //现价、库存、类型
    @SerializedName("specifications")
    List<SpecificationModel> specifications;

    //商品详情

    public List<GoodDetailModel> getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(List<GoodDetailModel> goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public String getBaseGoodsId() {
        return baseGoodsId;
    }

    public void setBaseGoodsId(String baseGoodsId) {
        this.baseGoodsId = baseGoodsId;
    }

    public int getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(int transportationType) {
        this.transportationType = transportationType;
    }

    public double getTransportationPrice() {
        return transportationPrice;
    }

    public void setTransportationPrice(double transportationPrice) {
        this.transportationPrice = transportationPrice;
    }

    public List<String> getTopPics() {
        return topPics;
    }

    public void setTopPics(List<String> topPics) {
        this.topPics = topPics;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getOrieignPrice() {
        return orieignPrice;
    }

    public void setOrieignPrice(String orieignPrice) {
        this.orieignPrice = orieignPrice;
    }

    public List<SpecificationModel> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(List<SpecificationModel> specifications) {
        this.specifications = specifications;
    }

    @SerializedName("goodsDetail")
    List<GoodDetailModel> goodsDetail;

}
