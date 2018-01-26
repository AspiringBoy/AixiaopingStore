package com.axp.axpseller.activitys.user;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.user.ScoreRechargeOnlineFragment;

/**
 * Created by YY on 2018/1/9.
 * 在线充值
 */
public class ScoreRechargeOnlineActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_recharge_online);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new ScoreRechargeOnlineFragment()).commit();
    }
}
