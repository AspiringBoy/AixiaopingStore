package com.axp.axpseller.presenter;

import com.axp.axpseller.view.IShowPreviewFg;

/**
 * Created by YY on 2018/1/3.
 */
public class PreviewFgPresenter {
    private IShowPreviewFg iShowPreviewFg;

    public PreviewFgPresenter(IShowPreviewFg iShowPreviewFg) {
        this.iShowPreviewFg = iShowPreviewFg;
    }

    public void showFgView(){
        iShowPreviewFg.initView();
        iShowPreviewFg.initData();
        iShowPreviewFg.operateView();
    }
}
