package com.axp.axpseller.activitys.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.sellerinfo.StayExchangeFragment;

/**
 * Created by Dong on 2016/7/14.
 * 待兑换，积分兑换
 */
public class StayExchangeActivity extends BaseActivity{

    public static final String KEY_ORDER_ID = "KEY_ORDER_ID";
    public static final String KEY_EXCHANGE_CODE = "KEY_EXCHANGE_CODE";

    StayExchangeFragment fragment = new StayExchangeFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,fragment).commit();
    }
}
