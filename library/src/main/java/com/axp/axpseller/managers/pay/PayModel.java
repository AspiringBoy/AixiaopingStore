package com.axp.axpseller.managers.pay;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by xu on 2016/6/30.
 * 微信支付后台的返回数据
 */
public class PayModel extends BaseModel {

//    应用ID	appid	String(32)	是	wx8888888888888888	微信开放平台审核通过的应用APPID
//    商户号	partnerid	String(32)	是	1900000109	微信支付分配的商户号
//    预支付交易会话ID	prepayid	String(32)	是	WX1217752501201407033233368018	微信返回的支付交易会话ID
//    扩展字段	package	String(128)	是	Sign=WXPay	暂填写固定值Sign=WXPay
//    随机字符串	noncestr	String(32)	是	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，不长于32位。推荐随机数生成算法
//    时间戳	timestamp	String(10)	是	1412000000	时间戳，请见接口规则-参数规定
//    签名	sign	String(32)	是	C380BEC2BFD727A4B6845133519F3AD6	签名，详见签名生成算法


    @SerializedName("appId")
    /** 应用ID	appid */
    private String appId;
    @SerializedName("partnerid")
    /** 商户号	partnerid */
    private String partnerId;
    @SerializedName("prepayId")
    /** 预支付交易会话ID */
    private String prepayId;
    @SerializedName("nonceStr")
    /** 随机字符串 */
    private String nonceStr;
    @SerializedName("timeStamp")
    /** 时间戳 */
    private String timeStamp;

    public String getTeamOrderId() {
        return teamOrderId;
    }

    public void setTeamOrderId(String teamOrderId) {
        this.teamOrderId = teamOrderId;
    }

    @SerializedName("teamOrderId")
    /** 拼团商品id */
    private String teamOrderId;
    @SerializedName("package")
    /** 扩展字段 */
    private String packageValue;
    @SerializedName("sign")
    /** 签名 支付宝支付的时候，只会返回这个字段 */
    private String sign;

    private String extData;

    @SerializedName("Version")
    String version;

    @SerializedName("MerchantId")
    String MerchantId;

    @SerializedName("MerchOrderId")
    String MerchOrderId;

    @SerializedName("Amount")
    String Amount;

    @SerializedName("TradeTime")
    String TradeTime;

    @SerializedName("OrderId")
    String OrderId;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMerchantId() {
        return MerchantId;
    }

    public void setMerchantId(String merchantId) {
        MerchantId = merchantId;
    }

    public String getMerchOrderId() {
        return MerchOrderId;
    }

    public void setMerchOrderId(String merchOrderId) {
        MerchOrderId = merchOrderId;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getTradeTime() {
        return TradeTime;
    }

    public void setTradeTime(String tradeTime) {
        TradeTime = tradeTime;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    @SerializedName("Sign")
    String Sign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getExtData() {
        return extData;
    }

    public void setExtData(String extData) {
        this.extData = extData;
    }
}
