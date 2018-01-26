package com.axp.axpseller;


import com.axp.axpseller.utils.AppUtils;

/**
 * Created by xu on 2016/5/6.
 * 用于定义常量
 */
public class Constants {
    /** 正式发布 */
    public static final boolean RELEASE = false;
    /** 正式服务器 */
    public static final boolean RELEASE_SERVICE = true;


    //    1:独立商城stand alone
    public static final String MALL_STAND_ALONE = "1";
    //    2:积分商城score
    public static final String MALL_SOCRE = "2";
    //    3:秒杀商城secondKill
    public static final String MALL_SECOND_KILL = "3";
    //    4:各地特产商城specialLocalProduct
    public static final String MALL_SPECIAL_LOCAL_PRODUCT = "4";
    //    5:99特惠商城preferential99
    public static final String MALL_PREFERENTIAL_99 = "5";
    //    6:会员免单商城Free single
    public static final String MALL_FREE_SINGLE = "6";

    //    独立商城 = "sem";
    public static final String MALL_TYPE_STAND_ALONE = MALL_STAND_ALONE;
    //    积分商城 = "scm";
    public static final String MALL_TYPE_SOCRE = MALL_SOCRE;
    //    秒杀商城 = "skm";
    public static final String MALL_TYPE_SECOND_KILL = MALL_SECOND_KILL;
    //    各地特产商城 = "lsm";
    public static final String MALL_TYPE_SPECIAL_LOCAL_PRODUCT = MALL_SPECIAL_LOCAL_PRODUCT;
    //    99特惠商城 = "nnm";
    public static final String MALL_TYPE_PREFERENTIAL_99 = MALL_PREFERENTIAL_99;
    //    会员免单商城 = "mem";
    public static final String MALL_TYPE_FREE_SINGLE = MALL_FREE_SINGLE;

    //管理商品管理列表数据刷新的sharedpreference名称
    public static final String REFRESH_GOOD_MANAGE_GOOD_LIST = "REFRESH_GOOD_MANAGE_GOOD_LIST";
    //管理商品管理列表数据刷新的sharedpreference的key的名称
    public static final String KEY_OF_GOOD_MANAGE_LIST = "KEY_OF_GOOD_MANAGE_LIST";
    //刷新出售中列表
    public static final int REFRESH_SELLING_GOOD_LIST = 0;
    //刷新商品审核列表
    public static final int REFRESH_GOOD_CHECK_LIST = 1;
    //刷新待处理列表
    public static final int REFRESH_WAIT_HANDLE_LIST = 2;
    //无需刷新列表
    public static final int NO_NEED_REFRESH = 3;

    //RongIM测试环境appkey
    //    public static final String RONGIM_APP_KEY = "vnroth0kv4euo";

    //RongIM正式环境appkey
    public static final String RONGIM_APP_KEY = "m7ua80gbmwh9m";

    //银联支付结果回调广播action
    public final static String PAY_RESULT_RECEIVER_ACTION = "bank_pay_result_action";


    // 微信邀请与QQ邀请及微信支付
    public final static String QQ_APP_ID;
    public static final String QQ_APP_KEY;
    public final static String WEXIN_APP_ID;
    public final static String WEXIN_APP_APPSECRET;
    public static final String PACKAGE_WEIXIN = "com.tencent.mm";
    public static final String PACKAGE_QQ = "com.tencent.mobileqq";

    //Bugly
    public static final String BUGLY_APP_ID;


    /** 显示商城 */
    public static final int HOME_SHOW_MALL = 0;
    /** 显示购物车 */
    public static final int HOME_SHOW_SHOPPING_CAR = 1;
    /** 显示订单 */
    public static final int HOME_SHOW_ODER = 2;
    /** 显示个人中心 */
    public static final int HOME_SHOW_USER_INFO= 3;


    /** 微信支付 */
    public static final String PAY_WEIXIN = "300";
    /** 钱包支付 */
    public static final String PAY_WALLET = "100";
    /** 支付宝支付 */
    public static final String PAY_ALIPAY = "200";
    /** 银联支付 */
    public static final String PAY_BANK = "400";


    /** 待支付 */
    public static final String ORDER_STATUS_WAIT_PAY = "0";
    /** 待确认 */
    public static final String ORDER_STATUS_WAIT_CONFIRM = "10";
    /** 待发货 */
    public static final String ORDER_STATUS_WAIT_SEND_OUT_GOODS = "20";
    /** 待兑换 */
    public static final String ORDER_STATUS_WAIT_EXCHANGE = "25";
    /** 待收货 */
    public static final String ORDER_STATUS_WAIT_OF_GOODS = "30";
    /** 待评价 */
    public static final String ORDER_STATUS_WAIT_COMMENT = "40";
    /** 已完成 */
    public static final String ORDER_STATUS_END = "50";
    /** 已评价 */
    public static final String ORDER_STATUS_COMMENT = "70";


    /** 审核不通过 */
    public static final String BACK_ORDER_STATUS_NO_ACCEPT = "-15";
    /** 商家未确认 */
    public static final String BACK_ORDER_STATUS_WAIT_CONFIRM = "0";
    /** 待审核 */
    public static final String BACK_ORDER_STATUS_WAIT_VERIFY = "10";
    /** 已审核 */
    public static final String BACK_ORDER_STATUS_VERIFY = "20";
    /** 已支付 */
    public static final String BACK_ORDER_STATUS_PAY = "30";
    /** 退单完成 */
    public static final String BACK_ORDER_STATUS_ACCEPT = "40";


    /** 商品评论图片 */
    public static final String UPLOAD_COMMENT_IMAGE = "COMMENT_IMAGE";
    /** 用户头像 */
    public static final String UPLOAD_USER_HEAD_SCULPTURE = "USER_HEAD_SCULPTURE";
    /** 意见反馈 */
    public static final String UPLOAD_FEED_BACK = "FEED_BACK";
    /** 申请退单 */
    public static final String UPLOAD_APPLY_BACK_ORDER = "APPLY_BACK_ORDER";


    /** 解锁获得积分类型 */
    public static final String SCORE_TYPE_UNLOCK = "2";
    /** 访问微官网获得积分类型 */
    public static final String SCORE_TYPE_WEBSITE = "3";
    /** 滑屏广告获得积分类型 */
    public static final String SCORE_TYPE_GLIDE_ADVERT = "12";
    /** 商品管理列表状态:出售中 */
    public static final String GOOD_MANAGE_SELLING_STATUS = "1000";
    /** 商品管理列表状态:商品审核 */
    public static final String GOOD_MANAGE_CHECK_STATUS = "1001";
    /** 商品管理列表状态:待处理 */
    public static final String GOOD_MANAGE_WAIT_HANDLE_STATUS = "1003";
    /** 推广类别：全国 100  */
    public static final String EXTENSION_ALL_CITY_TYPE = "100";
    /** 推广类别：普通券 200  */
    public static final String EXTENSION_NORMAL_COUPON_TYPE = "200";
    /** 推广类别：优惠券 300  */
    public static final String EXTENSION_ACTIVE_COUPON_TYPE = "300";
    /** 推广类别：积分 400  */
    public static final String EXTENSION_SCORE_TYPE = "400";
    /** 推广类别：秒杀 500  */
    public static final String EXTENSION_SEC_KILL_TYPE = "500";
    /** 推广类别：拼团 600  */
    public static final String EXTENSION_PINTUAN_TYPE = "600";
    /** 推广类别：换货 700  */
    public static final String EXTENSION_EXCHANGE_TYPE = "700";
    /** 推广为活动优惠券的web页面 (后面拼接gid=6941&sid=1&admin_id=811&fromPlatform=Android)*/
    public static final String ACTIVE_COUPON_EXTENSION_WEB_URL = "http://seller.aixiaoping.com/Home/Goods/newCoupon?";


    static {
        //初始化配置数据
        QQ_APP_ID = AppUtils.getApplicationMataData(ArchitectureAppliation.getAppliation(),"QQ_APP_ID");
        QQ_APP_KEY = AppUtils.getApplicationMataData(ArchitectureAppliation.getAppliation(),"QQ_APP_KEY");
        WEXIN_APP_ID = AppUtils.getApplicationMataData(ArchitectureAppliation.getAppliation(),"WEXIN_APP_ID");
        WEXIN_APP_APPSECRET = AppUtils.getApplicationMataData(ArchitectureAppliation.getAppliation(),"WEXIN_APP_APPSECRET");

        if(RELEASE){
            //正式服务器Bugly
            BUGLY_APP_ID = "900011169";
        } else {
            //测试服务器Bugly
            BUGLY_APP_ID = "900043058";
        }
    }

}
