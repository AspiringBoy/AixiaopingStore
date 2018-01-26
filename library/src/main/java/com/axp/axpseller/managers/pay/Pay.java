package com.axp.axpseller.managers.pay;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.axp.axpseller.Constants;
import com.axp.axpseller.managers.pay.alipay.AlipayResult;
import com.axp.axpseller.utils.L;
import com.axp.axpseller.utils.NetworkUtils;
import com.axp.axpseller.utils.T;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xu on 2016/6/30.
 * 统一中的支付调用
 */
public class Pay {

    /**
     * http://blog.csdn.net/jdsjlzx/article/details/47422279
     * @param activity
     * @param payModel
     * @param listener
     */
    public static void payToWeiXin(Activity activity, PayModel payModel, PayListener listener) {

//        StringBuilder sb = new StringBuilder();
//        sb.append("appid=" + payModel.getAppId() + "&");
//        sb.append("noncestr=" + payModel.getNonceStr() + "&");
//        sb.append("package=" + payModel.getPackageValue() + "&");
//        sb.append("partnerid=" + payModel.getPartnerId() + "&");
//        sb.append("prepayid=" + payModel.getPrepayId() + "&");
//        sb.append("timestamp=" + payModel.getTimeStamp() + "&");
//        sb.append("key=" + API_KEY);
//        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
//        Log.e("orion",appSign);
//        payModel.setSign(appSign);



//        new Pay();
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(activity, null);
        // 将该app注册到微信
        boolean value = msgApi.registerApp(Constants.WEXIN_APP_ID);
        PayReq req = new PayReq();
        //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
        req.appId = payModel.getAppId();
        req.partnerId = payModel.getPartnerId();
        req.prepayId = payModel.getPrepayId();
        req.nonceStr = payModel.getNonceStr();
        req.timeStamp = payModel.getTimeStamp();
        req.packageValue = payModel.getPackageValue();
        req.sign = payModel.getSign();
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        boolean result = msgApi.sendReq(req);
        L.e("返回值：" + result);
        //分离，预防内存泄露
        msgApi.detach();


    }

    /**
     * 支付宝支付
     *
     * @param context
     * @param payInfo
     * @param listener
     */
    public static void payToAlipay(final Activity context, final String payInfo, final PayListener listener) {

        Observable.just(payInfo).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                if (!NetworkUtils.isConnected(context)) {
                    T.showShort(context, "网络没有连接哦~");
                    return false;
                }
                return true;
            }
        }).observeOn(Schedulers.computation()).map(new Func1<String, AlipayResult>() {
            @Override
            public AlipayResult call(String s) {
                PayTask payTask = new PayTask(context);
                String result = payTask.pay(s, true);

                AlipayResult payResult = new AlipayResult(result);
                return payResult;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<AlipayResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                L.e("error", "error", e);
            }

            @Override
            public void onNext(AlipayResult alipayResult) {
                String resultStatus = alipayResult.getResultStatus();
                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                if (TextUtils.equals(resultStatus, "9000")) {
                    listener.onSuccess();
                } else if (TextUtils.equals(resultStatus, "8000")) {
                    // 判断resultStatus 为非"9000"则代表可能支付失败
                    // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    T.showShort(context, "支付结果确认中");

                    listener.onSuccess();
                } else if (TextUtils.equals(resultStatus, "6001")) {
                    L.i("用户取消操作");
                    listener.onCancel();
                } else {
                    L.i("支付失败");
                    listener.onDefeated();
                }
            }
        });
    }

}
