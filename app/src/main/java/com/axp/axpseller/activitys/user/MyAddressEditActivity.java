package com.axp.axpseller.activitys.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.user.MyAddressEditFragment;
import com.axp.axpseller.models.Address;

/**
 * Created by Dong on 2016/7/1.
 * 修改地址或者添加地址
 */
public class MyAddressEditActivity extends BaseActivity {
    MyAddressEditFragment fragment = new MyAddressEditFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change, fragment).commit();
        Intent intent = getIntent();
        Bundle bundle1 = new Bundle();
        Bundle bundle = intent.getExtras();
        if (bundle.getInt("type") == 2) {
            Address address = (Address) bundle.getSerializable("address");
            bundle1.putInt("type",2);
            bundle1.putSerializable("mAddress",address);
            fragment.setArguments(bundle1);
        }else if(bundle.getInt("type") == 1){
            bundle1.putInt("type",1);
            fragment.setArguments(bundle1);

        }else{
            bundle1.putInt("type",3);
            fragment.setArguments(bundle1);
        }
    }
}
