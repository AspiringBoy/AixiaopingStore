package com.axp.axpseller.activitys.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.CheckStatusModel;
import com.axp.axpseller.models.UpdateStoreInfo;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2017/3/1.
 */
public class InputStatusActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_connection)
    TextView tvConnection;
    @BindView(R.id.tv_remind)
    TextView tvRemind;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    int verifyStatus;
    @BindView(R.id.ll_start)
    LinearLayout llStart;
    @BindView(R.id.ll_pass)
    LinearLayout llPass;
    @BindView(R.id.ll_defeated)
    RelativeLayout llDefeated;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_input_type);
        ButterKnife.bind(this);
        getStoreVerifyStatus();
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputStatusActivity.this.finish();
            }
        });
    }

    private void getStoreVerifyStatus() {
        RXUtils.request(InputStatusActivity.this, new Request(), "storeVerifyStatus", new SupportSubscriber<Response<UpdateStoreInfo>>() {

            @Override
            public void onNext(Response<UpdateStoreInfo> updateStoreInfoResponse) {
                setData(updateStoreInfoResponse.getData());
            }
        });
    }

    private void setData(UpdateStoreInfo spdateStoreInfo) {
        tvConnection.setText(spdateStoreInfo.getTips());
        tvMessage.setText(spdateStoreInfo.getMessage());
        tvRemind.setText(spdateStoreInfo.getRemind());
        verifyStatus = spdateStoreInfo.getVerifyStatus();
        if (verifyStatus == 2) {
            llStart.setVisibility(View.GONE);
            llDefeated.setVisibility(View.GONE);
            llPass.setVisibility(View.VISIBLE);
        } else if (verifyStatus == -2) {
            llStart.setVisibility(View.GONE);
            llDefeated.setVisibility(View.VISIBLE);
            llPass.setVisibility(View.GONE);
        } else if (verifyStatus == 0) {
            llStart.setVisibility(View.VISIBLE);
            llDefeated.setVisibility(View.GONE);
            llPass.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.btn_commint)
    public void onClick() {
        if (verifyStatus == 2 || verifyStatus == -2) {
            returnCheckstatus();
        } else {
            this.finish();
        }
    }

    /**
     * 审核通过确认
     */
    private void returnCheckstatus() {
        RXUtils.request(InputStatusActivity.this, new Request(), "returnCheckstatus", new SupportSubscriber<Response<CheckStatusModel>>() {
            @Override
            public void onNext(Response<CheckStatusModel> checkStatusModelResponse) {
                String adminuserId = checkStatusModelResponse.getData().getAdminuserId();
                if ("-1".equals(adminuserId)) {
                    ContextParameter.getUserInfo().setAdminuserId("");
                } else {
                    ContextParameter.getUserInfo().setAdminuserId(adminuserId);
                }
                String sellerId = checkStatusModelResponse.getData().getSellerId();
                if (!"-1".equals(sellerId)) {
                    ContextParameter.getUserInfo().setSellerId(sellerId);
                } else {
                    ContextParameter.getUserInfo().setSellerId("");
                }
                ContextParameter.getUserInfo().setUserId(checkStatusModelResponse.getData().getUserId());
                InputStatusActivity.this.finish();
            }

            @Override
            public void onError(Throwable e) {
                Log.d("雨落无痕丶", "onError: " + e.toString());
            }
        });
    }
}