package com.axp.axpseller.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.RecyclerViewModel;
import com.axp.axpseller.network.DataList;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/12/28.
 */
public class SellerUnConfirmedFragment extends BaseFragment {

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.nobar)
    Toolbar nobar;
    @BindView(R.id.lv_asset_management)
    SuperRecyclerView lvAssetManagement;
    DataList<RecyclerViewModel> dataList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_unconfirmed, container, false);
        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init() {
       toolBar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               getActivity().finish();
           }
       });

    }
}
