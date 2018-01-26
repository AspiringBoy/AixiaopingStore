package com.axp.axpseller.activitys.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2017/2/9.
 */
public class ShopsPreviewActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops_preview);
        ButterKnife.bind(this);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopsPreviewActivity.this.finish();
            }
        });
    }

    @OnClick({R.id.iv1, R.id.iv2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv1:

                AppUtils.toActivity(ShopsPreviewActivity.this,GoodsPreviewActivity.class);

                break;
            case R.id.iv2:
                AppUtils.toActivity(ShopsPreviewActivity.this,GoodsPreviewActivity.class);
                break;
        }
    }
}
