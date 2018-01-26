package com.axp.axpseller.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.AssetsMsgDtFragment;

/**
 * Created by YY on 2017/5/9.
 */
public class AssetsMsgDtActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_msg_dt);
        getSupportFragmentManager().beginTransaction().replace(R.id.recharge_msg_dt_replace,new AssetsMsgDtFragment()).commit();
    }
}
