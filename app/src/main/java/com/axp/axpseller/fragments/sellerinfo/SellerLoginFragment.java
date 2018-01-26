package com.axp.axpseller.fragments.sellerinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.axp.axpseller.R;
import com.axp.axpseller.activitys.HomeActivity;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.dao.sp.SellerInfoSp;
import com.axp.axpseller.models.SellersInfo;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.utils.T;
import com.axp.axpseller.views.dialogs.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/12/29.
 */
public class SellerLoginFragment extends BaseFragment {

    View mView;
    @BindView(R.id.tv_name)
    EditText tvName;
    @BindView(R.id.tv_pwd)
    EditText tvPwd;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.activity_store_login, container, false);
        ButterKnife.bind(this, mView);
        rlBack.setFocusable(false);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        tvPwd.setFocusable(true);
        tvName.setFocusable(true);
    }

    @OnClick(R.id.btn_login)
    public void onClick() {

        if (StringUtils.isBlank(tvName.getText().toString()) && StringUtils.isBlank(tvPwd.getText().toString())) {
            T.showShort(getActivity(), "请输入账号和密码");
        } else {
            if (StringUtils.isBlank(tvName.getText().toString())) {
                T.showShort(getActivity(), "请输入账号");
            } else {
                if (StringUtils.isBlank(tvPwd.getText().toString())) {
                    T.showShort(getActivity(), "请输入密码");
                } else {
                    login();
                }
            }
        }
    }

    private void login() {
        Request<SellersInfo> request = new Request<>();
        SellersInfo sellerInfo = new SellersInfo();
        sellerInfo.setLoginname(tvName.getText().toString());
        sellerInfo.setPassword(tvPwd.getText().toString());
        request.setData(sellerInfo);
        RXUtils.request(getActivity(), request, "logins", new SupportSubscriber<Response<SellersInfo>>() {
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
            public void onNext(Response<SellersInfo> userInfoResponse) {
                SellerInfoSp.setSellersInfo(userInfoResponse.getData());
                Intent intent = new Intent(getActivity(), HomeActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("sellerId",userInfoResponse.getData().getSellerId());
//                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                T.showShort(getActivity(), response.getMessage());
            }
        });

    }
}
