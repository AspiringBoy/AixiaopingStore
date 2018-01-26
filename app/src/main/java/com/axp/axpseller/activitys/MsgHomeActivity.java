package com.axp.axpseller.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.axp.axpseller.R;
import com.axp.axpseller.fragments.MsgHomeFragment;

/**
 * Created by YY on 2017/5/8.
 */
public class MsgHomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_msg);
        getSupportFragmentManager().beginTransaction().replace(R.id.all_msg_replace,new MsgHomeFragment()).commit();
    }
}
