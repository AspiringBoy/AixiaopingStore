package com.axp.axpseller.activitys.order;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.order.OrderDetailFragment;

/**
 * Created by xu on 2016/7/18.
 * 订单详情界面
 */
public class OrderDetailActivity extends BaseActivity {

    /**
     * 状态
     */
    public static final String KEY_STATUS = "KEY_STATUS";
    /**
     * 订单id
     */
    public static final String KEY_ORDER_ID = "KEY_ORDER_ID";


    OrderDetailFragment fragment = new OrderDetailFragment();
    public static OrderDetailActivity orderDetailActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_detail);
        orderDetailActivity = this;
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
