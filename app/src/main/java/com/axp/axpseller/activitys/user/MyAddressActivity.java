package com.axp.axpseller.activitys.user;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.user.MyAddressFragment;

/**
 * Created by Dong on 2016/7/1.
 * 地址列表
 */
public class MyAddressActivity extends BaseActivity {

    MyAddressFragment fragment = new MyAddressFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,fragment).commit();
    }
}
