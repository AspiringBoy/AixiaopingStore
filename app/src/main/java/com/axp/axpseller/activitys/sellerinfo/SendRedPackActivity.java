package com.axp.axpseller.activitys.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.sellerinfo.SendRedPackFragment;

/**
 * Created by Dong on 2016/12/10.
 *发红包
 */
public class SendRedPackActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,new SendRedPackFragment()).commit();
    }
}
