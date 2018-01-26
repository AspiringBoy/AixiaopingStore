package com.axp.axpseller.activitys.user;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.user.SettingFragment;

/**
 * Created by Dong on 2016/6/29.
 * 设置
 */
public class SettingActivity extends BaseActivity {
    SettingFragment fragment = new SettingFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,fragment).commit();
    }
}
