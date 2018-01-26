package com.axp.axpseller.activitys.user;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.user.GoodsConcernFragment;

/**
 * Created by Dong on 2016/7/24.
 * 商品关注
 */
public class GoodsConcernActivity extends BaseActivity{
    GoodsConcernFragment fragment = new GoodsConcernFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,fragment).commit();
    }
}
