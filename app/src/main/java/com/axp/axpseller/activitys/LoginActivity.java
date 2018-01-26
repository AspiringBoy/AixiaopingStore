package com.axp.axpseller.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.dao.sp.SellerInfoSp;
import com.axp.axpseller.fragments.sellerinfo.SellerLoginFragment;
import com.axp.axpseller.models.SellersInfo;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.RXUtils;

/**
 * Created by Dong on 2016/12/5.
 * 商家版登录
 */
public class LoginActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,new SellerLoginFragment()).commit();
    }

   /* private void login(){
        Request<SellersInfo> request = new Request<>();
        SellersInfo sellerInfo = new SellersInfo();
        sellerInfo.setLoginname(tvName.getText().toString());
        sellerInfo.setPassword(tvPwd.getText().toString());
        request.setData(sellerInfo);
        RXUtils.request(LoginActivity.this, request, "logins", new SupportSubscriber<Response<SellersInfo>>() {
            LoadingDialog dialog;

            @Override
            public void onStart() {
                dialog = new LoadingDialog(LoginActivity.this);
                dialog.show();
            }

            @Override
            public void onCompleted() {
                dialog.dismiss();
            }
            @Override
            public void onNext(Response<SellersInfo> userInfoResponse) {
                SellerInfoSp.setSellersInfo(userInfoResponse.getData());
                AppUtils.toActivity(LoginActivity.this,HomeActivity.class);
                LoginActivity.this.finish();
            }
        });
    }*/

    private void getSellersInfo(String sellersId,String adminuserId){
        Request<SellersInfo> request = new Request<>();
        SellersInfo sellerInfo = new SellersInfo();
        sellerInfo.setSellerId(sellersId);
        sellerInfo.setAdminuserId(adminuserId);
        request.setData(sellerInfo);
        RXUtils.request(LoginActivity.this, request, "getSellersInfo", new SupportSubscriber<Response<SellersInfo>>() {

            @Override
            public void onNext(Response<SellersInfo> userInfoResponse) {
                //存储商家信息
                AppUtils.toActivity(LoginActivity.this,HomeActivity.class);

            }
        });
    }
}
