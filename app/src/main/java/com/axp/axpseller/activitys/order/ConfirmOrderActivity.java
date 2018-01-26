package com.axp.axpseller.activitys.order;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.order.ConfirmOrderFragment;

/**
 * Created by xu on 2016/6/30.
 * 确认订单界面
 */
public class ConfirmOrderActivity extends BaseActivity {

    /**
     * 用于生成临时订单的Bean
     */
    public static final String KEY_ORDER_LIST = "KEY_ORDER_LIST";

    ConfirmOrderFragment mFragment = new ConfirmOrderFragment();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confirm_order);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, mFragment).commit();
    }
}
