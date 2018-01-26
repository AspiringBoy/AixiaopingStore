package com.axp.axpseller.activitys.user;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.user.MyIntegralSaveFragment;
import com.axp.axpseller.fragments.user.MyScoreFragment;

/**
 * Created by Dong on 2016/6/29.
 * 积分列表
 */
public class MyScoreActivity extends BaseActivity {

    MyIntegralSaveFragment fragment = new MyIntegralSaveFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
       getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,new MyScoreFragment()).commit();
    }
}
