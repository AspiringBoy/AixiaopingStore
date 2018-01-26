package com.axp.axpseller.activitys.sellerinfo;

import android.os.Bundle;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.sellerinfo.PreviewFragment;

public class PreviewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        getSupportFragmentManager().beginTransaction().replace(R.id.fg_containner,new PreviewFragment()).commit();
    }
}
