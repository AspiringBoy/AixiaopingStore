package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/12/5.
 */
public class GoodsManageModel extends BaseModel {

    //商品状态（出售中，商品审核，待处理）出售中 1000   审核中1001  审核失败 1002 待处理 1003
    @SerializedName("statusId")
    int statusId;

    //种类（全国特产|周边店铺，积分商品，普通优惠券|活动优惠券，秒杀，拼团）
    @SerializedName("typeId")
    int typeId;

    //已售数量
    @SerializedName("soldNumber")
    int soldNumber;

    //审核失败说明信息(审核商品特有字段)
    @SerializedName("checkFailedMsg")
    String checkFailedMsg;

    public String getBaseGoodsId() {
        return baseGoodsId;
    }

    public void setBaseGoodsId(String baseGoodsId) {
        this.baseGoodsId = baseGoodsId;
    }

    //基础商品ID
    @SerializedName("baseGoodsId")
    String baseGoodsId;

    //周边店铺商品ID
    @SerializedName("goodsOrder")
    String goodsOrder;

    public String getGoodsOrder() {
        return goodsOrder;
    }

    public void setGoodsOrder(String goodsOrder) {
        this.goodsOrder = goodsOrder;
    }

    //商品icon
    @SerializedName("goodsIcon")
    String goodsIcon;

    //商品name
    @SerializedName("goodsName")
    String goodsName;

    //配送方式(1:包邮 2:不包邮  3:上门自取 4:到店消费)
    @SerializedName("transportationType")
    String transportationType;

    //商品类别
    @SerializedName("cateName")
    String cateName;

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    //权益保障
    @SerializedName("rightsProtect")
    List<Integer> rightsProtect;

    //轮播图
    @SerializedName("coverPics")
    List<String> coverPics;

    public String getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(String transportationType) {
        this.transportationType = transportationType;
    }

    public List<Integer> getRightsProtect() {
        return rightsProtect;
    }

    public void setRightsProtect(List<Integer> rightsProtect) {
        this.rightsProtect = rightsProtect;
    }

    public List<String> getCoverPics() {
        return coverPics;
    }

    public void setCoverPics(List<String> coverPics) {
        this.coverPics = coverPics;
    }

    public String getTransportationPrice() {
        return transportationPrice;
    }

    public void setTransportationPrice(String transportationPrice) {
        this.transportationPrice = transportationPrice;
    }

    //邮费
    @SerializedName("transportationPrice")
    String transportationPrice;

    //价格
    @SerializedName("price")
    String price;

    //推广费比例

    public String getPtCommissionPercent() {
        return ptCommissionPercent;
    }

    public void setPtCommissionPercent(String ptCommissionPercent) {
        this.ptCommissionPercent = ptCommissionPercent;
    }

    @SerializedName("ptCommissionPercent")
    String ptCommissionPercent;

    //商品投放区域名字（全国特产|周边店铺）
    @SerializedName("areaName")
    String areaName;

    //库存(基础商品，积分商品，拼团商品 共有字段)
    @SerializedName("stock")
    int stock;

    //积分(积分商品字段)
    @SerializedName("score")
    String score;

    //金额(积分商品字段)
    @SerializedName("cashPrice")
    String cashPrice;

    //券后价(优惠券商品字段)
    @SerializedName("deductPrice")
    String deductPrice;

    //券量(优惠券商品字段)
    @SerializedName("ticketsNumber")
    String ticketsNumber;

    //券名称(优惠券商品字段)
    @SerializedName("ticketsName")
    String ticketsName;

    //秒杀价格(秒杀商品字段)
    @SerializedName("rushPrice")
    String rushPrice;

    //秒杀时段(秒杀商品字段)
    @SerializedName("rushTimeSlot")
    String rushTimeSlot;

    //秒杀数量(秒杀商品字段)
    @SerializedName("rushNumber")
    int rushNumber;

    //商品是否有规格
    @SerializedName("hasSpecStr")
    boolean hasSpecStr;

    //商品规格数
    @SerializedName("reStockNum")
    String reStockNum;

    public String getReStockNum() {
        return reStockNum;
    }

    public void setReStockNum(String reStockNum) {
        this.reStockNum = reStockNum;
    }

    public ArrayList<SpecificationModel> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(ArrayList<SpecificationModel> specifications) {
        this.specifications = specifications;
    }

    public boolean isHasSpecStr() {
        return hasSpecStr;
    }

    public void setHasSpecStr(boolean hasSpecStr) {
        this.hasSpecStr = hasSpecStr;
    }

    //商品规格
    @SerializedName("specifications")
    ArrayList<SpecificationModel> specifications ;

    //拼团价格(拼团商品字段)
    @SerializedName("putTogetherPrice")
    String putTogetherPrice;

    public List<PermissionModel> getPermission() {
        return permission;
    }

    public void setPermission(List<PermissionModel> permission) {
        this.permission = permission;
    }

    //推广权限(周边店铺商品字段)，权限数组顺序 全国特产，普通优惠券，活动优惠券，积分，秒杀，拼团
    @SerializedName("permission")
    List<PermissionModel> permission;

    public List<PermissionModel2> getPermission2() {
        return permission2;
    }

    public void setPermission2(List<PermissionModel2> permission2) {
        this.permission2 = permission2;
    }

    //重复推广判定，权限数组顺序 全国特产，普通优惠券，活动优惠券，积分，秒杀，拼团
    @SerializedName("permission2")
    List<PermissionModel2> permission2;

    public String getPtPeopleNumber() {
        return ptPeopleNumber;
    }

    public void setPtPeopleNumber(String ptPeopleNumber) {
        this.ptPeopleNumber = ptPeopleNumber;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getSoldNumber() {
        return soldNumber;
    }

    public void setSoldNumber(int soldNumber) {
        this.soldNumber = soldNumber;
    }

    public String getCheckFailedMsg() {
        return checkFailedMsg;
    }

    public void setCheckFailedMsg(String checkFailedMsg) {
        this.checkFailedMsg = checkFailedMsg;
    }

    public String getGoodsIcon() {
        return goodsIcon;
    }

    public void setGoodsIcon(String goodsIcon) {
        this.goodsIcon = goodsIcon;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCashPrice() {
        return cashPrice;
    }

    public void setCashPrice(String cashPrice) {
        this.cashPrice = cashPrice;
    }

    public String getDeductPrice() {
        return deductPrice;
    }

    public void setDeductPrice(String deductPrice) {
        this.deductPrice = deductPrice;
    }

    public String getTicketsNumber() {
        return ticketsNumber;
    }

    public void setTicketsNumber(String ticketsNumber) {
        this.ticketsNumber = ticketsNumber;
    }

    public String getTicketsName() {
        return ticketsName;
    }

    public void setTicketsName(String ticketsName) {
        this.ticketsName = ticketsName;
    }

    public String getRushPrice() {
        return rushPrice;
    }

    public void setRushPrice(String rushPrice) {
        this.rushPrice = rushPrice;
    }

    public String getRushTimeSlot() {
        return rushTimeSlot;
    }

    public void setRushTimeSlot(String rushTimeSlot) {
        this.rushTimeSlot = rushTimeSlot;
    }

    public int getRushNumber() {
        return rushNumber;
    }

    public void setRushNumber(int rushNumber) {
        this.rushNumber = rushNumber;
    }

    public String getPutTogetherPrice() {
        return putTogetherPrice;
    }

    public void setPutTogetherPrice(String putTogetherPrice) {
        this.putTogetherPrice = putTogetherPrice;
    }

    //拼团人数(拼团商品字段)
    @SerializedName("ptPeopleNumber")

    String ptPeopleNumber;
}
