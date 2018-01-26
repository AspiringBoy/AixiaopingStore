package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;

import java.util.ArrayList;

/**
 * Created by YY on 2017/12/14.
 */
public class ApplyGoodModel extends BaseModel {

    private String type;
    private String score;
    private String teamNumber;
    private String ticketprice;
    private String ticketnum;
    private String commission;
    private String cid;
    private String display;
    private String changeDesc;

    public String getChangeDesc() {
        return changeDesc;
    }

    public void setChangeDesc(String changeDesc) {
        this.changeDesc = changeDesc;
    }

    public ArrayList<SpecificationModel> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(ArrayList<SpecificationModel> specifications) {
        this.specifications = specifications;
    }

    private ArrayList<String> want;
    private ArrayList<SpecificationModel> specifications;

    public ArrayList<String> getWant() {
        return want;
    }

    public void setWant(ArrayList<String> want) {
        this.want = want;
    }

    public String getRestrictNum() {
        return restrictNum;
    }

    public void setRestrictNum(String restrictNum) {
        this.restrictNum = restrictNum;
    }

    private String restrictNum;

    public String getValidtime() {
        return validtime;
    }

    public void setValidtime(String validtime) {
        this.validtime = validtime;
    }

    public String getTicketprice() {
        return ticketprice;
    }

    public void setTicketprice(String ticketprice) {
        this.ticketprice = ticketprice;
    }

    public String getTicketnum() {
        return ticketnum;
    }

    public void setTicketnum(String ticketnum) {
        this.ticketnum = ticketnum;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    private String validtime;

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(String teamNumber) {
        this.teamNumber = teamNumber;
    }

    private String discountPrice;

    public boolean isRestrict() {
        return isRestrict;
    }

    public void setRestrict(boolean restrict) {
        isRestrict = restrict;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPurchaseNum() {
        return purchaseNum;
    }

    public void setPurchaseNum(String purchaseNum) {
        this.purchaseNum = purchaseNum;
    }

    private boolean isRestrict;
    private String goodsOrder;
    private String purchaseNum;
    private String price;
    private String stock;
    private String transportationType;
    private String transportationPrice;
    private String startedTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    private String startTime;
    private String endTime;

    public String getEndedTime() {
        return endedTime;
    }

    public void setEndedTime(String endedTime) {
        this.endedTime = endedTime;
    }

    private String endedTime;

    public String getSecondskilltimeId() {
        return secondskilltimeId;
    }

    public void setSecondskilltimeId(String secondskilltimeId) {
        this.secondskilltimeId = secondskilltimeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGoodsOrder() {
        return goodsOrder;
    }

    public void setGoodsOrder(String goodsOrder) {
        this.goodsOrder = goodsOrder;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public String getTransportationPrice() {
        return transportationPrice;
    }

    public void setTransportationPrice(String transportationPrice) {
        this.transportationPrice = transportationPrice;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(String transportationType) {
        this.transportationType = transportationType;
    }

    public String getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(String startedTime) {
        this.startedTime = startedTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    private String secondskilltimeId;
}
