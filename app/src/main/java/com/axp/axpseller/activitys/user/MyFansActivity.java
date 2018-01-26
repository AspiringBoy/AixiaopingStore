package com.axp.axpseller.activitys.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.user.MyFansFragment;

/**
 * Created by Dong on 2016/7/4.
 * 我的粉丝列表
 */
public class MyFansActivity extends BaseActivity {
    MyFansFragment fragment = new MyFansFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,fragment).commit();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        bundle.getString("number");
        fragment.setArguments(bundle);
    }
}
