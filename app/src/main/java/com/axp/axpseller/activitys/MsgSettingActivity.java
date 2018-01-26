package com.axp.axpseller.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.MsgSettingFragment;

/**
 * Created by YY on 2017/5/10.
 */
public class MsgSettingActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_setting);
        getSupportFragmentManager().beginTransaction().replace(R.id.msg_setting_replace, new MsgSettingFragment()).commit();
    }
}
