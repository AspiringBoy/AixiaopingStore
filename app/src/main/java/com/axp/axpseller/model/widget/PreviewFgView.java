package com.axp.axpseller.model.widget;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.axp.axpseller.R;
import com.lzy.widget.VerticalSlide;

/**
 * Created by YY on 2018/1/3.
 */
public class PreviewFgView implements View.OnClickListener, VerticalSlide.OnShowNextPageListener {
    private final Toolbar toolBar;
    private final VerticalSlide mDragLayout;
    private final Activity mActivity;
    private View fgView;

    public PreviewFgView(View fgView, Activity activity) {
        this.fgView = fgView;
        mActivity = activity;
        toolBar = ((Toolbar) fgView.findViewById(R.id.preview_fg_toolbar));
        mDragLayout = ((VerticalSlide) fgView.findViewById(R.id.dragLayout));
    }

    public void bindView(){
        toolBar.setNavigationOnClickListener(this);
        mDragLayout.setOnShowNextPageListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.preview_fg_toolbar:
                mActivity.finish();
                break;
        }
    }

    @Override
    public void onShowNextPage() {

    }
}
