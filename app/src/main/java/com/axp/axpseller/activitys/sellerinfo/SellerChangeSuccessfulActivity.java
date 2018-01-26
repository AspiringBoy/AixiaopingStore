package com.axp.axpseller.activitys.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2017/2/14.
 */
public class SellerChangeSuccessfulActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_besus);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.btn_become})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                SellerChangeSuccessfulActivity.this.finish();
                break;
            case R.id.btn_become:
                SellerChangeSuccessfulActivity.this.finish();
                break;
        }
    }
}
