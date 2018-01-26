package com.axp.axpseller.fragments.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.axp.axpseller.R;
import com.axp.axpseller.activitys.user.ScoreRechargeSuccessActivity;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.bean.RechargeCardModel;
import com.axp.axpseller.models.bean.ScanResultModel;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.DialogUtil;
import com.axp.axpseller.utils.QRCodeUtil;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.views.custom.CustomToolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YY on 2018/1/11.
 */
public class ScoreRechargeOffLineFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.card_num_edt)
    EditText cardNumEdt;
    @BindView(R.id.card_pwd_edt)
    EditText cardPwdEdt;
    private View fgView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgView = inflater.inflate(R.layout.fragment_score_recharge_offline, container, false);
        ButterKnife.bind(this, fgView);
        EventBus.getDefault().register(this);
        toolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                getActivity().finish();
            }

            @Override
            public void onRightImgClick() {

            }
        });
        return fgView;
    }

    @OnClick({R.id.recharge_btn, R.id.scan_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            //立即充值
            case R.id.recharge_btn:
                if (StringUtils.isBlank(cardNumEdt.getText().toString()) || StringUtils.isBlank(cardPwdEdt.getText().toString())) {
                    DialogUtil.showNoticDialog(getActivity(), "请填写账号或密码!");
                    return;
                }
                recharge();
                break;
            //扫码充值
            case R.id.scan_btn:
                QRCodeUtil.scan(getActivity());
                break;
        }
    }

    private void recharge() {
        Request<RechargeCardModel> request = new Request<>();
        RechargeCardModel model = new RechargeCardModel();
        model.setCardNum(cardNumEdt.getText().toString());
        model.setPassword(cardPwdEdt.getText().toString());
        request.setData(model);
        RXUtils.request(getActivity(), request, "recharge", new SupportSubscriber<Response<RechargeCardModel>>() {
            @Override
            public void onNext(Response<RechargeCardModel> rechargeCardModelResponse) {
                if (rechargeCardModelResponse.getStatus() == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putString("score", rechargeCardModelResponse.getData().getScore());
                    bundle.putString("title", "积分充值");
                    AppUtils.toActivity(getActivity(), ScoreRechargeSuccessActivity.class, bundle);
                    getActivity().finish();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onResponseError(Response response) {
                DialogUtil.showNoticDialog(getActivity(), response.getMessage());
            }
        });
    }

    @Subscribe
    public void onEvent(ScanResultModel model) {
        if (model != null && model.getAxpCode() != null) {
            cardNumEdt.setText(model.getAxpCode());
            cardPwdEdt.setText("");
        }
    }
}
