package com.axp.axpseller.fragments.goods;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.views.AXPWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/7.
 * 商品详情
 */
public class GoodsDetailFragment extends BaseFragment {

    View mView;
    @BindView(R.id.wv_web)
    AXPWebView wvWeb;

    private boolean isload = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_goods_detail, container, false);

        ButterKnife.bind(this, mView);
        return mView;
    }

    public void setUrl(String url) {

        if(!isload){
            isload = true;

            new Handler().postAtTime(new Runnable() {
                @Override
                public void run() {
                    wvWeb.getWebView().loadUrl(url);
                }
            },800);
        }




    }
}
