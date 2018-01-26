package com.axp.axpseller.activitys.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.activitys.LoginActivity;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.utils.AppUtils;

/**
 * Created by Dong on 2016/12/29.
 */
public class ComeLoginActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.toActivity(this, LoginActivity.class);
        this.finish();
    }
}
