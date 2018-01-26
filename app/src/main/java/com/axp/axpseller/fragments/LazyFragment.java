package com.axp.axpseller.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by YY on 2017/12/29.
 */
public abstract class LazyFragment extends Fragment {

    public boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            onfgVisible();
        }else {
            isVisible = false;
            onInVisible();
        }
    }

    protected void onfgVisible(){
        lazyLoad();
    }

    protected void onInVisible(){};

    public abstract void lazyLoad();
}
