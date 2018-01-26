package com.axp.axpseller.activitys.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.sellerinfo.SellerHandlerBackOrderStatusFragment;

/**
 * Created by xu on 2016/7/25.
 * 商家处理用户退单界面列表
 */
public class SellerHandlerBackOrderListActivity extends BaseActivity {

//    SellerHandlerBackOrderListFragment fragment = new SellerHandlerBackOrderListFragment();
    SellerHandlerBackOrderStatusFragment fragment = new SellerHandlerBackOrderStatusFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_seller_handler_back_order_list);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
