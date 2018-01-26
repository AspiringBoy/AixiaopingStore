package com.axp.axpseller.activitys.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.mall.FreeGoodsFragment;

/**
 * Created by xu on 2016/7/13.
 * 免单商品列表
 */
public class FreeGoodsActivity extends BaseActivity {

    FreeGoodsFragment mFragment = new FreeGoodsFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_free_goods);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, mFragment).commit();
    }
}
