package com.axp.axpseller.activitys.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.user.MyWithdrawFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/8/29.
 * 我的提现
 */
public class MyWithdrawActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_withdraw);
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change, new MyWithdrawFragment()).commit();
    }

    @OnClick(R.id.btn_withdraw)
    public void onClick() {

    }
}
