package com.axp.axpseller.models;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dong on 2016/12/6.
 * 商家版商家信息
 */
public class SellersInfo extends BaseModel{
    @SerializedName("loginname")
    private String loginname;

    @SerializedName("password")
    private String password;

    @SerializedName("sellerId")
    private String sellerId;

    @SerializedName("adminuserId")
    private String adminuserId;

    @SerializedName("phone")
    private String phone;

    @SerializedName("invitecode")
    private String invitecode;

    @SerializedName("address")
    private String address;

    @SerializedName("reContacts")
    private String reContacts;

    @SerializedName("sellerName")
    private String sellerName;

    @SerializedName("sellerLogo")
    private String sellerLogo;

    @SerializedName("reAddress")
    private String reAddress;

    @SerializedName("contacts")
    private String contacts;

    @SerializedName("loginName")
    private String loginName;

    @SerializedName("loginNameRemark")
    private String loginNameRemark;

    @SerializedName("partnerName")
    private String partnerName;

    public String getLoginname() { return loginname; }

    public void setLoginname(String loginname) { this.loginname = loginname; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getSellerId() { return sellerId; }

    public void setSellerId(String sellerId) { this.sellerId = sellerId; }

    public String getAdminuserId() { return adminuserId; }

    public void setAdminuserId(String adminuserId) { this.adminuserId = adminuserId; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getInvitecode() {
        return invitecode;
    }

    public void setInvitecode(String invitecode) {
        this.invitecode = invitecode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReContacts() {
        return reContacts;
    }

    public void setReContacts(String reContacts) {
        this.reContacts = reContacts;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginNameRemark() {
        return loginNameRemark;
    }

    public void setLoginNameRemark(String loginNameRemark) {
        this.loginNameRemark = loginNameRemark;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
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

    public String getReAddress() {
        return reAddress;
    }

    public void setReAddress(String reAddress) {
        this.reAddress = reAddress;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
}
