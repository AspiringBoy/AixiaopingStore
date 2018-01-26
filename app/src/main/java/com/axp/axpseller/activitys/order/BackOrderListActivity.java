package com.axp.axpseller.activitys.order;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.order.BackOrderListFragment;

/**
 * Created by xu on 2016/7/15.
 * 退单列表
 */
public class BackOrderListActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_back_order_list);

        BackOrderListFragment fragment = new BackOrderListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
