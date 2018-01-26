package com.axp.axpseller.network;

import com.axp.axpseller.managers.pay.PayModel;
import com.axp.axpseller.models.Address;
import com.axp.axpseller.models.Apply;
import com.axp.axpseller.models.Assets;
import com.axp.axpseller.models.AssetsMsgDtModel;
import com.axp.axpseller.models.BackOrder;
import com.axp.axpseller.models.BackOrderList;
import com.axp.axpseller.models.Bank;
import com.axp.axpseller.models.CheckStatusModel;
import com.axp.axpseller.models.CommentList;
import com.axp.axpseller.models.DeleteMsgModel;
import com.axp.axpseller.models.Exempt;
import com.axp.axpseller.models.Fans;
import com.axp.axpseller.models.FreeMall;
import com.axp.axpseller.models.GlideAdvert;
import com.axp.axpseller.models.Goods;
import com.axp.axpseller.models.GoodsList;
import com.axp.axpseller.models.GoodsType;
import com.axp.axpseller.models.Home;
import com.axp.axpseller.models.Live;
import com.axp.axpseller.models.Message;
import com.axp.axpseller.models.MonitorAccount;
import com.axp.axpseller.models.MsgHomeModel;
import com.axp.axpseller.models.MyScore;
import com.axp.axpseller.models.Order;
import com.axp.axpseller.models.OrderList;
import com.axp.axpseller.models.OrderMsgDtModel;
import com.axp.axpseller.models.OrderMsgModel;
import com.axp.axpseller.models.Preferential99;
import com.axp.axpseller.models.RedPaper;
import com.axp.axpseller.models.ScoreExchangeMall;
import com.axp.axpseller.models.SecondKill;
import com.axp.axpseller.models.Seller;
import com.axp.axpseller.models.SellerInfo;
import com.axp.axpseller.models.SellerList;
import com.axp.axpseller.models.SellerMall;
import com.axp.axpseller.models.SellersInfo;
import com.axp.axpseller.models.Shopcate;
import com.axp.axpseller.models.ShoppingCarList;
import com.axp.axpseller.models.Show;
import com.axp.axpseller.models.SpecialLocalProduct;
import com.axp.axpseller.models.SystemMsgDtModel;
import com.axp.axpseller.models.UpdateStoreInfo;
import com.axp.axpseller.models.UserInfo;
import com.axp.axpseller.models.ZoneList;
import com.axp.axpseller.models.bean.GoodManageResModel;
import com.axp.axpseller.models.bean.LeaderBean;
import com.axp.axpseller.models.bean.MyScoreResModel;
import com.axp.axpseller.models.bean.PostGoodResModel;
import com.axp.axpseller.models.bean.RechargeCardModel;
import com.axp.axpseller.models.bean.RechargeScoreOnlineModel;
import com.axp.axpseller.models.bean.RongUserInfo;
import com.axp.axpseller.models.bean.SecKillTimeResModel;
import com.axp.axpseller.models.bean.SendScoreDtResModel;
import com.axp.axpseller.models.bean.StoreSettingInfo;
import com.axp.axpseller.models.bean.UpdateScoreBean;
import com.axp.axpseller.models.bean.UploadFileBean;
import com.axp.axpseller.models.config.ClientConfig;
import com.axp.axpseller.models.demo.IpInfo;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by xu on 2016/4/28.
 * <p>
 * 所有请求接口的定义
 */
public interface API {

    /**
     * Demo中使用的接口
     *
     * @param ip 查询的ip
     * @return
     */
    @GET("service/getIpInfo.php")
    Call<Response<IpInfo>> getIpInfo(@Query("ip") String ip);

//    @POST("handler/askHandler.ashx")
//    @FormUrlEncoded
//    Call<Test> gettest(@Field("action") String action, @Field("voteId") String voteId, @Field("CompId") String CompId );


    /**---------------------------------------------------config START--------------------------------------------------------------------- */

    /**
     * 获取城市列表
     *
     * @param jsonData
     * @return
     */
    @POST("config/getZoneList")
    Call<Response<ZoneList>> getZoneList(@Query("data") String jsonData);

    /**
     * 获取客户端运行配置
     *
     * @param jsonData
     * @return
     */
    @POST("config/getClientConfig")
    Call<Response<ClientConfig>> getClientConfig(@Query("data") String jsonData);

    /**---------------------------------------------------config END--------------------------------------------------------------------- */


    /**---------------------------------------------------USER START--------------------------------------------------------------------- */

    /**
     * 登录
     *
     * @param jsonData
     * @return
     */
    @POST("users/login")
    //通过注解设置请求头
//    @Headers({
//            "Cache-Control: max-age=640000",
//            "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko"
//    })
    Call<Response<UserInfo>> login(@Query("data") String jsonData);

    /**
     * 商家登录
     */
    @POST("sellers/login")
    Call<Response<SellersInfo>> logins(@Query("data") String jsonData);

    /**
     * 商家版首页
     */
    @POST("sellers/getSellerHomepage")
    Call<Response<SellerMall>> getSellerHomepage(@Query("data") String jsonData);

    /**
     * 充值
     */
    @POST("recharge/payRecharge")
    Call<Response<PayModel>> payRecharge(@Query("data") String jsonData);

    /**
     * 充值界面余额
     */
    @POST("adminUser/getTotalM")
    Call<Response<Assets>> getTotalM(@Query("data") String jsonData);
    /**
     * 资产列表
     */
    @POST("adminUser/getM")
    Call<Response<DataList<Assets>>> getM(@Query("data") String jsonData);

    /**
     * 红包列表
     */
    @POST("adminUser/getR")
    Call<Response<DataList<Assets>>> getR(@Query("data") String jsonData);

    /**
     * 发红包
     */
    @POST("sellers/sendRedpacket")
    Call<Response> sendRedpacket(@Query("data") String jsonData);

    /**
     * 提现资料申请
     */
    @POST("sellers/Apply")
    Call<Response> apply(@Query("data") String jsonData);

    /**
     * 选择店铺类型
     */
    @POST("sellers/getShopcategoryInfo")
    Call<Response<List<Shopcate>>> getShopcategoryInfo(@Query("data") String jsonData);

    /**
     * 选择店铺类型
     */
    @POST("sellers/getPublishGoodsType")
    Call<Response<List<Shopcate>>> getPublishGoodsType(@Query("data") String jsonData);

    /**
     * 提交开店资料
     */
    @POST("sellers/updateStoreInfo")
    Call<Response<UpdateStoreInfo>> updateStoreInfo(@Query("data") String jsonData);

    /**
     * 提交资料状态审核
     */
    @POST("sellers/storeVerifyStatus")
    Call<Response<UpdateStoreInfo>> storeVerifyStatus(@Query("data") String jsonData);

    /**
     * 提交资料状态审核
     */
    @POST("sellers/returnCheckstatus")
    Call<Response<CheckStatusModel>> returnCheckstatus(@Query("data") String jsonData);


    /**
     * 获取银行卡列表
     */
     @POST("sellers/getBank")
     Call<Response<DataList<Bank>>> getBank(@Query("data") String jsonData);

    /**
     * 删除银行卡
     */
    @POST("sellers/delBankById")
    Call<Response> delBankById(@Query("data") String jsonData);

    /**
     * 提现
     */
    @POST("sellers/withdrawalApply")
    Call<Response> withdrawalApply(@Query("data") String jsonData);

    /**
     * 已确认提现列表
     */
    @POST("sellers/getwithdrawalApplyForPay")
    Call<Response<DataList<Apply>>> getwithdrawalApplyForPay(@Query("data") String jsonData);

    /**
     * 未确认提现列表
     */
    @POST("sellers/getwithdrawalApply")
    Call<Response<DataList<Apply>>> getwithdrawalApply(@Query("data") String jsonData);

    /**
     * 提现信息
     */
    @POST("sellers/WithdrawApplyInfo")
    Call<Response<DataList<Bank>>> WithdrawApplyInfo(@Query("data") String jsonData);

    /**
     * 更新银行卡
     */
    @POST("sellers/updateBankById")
    Call<Response> updateBankById(@Query("data") String jsonData);

    /**
     * 添加银行卡
     */
    @POST("sellers/commitBank")
    Call<Response> commitBank(@Query("data") String jsonData);
    /**
     * 获得提现资料
     */
    @POST("sellers/getWithdrawals")
    Call<Response<DataList<Apply>>> getwithdrawals(@Query("data") String jsonData);

    /**
     * 获取商家个人信息
     */
    @POST("sellers/getAdminInfoById")
    Call<Response<SellersInfo>> getAdminInfoById(@Query("data") String jsonData);
    /**
     * 注册
     *
     * @param jsonData
     * @return
     */
    @POST("users/register")
    Call<Response<UserInfo>> register(@Query("data") String jsonData);


    /**
     * 上传文件
     *
     * @param userId
     * @param fileUse
     * @param uploadFile
     * @return
     */
    @Multipart
    @POST("users/uploadFilePic")
    Call<Response<UploadFileBean>> uploadFile(@Part("userId") String userId, @Part("fileUse") String fileUse,
                                              @Part MultipartBody.Part uploadFile);

    /*@POST("users/uploadFilePic")
    Call<Response<UploadFileBean>> uploadFile(@Query("userId") String userId, @Query("fileUse") String fileUse,
                                              @Query("uploadFile") String uploadFile);*/

    /**
     * 获取个人信息
     *
     * @param jsonData
     * @return
     */
    @POST("users/getBaseInfo")
    Call<Response<UserInfo>> getBaseInfo(@Query("data") String jsonData);
    /**
     * 获取监控账号密码
     */
    @POST("users/getMonitorAccount")
    Call<Response<MonitorAccount>> getMonitorAccount(@Query("data") String jsonData);

    /**
     * 检查红包
     *
     * @param jsonData
     * @return
     */
    @POST("users/checkRedPaper")
    Call<Response<RedPaper>> checkRedPaper(@Query("data") String jsonData);



    /**
     * 获取验证码D
     *
     * @param jsonData
     * @return
     */
    @POST("users/sendCaptcha")
    Call<Response> sendCaptcha(@Query("data") String jsonData);

    /**
     * 添加地址
     *
     * @param jsonData
     * @return
     */
    @POST("users/putAddress")
    Call<Response> putAddress(@Query("data") String jsonData);

    /**
     * 提交用户获得的积分
     *
     * @param jsonData
     * @return
     */
    @POST("users/updateScore")
    Call<Response<UpdateScoreBean>> updateScore(@Query("data") String jsonData);

    /**
     * 获取积分列表
     */
    @POST("users/getScoreList")
    Call<Response<DataList<MyScore>>> getScoreList(@Query("data") String jsonData);

    /**
     * 获取消息列表
     */
    @POST("users/getMessageList")
    Call<Response<DataList<Message>>> getMessageList(@Query("data") String jsonData);

    /**
     * 获取地址列表
     */
    @POST("users/getAddressList")
    Call<Response<DataList<Address>>> getAddressList(@Query("data") String jsonData);

    /**
     * 获取用户默认地址
     *
     * @param jsonData
     * @return
     */
    @POST("users/getDefaultAddress")
    Call<Response<Address>> getDefaultAddress(@Query("data") String jsonData);

    /**
     * 删除地址
     */
    @POST("users/removeAddress")
    Call<Response<Address>> removeAddress(@Query("data") String jsonData);

    /**
     * 设置默认地址
     */
    @POST("users/setDefaultAddress")
    Call<Response<Address>> setDefaultAddress(@Query("data") String jsonData);

    /**
     * 修改地址
     */
    @POST("users/updateAddress")
    Call<Response<Address>> updateAddress(@Query("data") String jsonData);

    /**
     * 获取粉丝列表
     */
    @POST("users/getFansList")
    Call<Response<DataList<Fans>>> getFansList(@Query("data") String jsonData);

    /**
     * 意见反馈
     */
    @POST("users/feedback")
    Call<Response<Fans>> feedback(@Query("data") String jsonData);

    /**
     * 获取兑换券列表
     */
    @POST("users/getExemptList")
    Call<Response<DataList<Exempt>>> getExemptList(@Query("data") String jsonData);

    /**
     * 绑定联系人
     */
    @POST("users/bangdingContacts")
    Call<Response<LeaderBean>> bangdingContacts(@Query("data") String jsonData);

    /**
     * 个人资料修改
     */
    @POST("users/changeBaseInfo")
    Call<Response> changeBaseInfo(@Query("data") String jsonData);

    /**
     * 忘记密码及找回密码
     */
      @POST("users/forgetPassword")
      Call<Response> forgetPassword(@Query("data") String jsonData);

    /**
     * 修改密码
     */
    @POST("users/changePassword")
    Call<Response> changePassword(@Query("data") String jsonData);
    /**
     * 成为会员
     */
    @POST("users/becomeVip")
    Call<Response<PayModel>> becomeVip(@Query("data") String jsonData);

    /**
     * 获取商家信息
     */
    @POST("users/getSellerInfo")
    Call<Response<SellerInfo>> getSellerInfo(@Query("data") String jsonData);

    /**
     * 待发货
     */
    @POST("order/sellerConfirmReceipt")
    Call<Response> sellerConfirmReceipt(@Query("data") String jsonData);

    /**
     * 待兑换
     */
    @POST("order/sellerConfirmExchange")
    Call<Response> sellerConfirmExchange(@Query("data") String jsonData);

    /**
     * 积分兑换
     */
    @POST("mall/scoreExchangeMall")
    Call<Response<ScoreExchangeMall>> scoreExchangeMall(@Query("data") String jsonData);
    /**---------------------------------------------------USER END--------------------------------------------------------------------- */

    /**---------------------------------------------------ADVERTS START--------------------------------------------------------------------- */

    /**
     * 获取滑屏广告
     *
     * @param jsonData
     * @return
     */
    @POST("advers/getAdverImgs")
    Call<Response<GlideAdvert>> getAdverImgs(@Query("data") String jsonData);

    /**---------------------------------------------------ADVERTS END--------------------------------------------------------------------- */


    /**---------------------------------------------------MALL START--------------------------------------------------------------------- */
    /**
     * 请求首页数据
     *
     * @param jsonData
     * @return
     */
    @POST("mall/getHome")
    Call<Response<Home>> getHome(@Query("data") String jsonData);

    /**
     * 请求首页直播列表数据
     */
    @POST("live/getTopLive")
    Call<Response<List<Live>>> getTopLive(@Query("data") String jsonData);

    /**
     * 请求更多直播列表数据
     */
    @POST("live/getLive")
    Call<Response<DataList<Live>>> getLive(@Query("data") String jsonData);

    /**
     * 直播详情
     */
    @POST("live/getLiveById")
    Call<Response<DataList<Live>>> getLiveById(@Query("data") String jsonData);
    /**
     * 是否有换货会
     */
    @POST("config/getIsShow")
    Call<Response<Show>> getIsShow(@Query("data") String jsonData);

    /**
     * 免单商城
     *
     * @param jsonData
     * @return
     */
    @POST("mall/freeMall")
    Call<Response<FreeMall>> freeMall(@Query("data") String jsonData);


    /**
     * 提交商家位置
     *
     * @param jsonData
     * @return
     */
    @POST("mall/postSellerAddress")
    Call<Response> postSellerAddress(@Query("data") String jsonData);

    /**
     * 99特惠商城
     *
     * @param jsonData
     * @return
     */
    @POST("mall/preferential99")
    Call<Response<Preferential99>> preferential99(@Query("data") String jsonData);

    /**
     * 特产速递
     *
     * @param jsonData
     * @return
     */
    @POST("mall/specialLocalProduct")
    Call<Response<SpecialLocalProduct>> specialLocalProduct(@Query("data") String jsonData);

    /**
     * 商品关注
     *
     * @param jsonData
     * @return
     */
    @POST("mall/goodsConcern")
    Call<Response> goodsConcern(@Query("data") String jsonData);

    /**
     * 关注商家
     *
     * @param jsonData
     * @return
     */
    @POST("mall/shopConcern")
    Call<Response> shopConcern(@Query("data") String jsonData);

    /**
     * 请求商家列表
     *
     * @param jsonData
     * @return
     */
    @POST("mall/getSellerList")
    Call<Response<SellerList>> getSellerList(@Query("data") String jsonData);

    /**
     * 请求关注商家列表
     *
     * @param jsonData
     * @return
     */
    @POST("mall/getConcernSellerList")
    Call<Response<SellerList>> getConcernSellerList(@Query("data") String jsonData);

    /**
     * 请求商品列表
     *
     * @param jsonData
     * @return
     */
    @POST("mall/getGoodsList")
    Call<Response<GoodsList>> getGoodsList(@Query("data") String jsonData);

    /**
     * 请求本地特产分类
     */
    @POST("change/getGoodsType")
    Call<Response<List<GoodsType>>> getGoodsType(@Query("data") String jsonData);

    /**
     * 请求商家信息
     *
     * @param jsonData
     * @return
     */
    @POST("mall/getSeller")
    Call<Response<Seller>> getSeller(@Query("data") String jsonData);

    /**
     * 请求商品信息
     *
     * @param jsonData
     * @return
     */
    @POST("mall/getGoods")
    Call<Response<Goods>> getGoods(@Query("data") String jsonData);

    /**
     * 秒杀商城
     */
    @POST("mall/secondKillMall")
    Call<Response<DataList<SecondKill>>> secondKillMall(@Query("data") String jsonData);

    /**
     * 获取商品关注
     */
    @POST("mall/getConcernGoodsList")
    Call<Response<GoodsList>> getConcernGoodsList(@Query("data") String jsonData);

    /**---------------------------------------------------MALL END--------------------------------------------------------------------- */

    /**---------------------------------------------------ORDER START--------------------------------------------------------------------- */

    /**
     * 请求评论列表
     *
     * @param jsonData
     * @return
     */
    @POST("order/getCommentList")
    Call<Response<CommentList>> getCommentList(@Query("data") String jsonData);

    /**
     * 请求购物车数据列表
     *
     * @param jsonData
     * @return
     */
    @POST("order/getShoppingCarList")
    Call<Response<ShoppingCarList>> getShoppingCarList(@Query("data") String jsonData);

    /**
     * 移除购物车数据
     *
     * @param jsonData
     * @return
     */
    @POST("order/removeShoppingCar")
    Call<Response> removeShoppingCar(@Query("data") String jsonData);

    /**
     * 更新购物车数据
     *
     * @param jsonData
     * @return
     */
    @POST("order/updateShoppingCar")
    Call<Response> updateShoppingCar(@Query("data") String jsonData);

    /**
     * 向购物车添加商品
     *
     * @param jsonData
     * @return
     */
    @POST("order/putShoppingCar")
    Call<Response> putShoppingCar(@Query("data") String jsonData);


    /**
     * 生成临时订单
     *
     * @param jsonData
     * @return
     */
    @POST("order/createTempOrderList")
    Call<Response<OrderList>> createTempOrderList(@Query("data") String jsonData);

    /**
     * 提交订单
     *
     * @param jsonData
     * @return
     */
    @POST("order/confirmOrder")
    Call<Response> confirmOrder(@Query("data") String jsonData);

    /**
     * 获取订单列表
     *
     * @param jsonData
     * @return
     */
    @POST("order/getOrderList")
    Call<Response<OrderList>> getOrderList(@Query("data") String jsonData);

    /**
     * 获取退单列表
     *
     * @param jsonData
     * @return
     */
    @POST("order/getBackOrderList")
    Call<Response<BackOrderList>> getBackOrderList(@Query("data") String jsonData);


    /**
     * 获取退单详情
     *
     * @param jsonData
     * @return
     */
    @POST("order/getBackOrder")
    Call<Response<BackOrder>> getBackOrder(@Query("data") String jsonData);

    /**
     * 获取订单信息
     *
     * @param jsonData
     * @return
     */
    @POST("order/getOrder")
    Call<Response<Order>> getOrder(@Query("data") String jsonData);


    /**
     * 取消订单
     *
     * @param jsonData
     * @return
     */
    @POST("order/cancelOrder")
    Call<Response> cancelOrder(@Query("data") String jsonData);

    /**
     * 确认收货
     *
     * @param jsonData
     * @return
     */
    @POST("order/confirmReceipt")
    Call<Response> confirmReceipt(@Query("data") String jsonData);

    /**
     * 提交评论
     *
     * @param jsonData
     * @return
     */
    @POST("order/putComment")
    Call<Response> putComment(@Query("data") String jsonData);

    /**
     * 订单支付
     *
     * @param jsonData
     * @return
     */
    @POST("order/payOrders")
    Call<Response<PayModel>> payOrders(@Query("data") String jsonData);

    /**
     * 获取商家退单列表
     *
     * @param jsonData
     * @return
     */
    @POST("order/getSellerBackOrderList")
    Call<Response<BackOrderList>> getSellerBackOrderList(@Query("data") String jsonData);

    /**
     * 获取商家订单列表
     *
     * @param jsonData
     * @return
     */
    @POST("order/getSellerOrderList")
    Call<Response<DataList<Order>>> getSellerOrderList(@Query("data") String jsonData);

    /**
     * 申请退单
     *
     * @param jsonData
     * @return
     */
    @POST("order/applyBackOrder")
    Call<Response> applyBackOrder(@Query("data") String jsonData);

    /**
     * 商家确认订单
     *
     * @param jsonData
     * @return
     */
    @POST("order/sellerConfirmOrder")
    Call<Response> sellerConfirmOrder(@Query("data") String jsonData);


    /**
     * 商家退单审核
     *
     * @param jsonData
     * @return
     */
    @POST("order/sellerBackOrderVerify")
    Call<Response> sellerBackOrderVerify(@Query("data") String jsonData);

    @POST("message/getMessageList")
    Call<Response<OrderMsgModel>> getMsgList(@Query("data") String jsonData);

    @POST("message/getMessageHome")
    Call<Response<MsgHomeModel>> getMsgTypes(@Query("data") String jsonData);

    @POST("message/msgDetail")
    Call<Response<SystemMsgDtModel>> getMsgDetailSystem(@Query("data") String jsonData);

    @POST("message/msgDetail")
    Call<Response<AssetsMsgDtModel>> getMsgDetailAssets(@Query("data") String jsonData);

    @POST("message/msgDetail")
    Call<Response<OrderMsgDtModel>> getMsgDetailOrder(@Query("data") String jsonData);

    @POST("message/delMessage")
    Call<Response<DeleteMsgModel>> deleteMsg(@Query("data") String jsonData);

    /**
     * 获取融云聊天列表userinfo
     */
    @POST("users/getUserInfo")
    Call<Response<RongUserInfo>> getRongUserInfo(@Query("data") String jsonData);

    /**
     * 店铺设置数据
     */
    @POST("mall/getstoreInfo")
    Call<Response<StoreSettingInfo>> getstoreInfo(@Query("data") String jsonData);

    /**
     * 店铺设置数据
     */
    @POST("mall/shopManage")
    Call<Response> shopManage(@Query("data") String jsonData);

    /**
     * 商品管理列表数据
     */
    @POST("mall/getGodosListByStatus")
    Call<Response<GoodManageResModel>> getGodosListByStatus(@Query("data") String jsonData);

    /**
     * 发布商品
     */
    @POST("mall/publishGoods")
    Call<Response<PostGoodResModel>> publishGoods(@Query("data") String jsonData);

    /**
     * 获取发布商品数据
     */
    @POST("mall/getGoodsdetails")
    Call<Response<PostGoodResModel>> getGoodsdetails(@Query("data") String jsonData);

    /**
     * 删除商品
     */
    @POST("mall/delMallOfGoods")
    Call<Response<PostGoodResModel>> delMallOfGoods(@Query("data") String jsonData);

    /**
     * 下架商品
     */
    @POST("mall/soldOutGoods")
    Call<Response<PostGoodResModel>> soldOutGoods(@Query("data") String jsonData);

    /**
     * 添加库存
     */
    @POST("mall/reStock")
    Call<Response<PostGoodResModel>> reStock(@Query("data") String jsonData);

    /**
     * 上架商品
     */
    @POST("mall/soldUpGoods")
    Call<Response<PostGoodResModel>> soldUpGoods(@Query("data") String jsonData);

    /**
     * 推广商品
     */
    @POST("mall/applyGoods")
    Call<Response<PostGoodResModel>> applyGoods(@Query("data") String jsonData);

    /**
     * 获取秒杀时间段
     */
    @POST("mall/getCashOfTime")
    Call<Response<SecKillTimeResModel>> getCashOfTime(@Query("data") String jsonData);

    /**
     * 获取积分在线充值数据
     */
    @POST("integral/rechargeOnLine")
    Call<Response<RechargeScoreOnlineModel>> rechargeOnLine(@Query("data") String jsonData);

    /**
     * 获取积分在线充值数据
     */
    @POST("integral/getIntegralList")
    Call<Response<MyScoreResModel>> getIntegralList(@Query("data") String jsonData);

    /**
     * 实卡充值
     */
    @POST("integral/recharge")
    Call<Response<RechargeCardModel>> recharge(@Query("data") String jsonData);

    /**
     * 在线充值
     */
    @POST("recharge/payRechargeScore")
    Call<Response<PayModel>> payRechargeScore(@Query("data") String jsonData);

    /**
     * 检验被赠送者账号
     */
    @POST("integral/checkPresenters")
    Call<Response<SendScoreDtResModel>> checkPresenters(@Query("data") String jsonData);

    /**
     * 获取收取者信息
     */
    @POST("integral/getPresenterInfo")
    Call<Response<SendScoreDtResModel>> getPresenterInfo(@Query("data") String jsonData);

    /**
     * 赠送积分
     */
    @POST("integral/sendScore")
    Call<Response> sendScore(@Query("data") String jsonData);

    /**
     * 接收通过qr赠送积分
     */
    @POST("integral/receiveQRScore")
    Call<Response> receiveQRScore(@Query("data") String jsonData);

    //银联支付成功后通知我方服务器
    @POST("order/synchroYiLianNotify")
    Call<Response> synchroYiLianNotify(@Query("data") String jsonData);


    /**---------------------------------------------------ORDER END--------------------------------------------------------------------- */
}
