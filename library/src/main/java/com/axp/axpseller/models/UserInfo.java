package com.axp.axpseller.models;

import com.google.gson.annotations.SerializedName;
import com.axp.axpseller.core.BaseModel;

/**
 * Created by xu on 2016/5/24.
 * 用户个人信息
 */
public class UserInfo extends BaseModel {

    @SerializedName("token")//融云token
    private String token;
    @SerializedName("userHead")//融云用户头像
    private String userHead;
    @SerializedName("sellerLogo")//融云商家头像
    private String sellerLogo;

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

    @SerializedName("sellerName")//融云商家名称
    private String sellerName;
    @SerializedName("axpAdminUserId")//商家注册融云ID
    private String axpAdminUserId;
    @SerializedName("userId")
    private String userId;
    @SerializedName("name")
    private String name;
    @SerializedName("pwd")
    private String pwd;
    @SerializedName("username")
    private String username;
    @SerializedName("realname")
    private String realname;
    @SerializedName("phone")
    private String phone;
    @SerializedName("address")
    private String address;
    @SerializedName("sex")
    private String sex;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("headimage")
    private String headimage;
    @SerializedName("invitecode")
    private String inviteCode;
    @SerializedName("sign")
    private String sign;
    @SerializedName("cashpoint")
    private String cashpoint = "0";
    @SerializedName("score")
    private String score;
    @SerializedName("vipType")
    private String vipType;
    @SerializedName("availablemoney")
    private String availableMoney = "0";
    @SerializedName("unavailableMoney")
    private String unavailableMoney;
    @SerializedName("freeCount")
    private String freeCount = "0";
    @SerializedName("sellerId")
    private String sellerId;
    @SerializedName("binding")
    private String binding;
    @SerializedName("openId")
    private String openId;
    @SerializedName("tokenId")
    private String tokenId;
    @SerializedName("captcha")
    private String captcha;
    @SerializedName("fansNumber")
    private String fansNumber;
    @SerializedName("inviterImg")
    private String inviterImg;
    @SerializedName("inviterName")
    private String inviterName;
    @SerializedName("bindingInviter")
    private boolean bindingInviter;
    @SerializedName("tdCode")//扫描二维码的链接
    private String tdCode;
    @SerializedName("adminuserId")//商家标识
    private String adminuserId;



    /**
     * 用户ID
     */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 用户昵称
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 用户真实姓名
     */
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * 用户手机号码
     */
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 用户地址
     */
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 用户性别
     */
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 用户生日
     */
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * 用户头像
     */
    public String getHeadimage() {
        return headimage;
    }

    public void setHeadimage(String headimage) {
        this.headimage = headimage;
    }

    /**
     * 用户邀请码
     */
    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    /**
     * 用户签名
     */
    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * 用户代金券（包含红包）
     */
    public String getCashpoint() {
        return cashpoint;
    }

    public void setCashpoint(String cashpoint) {
        this.cashpoint = cashpoint;
    }

    /**
     * 用户积分
     */
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    /**
     * 用户会员类型
     */
    public String getVipType() {
        return vipType;
    }

    public void setVipType(String vipType) {
        this.vipType = vipType;
    }

    /**
     * 用户可用现金余额
     */
    public String getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(String availableMoney) {
        this.availableMoney = availableMoney;
    }

    /**
     * 用户冻结现金余额
     */
    public String getUnavailableMoney() {
        return unavailableMoney;
    }

    public void setUnavailableMoney(String unavailableMoney) {
        this.unavailableMoney = unavailableMoney;
    }

    /**
     * 用户剩余免单券数量
     */
    public String getFreeCount() {
        return freeCount;
    }

    public void setFreeCount(String freeCount) {
        this.freeCount = freeCount;
    }

    /**
     * 用户的商家ID
     */
    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * 用户绑定的手机号码
     */
    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }


    /** 登录名，仅用于传递信息，勿保存本地 */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** 登录密码，仅用于传递信息，勿保存本地 */
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**第三方登录时返回的openid*/
    public String getOpenId() { return openId; }

    public void setOpenId(String openId) { this.openId = openId; }

    /**第三方登录时返回的tokenId*/
    public void setTokenId(String tokenId) { this.tokenId = tokenId; }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getAxpAdminUserId() {
        return axpAdminUserId;
    }

    public void setAxpAdminUserId(String axpAdminUserId) {
        this.axpAdminUserId = axpAdminUserId;
    }

    public String getTokenId() { return tokenId; }
    /**注册时的验证码 */
    public String getCaptcha() { return captcha; }

    public void setCaptcha(String captcha) { this.captcha = captcha; }

    public String getFansNumber() { return fansNumber; }

    public void setFansNumber(String fansNumber) { this.fansNumber = fansNumber; }

    public String getInviterImg() { return inviterImg; }

    public void setInviterImg(String inviterImg) { this.inviterImg = inviterImg; }

    public String getInviterName() { return inviterName; }

    public void setInviterName(String inviterName) { this.inviterName = inviterName; }

    public boolean getBindingInviter() { return bindingInviter; }

    public void setBindingInviter(boolean bindingInviter) { this.bindingInviter = bindingInviter; }

    public String getTdCode() { return tdCode; }

    public void setTdCode(String tdCode) { this.tdCode = tdCode; }

    public String getAdminuserId() { return adminuserId; }

    public void setAdminuserId(String adminuserId) { this.adminuserId = adminuserId; }
}
