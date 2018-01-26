package com.axp.axpseller.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.axp.axpseller.Constants;
import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.managers.pay.Pay;
import com.axp.axpseller.managers.pay.PayListener;
import com.axp.axpseller.managers.pay.PayModel;
import com.axp.axpseller.models.Assets;
import com.axp.axpseller.models.PayTypeModels;
import com.axp.axpseller.models.bean.AssetBean;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.L;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.utils.T;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.axp.axpseller.views.order.PayView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/12/7.
 */
public class SellerEnvelopeFragment extends BaseFragment implements PayListener {

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.edt_money)
    EditText edtMoney;
    @BindView(R.id.pv_pay)
    PayView pvPay;
    String mPayMode;
    AssetBean assetBean = new AssetBean();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_seller_enwelope, container, false);

        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getTotalMoney();
    }

    private void init(){
        pvPay.setOnPayChangeListener(new PayView.OnPayChangeListener() {
            @Override
            public void payChange(String pay) {
               mPayMode = pay;
            }
        });
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    //获得支付信息
    private void getPayRecharge(){
        Request<PayTypeModels> request = new Request();
        PayTypeModels payTypeModels = new PayTypeModels();
        payTypeModels.setPayType(mPayMode);
        payTypeModels.setTotal(edtMoney.getText().toString());
        payTypeModels.setAdminuserId(ContextParameter.getUserInfo().getAdminuserId());
        request.setData(payTypeModels);

        RXUtils.request(getActivity(), request, "payRecharge", new SupportSubscriber<Response<PayModel>>() {

            LoadingDialog dialog;

            @Override
            public void onStart() {
                dialog = new LoadingDialog(getActivity());
                dialog.show();
            }

            @Override
            public void onCompleted() {
                dialog.dismiss();
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
            }

            @Override
            public void onNext(Response<PayModel> payOrderBean) {

                if (Constants.PAY_ALIPAY.equals(mPayMode)) {
                    Pay.payToAlipay(getActivity(), payOrderBean.getData().getSign(), SellerEnvelopeFragment.this);
                } else if (Constants.PAY_WEIXIN.equals(mPayMode)) {
                    Pay.payToWeiXin(getActivity(), payOrderBean.getData(), SellerEnvelopeFragment.this);
                }
            }
        });

    }
    @OnClick(R.id.btn_envelope)
    public void onClick() {
        if(!StringUtils.isBlank(edtMoney.getText().toString())) {
            getPayRecharge();
        }else{
            T.showShort(getActivity(),"请输入金额");
        }

    }

    @Override
    public void onSuccess() {
        L.e("支付成功");
    }

    @Override
    public void onDefeated() {
        L.e("支付失败");
    }

    @Override
    public void onCancel() {

    }

    private void getTotalMoney(){
        Request<AssetBean> request = new Request();
        assetBean.setPageIndex(assetBean.getPageIndex()+1);
        assetBean.setType("6");
        request.setData(assetBean);
        RXUtils.request(getActivity(), request, "getTotalM", new SupportSubscriber<Response<Assets>>() {
            @Override
            public void onNext(Response<Assets> response) {
                tvMoney.setText(response.getData().getTotalMoney());
            }
        });


    }
}
