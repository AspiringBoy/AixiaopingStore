package com.axp.axpseller.activitys.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.user.FeedBackFragment;

import java.util.ArrayList;

/**
 * Created by Dong on 2016/7/1.
 * 意见反馈
 */
public class FeedbackActivity extends BaseActivity{
    FeedBackFragment fragment = new FeedBackFragment();
    private ArrayList<String> mSelectPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,fragment).commit();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
