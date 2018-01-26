package com.axp.axpseller.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.models.AdvertImg;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.L;
import com.axp.axpseller.utils.StringUtils;

import net.aixiaoping.unlock.views.UnlockView;

/**
 * Created by xu on 2016/5/16.
 * 锁屏使用的Activity
 */
public class UnlockActivity extends BaseActivity {

    FrameLayout container;

    public static final String KEY_IS_APP = "KEY_IS_APP";

    /** 是否是app启动的 */
    boolean isApp = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadBundle();

        container = new FrameLayout(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(container, layoutParams);

        UnlockView mUnlockView = new UnlockView(this);
        container.addView(mUnlockView);


        mUnlockView.setOnOptionListener(new UnlockView.OnOptionListener() {
            @Override
            public void option(String option, AdvertImg advertImg) {
                if (UnlockView.OPTION_UNLOCK.equals(option)) {
                    if(isApp)
                        AppUtils.toActivity(UnlockActivity.this, HomeActivity.class);
                    finish();
                } else if (UnlockView.OPTION_MALL.equals(option)) {
                    AppUtils.toActivity(UnlockActivity.this, HomeActivity.class);
                    finish();
                } else if (UnlockView.OPTION_WEBSITE.equals(option)) {
                    if(isApp)
                        AppUtils.toActivity(UnlockActivity.this, HomeActivity.class);
                    if(advertImg == null || StringUtils.isBlank(advertImg.getUri()))
                        return;
                    AppUtils.toBrowser(UnlockActivity.this, advertImg.getUri());
                    finish();
                }
            }
        });
    }

    private void loadBundle(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            isApp = bundle.getBoolean(KEY_IS_APP, true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        L.e("test", "--start");


    }

    @Override
    protected void onStop() {
        super.onStop();

        L.e("test", "--stop");

    }

    /**
     * 禁用返回键
     */
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        container.removeAllViews();

    }
}
