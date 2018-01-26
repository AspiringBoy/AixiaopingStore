package com.axp.axpseller.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;

/**
 * Created by Dong on 2016/12/5.
 * 商家版粉丝
 */
public class SellerFansFragment extends BaseFragment{

    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_seller_fans,container,false);

        return mView;
    }
}
