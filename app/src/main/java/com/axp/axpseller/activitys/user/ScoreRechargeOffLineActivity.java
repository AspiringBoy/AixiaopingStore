package com.axp.axpseller.activitys.user;

import android.content.Intent;
import android.os.Bundle;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.user.ScoreRechargeOffLineFragment;
import com.axp.axpseller.models.bean.ScanResultModel;
import com.axp.axpseller.utils.DialogUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.greenrobot.eventbus.EventBus;

/**
 * 实卡充值
 */
public class ScoreRechargeOffLineActivity extends BaseActivity {

    private String axpCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_recharge_off_line);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new ScoreRechargeOffLineFragment()).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                DialogUtil.showNoticDialog(this,"未扫到相关内容!");
            } else {
                if (result.getContents().contains("axpCode")) {
                    axpCode = result.getContents().substring("axpCode:".length());
                    ScanResultModel model = new ScanResultModel();
                    model.setAxpCode(axpCode);
                    EventBus.getDefault().post(model);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
