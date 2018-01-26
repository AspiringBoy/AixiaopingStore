package com.axp.axpseller.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.mall.ExChangeBusFragment;

/**
 * Created by Dong on 2016/10/28.
 */
public class ExChangeActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,new ExChangeBusFragment()).commit();
    }

}
