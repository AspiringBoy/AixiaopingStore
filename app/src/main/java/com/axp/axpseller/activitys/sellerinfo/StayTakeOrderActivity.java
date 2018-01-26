package com.axp.axpseller.activitys.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.sellerinfo.StayTakeOrderFragment;

/**
 * Created by Dong on 2016/7/14.
 * 待发货
 */
public class StayTakeOrderActivity extends BaseActivity{

    public static final String KEY_ORDER_ID = "KEY_ORDER_ID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,new StayTakeOrderFragment()).commit();
    }
}
