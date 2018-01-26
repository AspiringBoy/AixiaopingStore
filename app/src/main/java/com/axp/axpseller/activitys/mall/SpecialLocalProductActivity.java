package com.axp.axpseller.activitys.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.mall.SpecialLocalProductFragment;

/**
 * Created by xu on 2016/7/19.
 * 特产速递
 */
public class SpecialLocalProductActivity extends BaseActivity {

    SpecialLocalProductFragment fragment = new SpecialLocalProductFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_special_local_product);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
