package com.axp.axpseller.activitys;

import android.content.ComponentName;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro2;

import com.axp.axpseller.R;
import com.axp.axpseller.fragments.GlideFragment;
import com.axp.axpseller.utils.AppUtils;


/**
 * Created by xu on 2016/5/5.
 * 引导页
 */
public class GuideActivity extends AppIntro2 {


    @Override
    public void init(@Nullable Bundle savedInstanceState) {


        addSlide(GlideFragment.newInstance(R.mipmap.glide1));
        addSlide(GlideFragment.newInstance(R.mipmap.glide2));
        addSlide(GlideFragment.newInstance(R.mipmap.glide3));
        addSlide(GlideFragment.newInstance(R.mipmap.glide4, true));
//        setZoomAnimation();
//        setSlideOverAnimation();

        //setProgressIndicator();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        Bundle bundle = new Bundle();
        AppUtils.toActivity(this, LoginOptionActivity.class);
        finish();
    }

    @Override
    public void onSlideChanged() {

    }
}
