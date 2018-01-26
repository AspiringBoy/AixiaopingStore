package com.axp.axpseller.activitys.order;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.order.BackOrderDetailFragment;

/**
 * Created by xu on 2016/7/25.
 * 退单详情
 */
public class BackOrderDetailActivity extends BaseActivity {

    /**
     * 退单id
     */
    public static final String KEY_BACK_ORDER_ITEM_ID = "KEY_BACK_ORDER_ITEM_ID";

    BackOrderDetailFragment fragment = new BackOrderDetailFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_back_order_detail);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }
}
