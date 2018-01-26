package com.axp.axpseller.activitys.user;

import android.os.Bundle;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.user.ScoreRechargeSuccessFragment;

public class ScoreRechargeSuccessActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_recharge_success);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new ScoreRechargeSuccessFragment()).commit();
    }
}
