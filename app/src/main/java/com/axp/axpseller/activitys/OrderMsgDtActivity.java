package com.axp.axpseller.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.OrderMsgDtFragment;

/**
 * Created by YY on 2017/5/9.
 */
public class OrderMsgDtActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_msg_dt);
        getSupportFragmentManager().beginTransaction().replace(R.id.order_msg_dt_replace,new OrderMsgDtFragment()).commit();
    }
}
