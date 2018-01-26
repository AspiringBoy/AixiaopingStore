package com.axp.axpseller.activitys.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.sellerinfo.SellerOrderFragment;

/**
 * Created by xu on 2016/7/14.
 * 商家中心中的商家订单列表
 */
public class SellerOrderActivity extends BaseActivity {

    /**
     * 用于传递订单状态
     */
    public static final String KEY_ORDER_STATUS = "KEY_ORDER_STATUS";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_seller_order);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SellerOrderFragment()).commit();
    }
}
