package com.axp.axpseller.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.RecyclerViewModel;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.view.IShowPreviewTopFg;
import com.axp.axpseller.views.adapters.GoodsAdapter;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

/**
 * Created by YY on 2018/1/2.
 */
public class PreviewTopFragment extends BaseFragment implements IShowPreviewTopFg {

    private View fgView;
    private SuperRecyclerView mRclv;
    private GoodsAdapter mAdapter;
    private DataList<RecyclerViewModel> mDataList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgView = inflater.inflate(R.layout.fragment_preview_top,container,false);

        return fgView;
    }

    @Override
    public void showView(DataList<RecyclerViewModel> dataList) {
        mDataList.getDataList().addAll(dataList.getDataList());
        mDataList.setPageSize(dataList.getPageSize());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void initView() {
        mRclv = ((SuperRecyclerView) fgView.findViewById(R.id.list));
    }

    @Override
    public void initData() {
        mDataList = new DataList<>();
        mRclv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new GoodsAdapter(getActivity(), mDataList);
        mRclv.setAdapter(mAdapter);
        mRclv.setDifferentSituationOptionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }
}
