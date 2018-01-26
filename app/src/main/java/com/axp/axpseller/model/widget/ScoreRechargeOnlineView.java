package com.axp.axpseller.model.widget;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.axp.axpseller.Constants;
import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.MyPay;
import com.axp.axpseller.R;
import com.axp.axpseller.activitys.user.ScoreRechargeSuccessActivity;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.managers.pay.PayListener;
import com.axp.axpseller.managers.pay.PayModel;
import com.axp.axpseller.models.bean.BankPayResultBean;
import com.axp.axpseller.models.bean.PayBean;
import com.axp.axpseller.models.bean.RechargeMoneyModel;
import com.axp.axpseller.models.bean.RechargeScoreOnlineModel;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.CalcUtils;
import com.axp.axpseller.utils.DialogUtil;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.views.adapters.RechargeMoneyItemGvAdapter;
import com.axp.axpseller.views.custom.CustomDialog;
import com.axp.axpseller.views.custom.CustomToolbar;
import com.axp.axpseller.views.custom.ScrollGridView;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.google.gson.Gson;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2018/1/9.
 */
public class ScoreRechargeOnlineView implements CustomToolbar.OnImgClick, View.OnClickListener, AdapterView.OnItemClickListener, PayListener {
    private CustomToolbar toolbar;
    private Activity mActivity;
    private View mFgView;
    private TextView scoreLeftTv, getScoreTv;
    private ScrollGridView scoreCardGv;
    private EditText rechargeMoneyEdt;
    private Button rechargeBtn;
    private double rechargeMoney = -1;
    private BroadcastReceiver payResultReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //接收易联支付插件的广播回调
            String action = intent.getAction();
            if (!Constants.PAY_RESULT_RECEIVER_ACTION.equals(action)) {
                Log.d("雨落无痕丶", "接收到广播，但与注册的名称不一致[" + action + "]");
                return;
            }
            //获取支付结果，result为手机支付返回的json数据
            String result = intent.getExtras().getString("upPay.Rsp");
            Log.d("雨落无痕丶", "onReceive: " + result);
            BankPayResultBean payResultBean = new Gson().fromJson(result, BankPayResultBean.class);
            if ("0000".equals(payResultBean.getRespCode())) {
                goToIntent();
            } else {
                DialogUtil.showNoticDialog(mActivity, "支付失败!");
            }
        }
    };
    private RechargeMoneyItemGvAdapter mAdapter;
    private List<RechargeMoneyModel> mList = new ArrayList<>();
    private String scoreProportion;
    private String payType = Constants.PAY_BANK;
    private CustomDialog payNoticeDialog;
    private String payModeStr;
    private int imgResId;
    private int curPos = 0;
    private int inputType = 0;

    public ScoreRechargeOnlineView(Activity activity, View fgView) {
        this.mActivity = activity;
        this.mFgView = fgView;
        initView();
    }

    private void initView() {
        toolbar = ((CustomToolbar) mFgView.findViewById(R.id.toolbar));
        scoreLeftTv = ((TextView) mFgView.findViewById(R.id.left_score_tv));
        getScoreTv = ((TextView) mFgView.findViewById(R.id.can_get_score_tv));
        scoreCardGv = ((ScrollGridView) mFgView.findViewById(R.id.recharge_card_gv));
        rechargeMoneyEdt = ((EditText) mFgView.findViewById(R.id.recharge_money_edt));
        rechargeBtn = ((Button) mFgView.findViewById(R.id.recharge_btn));
        regiesterReceiver();
    }

    public void initData() {
        toolbar.setOnImgClick(this);
        rechargeBtn.setOnClickListener(this);
        mAdapter = new RechargeMoneyItemGvAdapter(mActivity, mList);
        scoreCardGv.setAdapter(mAdapter);
        scoreCardGv.setOnItemClickListener(this);
        rechargeMoneyEdt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    inputType = 0;
                }
                return false;
            }
        });
        rechargeMoneyEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!StringUtils.isBlank(editable.toString())) {
                    rechargeMoney = Double.parseDouble(editable.toString());
                    getScoreTv.setText(CalcUtils.multiply(rechargeMoney, Double.parseDouble(scoreProportion), 2, RoundingMode.HALF_UP) + "积分");
//                getScoreTv.setText(Double.parseDouble(editable.toString()) * Double.parseDouble(scoreProportion) + "积分");
                    if (inputType == 0) {
                        curPos = -1;
                        mAdapter.changeSelect(curPos);
                    }
                } else {
//                    rechargeMoney = Double.parseDouble(mList.get(curPos).getRechargeMoney());
                    rechargeMoney = 0;
                    getScoreTv.setText(0 + "积分");
                }
            }
        });
    }

    public void bindView(RechargeScoreOnlineModel model) {
        scoreProportion = model.getScoreProportion();
        scoreLeftTv.setText(model.getScoreBanlance());
        mList.clear();
        mList.addAll(model.getScoreRechargeList());
        rechargeMoney = Double.parseDouble(mList.get(0).getRechargeMoney());
        getScoreTv.setText(mList.get(0).getRechargeScore() + "积分");
        inputType = 1;
        rechargeMoneyEdt.setText(rechargeMoney+"");
//        getScoreTv.setText(CalcUtils.multiply(rechargeMoney,Double.parseDouble(scoreProportion),2, RoundingMode.HALF_UP) + "积分");
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLeftImgClick() {
        mActivity.finish();
    }

    @Override
    public void onRightImgClick() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //充值
            case R.id.recharge_btn:
                showPayDialog();
                break;
            //edittext点击监听
            case R.id.recharge_money_edt:
                inputType = 0;
                break;
        }
    }

    private void showPayDialog() {
        if (Constants.PAY_BANK.equals(payType)) {
            imgResId = R.drawable.yinlian;
            payModeStr = "银联支付";
        } else if (Constants.PAY_WEIXIN.equals(payType)) {
            imgResId = R.drawable.weixin;
            payModeStr = "微信支付";
        } else if (Constants.PAY_ALIPAY.equals(payType)) {
            imgResId = R.drawable.zhifubao;
            payModeStr = "支付宝支付";
        }
        payNoticeDialog = DialogUtil.showCustomDialog(mActivity, R.style.customDialogStyle, R.layout.score_recharge_online_dialog, 255, 305, "￥" + rechargeMoney, R.id.recharge_money_tv, "可获" + getScoreTv.getText().toString(), R.id.get_score_tv, payModeStr, R.id.pay_mode_tv, imgResId, R.id.pay_mode_iv, new View.OnClickListener() {
            private CustomDialog choosePayTypeDialog;

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    //切换支付方式
                    case R.id.pay_rll_tag:
                        payNoticeDialog.dismiss();
                        choosePayTypeDialog = DialogUtil.showCustomDialog(mActivity, R.style.customDialogStyle, R.layout.score_recharge_choose_paymode, 255, 190, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch (view.getId()) {
                                    //取消
                                    case R.id.cancel_iv:
                                        choosePayTypeDialog.dismiss();
                                        break;
                                    //银联
                                    case R.id.pay_yinlian_rll:
                                        choosePayTypeDialog.dismiss();
                                        payType = Constants.PAY_BANK;
                                        showPayDialog();
                                        break;
                                    //支付宝
                                    case R.id.pay_ali_rll:
                                        choosePayTypeDialog.dismiss();
                                        payType = Constants.PAY_ALIPAY;
                                        showPayDialog();
                                        break;
                                    //微信
                                    case R.id.pay_wechat_rll:
                                        choosePayTypeDialog.dismiss();
                                        payType = Constants.PAY_WEIXIN;
                                        showPayDialog();
                                        break;
                                }
                            }
                        }, R.id.cancel_iv, R.id.pay_yinlian_rll, R.id.pay_ali_rll, R.id.pay_wechat_rll);
                        break;
                    //去支付
                    case R.id.go_pay_btn:
                        payNoticeDialog.dismiss();
                        if (rechargeMoney > 0) {
                            goToPay();
                        } else {
                            DialogUtil.showNoticDialog(mActivity, "支付金额有误!");
                        }
                        break;
                    //取消
                    case R.id.cancel_iv:
                        payNoticeDialog.dismiss();
                        break;
                }
            }
        }, R.id.pay_rll_tag, R.id.go_pay_btn, R.id.cancel_iv);
    }

    private void goToPay() {
        Request<PayBean> request = new Request<>();
        PayBean model = new PayBean();
        model.setRechargeMoney(rechargeMoney);
        model.setPayType(payType);
        model.setAdminUserId(ContextParameter.getUserInfo().getAdminuserId());
        request.setData(model);
        RXUtils.request(mActivity, request, "payRechargeScore", new SupportSubscriber<Response<PayModel>>() {
            private LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog(mActivity);
                }
                loadingDialog.show();
            }

            @Override
            public void onNext(Response<PayModel> payModelResponse) {
                if (Constants.PAY_ALIPAY.equals(payType)) {
                    MyPay.payToAlipay(mActivity, payModelResponse.getData().getSign(), ScoreRechargeOnlineView.this);
                } else if (Constants.PAY_WEIXIN.equals(payType)) {
                    MyPay.payToWeiXin(mActivity, payModelResponse.getData(), ScoreRechargeOnlineView.this);
                } else if (Constants.PAY_BANK.equals(payType)) {
                    MyPay.payToBank(payModelResponse, mActivity, "01");
                }
            }

            @Override
            public void onResponseError(Response response) {
                DialogUtil.showNoticDialog(mActivity, response.getMessage());
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }
        });
    }

    public void goToIntent() {
        Bundle bundle = new Bundle();
        String score = getScoreTv.getText().toString();
        bundle.putString("score", score.substring(0, score.indexOf("积")));
        bundle.putString("title", "积分充值");
        AppUtils.toActivity(mActivity, ScoreRechargeSuccessActivity.class, bundle);
    }

    //注册支付结果广播
    private void regiesterReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.PAY_RESULT_RECEIVER_ACTION);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        mActivity.registerReceiver(payResultReceiver, filter);
    }

    /**
     * @Title unRegisterPayecoPayBroadcastReceiver
     * @Description 注销广播接收器
     */
    public void unRegisterPayecoPayBroadcastReceiver() {
        if (payResultReceiver != null) {
            mActivity.unregisterReceiver(payResultReceiver);
            payResultReceiver = null;
        }
    }

    /**
     * @param adapterView
     * @param view
     * @param i
     * @param l           GridView的item点击监听
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        inputType = 1;
        curPos = i;
        rechargeMoney = Double.parseDouble(mList.get(i).getRechargeMoney());
        rechargeMoneyEdt.setText(rechargeMoney + "");
        getScoreTv.setText(mList.get(i).getRechargeScore() + "积分");
//        getScoreTv.setText(CalcUtils.multiply(rechargeMoney,Double.parseDouble(scoreProportion),2, RoundingMode.HALF_UP) + "积分");
        mAdapter.changeSelect(curPos);
    }

    //支付成功回调
    @Override
    public void onSuccess() {
        goToIntent();
    }

    //支付失败回调
    @Override
    public void onDefeated() {
        DialogUtil.showNoticDialog(mActivity, "支付失败!");
    }

    //支付取消回调
    @Override
    public void onCancel() {
        DialogUtil.showNoticDialog(mActivity, "支付已取消!");
    }
}
