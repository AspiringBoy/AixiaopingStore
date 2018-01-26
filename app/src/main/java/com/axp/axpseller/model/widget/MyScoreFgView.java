package com.axp.axpseller.model.widget;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.axp.axpseller.R;
import com.axp.axpseller.core.RecyclerViewModel;
import com.axp.axpseller.fragments.user.MyScoreFragment;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.views.adapters.MyScoreFgRclvAdapter;
import com.axp.axpseller.views.custom.CustomToolbar;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2018/1/10.
 */
public class MyScoreFgView implements CustomToolbar.OnImgClick, SwipeRefreshLayout.OnRefreshListener, OnMoreListener {
    private MyScoreFragment mFragment;
    private View mView;
    private CustomToolbar toolbar;
    private SuperRecyclerView mRclv;
    private DataList<RecyclerViewModel> mDataList;
    private List<RecyclerViewModel> mList;
    private MyScoreFgRclvAdapter mAdapter;

    public MyScoreFgView(Fragment fragment, View mView) {
        this.mFragment = ((MyScoreFragment) fragment);
        this.mView = mView;
        initView();
    }

    private void initView() {
        toolbar = ((CustomToolbar) mView.findViewById(R.id.toolbar));
        mRclv = ((SuperRecyclerView) mView.findViewById(R.id.rclv));
    }

    public void initData(){
        toolbar.setOnImgClick(this);
        mDataList = new DataList<>();
        mList = new ArrayList<>();
        mDataList.setDataList(mList);
        mAdapter = new MyScoreFgRclvAdapter(mFragment.getActivity(),mDataList);
        mRclv.setLayoutManager(new LinearLayoutManager(mFragment.getActivity(),LinearLayoutManager.VERTICAL,false));
        mRclv.setAdapter(mAdapter);
        mRclv.setRefreshListener(this);
        mRclv.setupMoreListener(this);
    }

    public void bindView(DataList<RecyclerViewModel> dataList){
        mDataList.getDataList().clear();
        mDataList.getDataList().addAll(dataList.getDataList());
        mDataList.setPageSize(dataList.getPageSize());
        mDataList.setPageIndex(dataList.getPageIndex());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLeftImgClick() {
        mFragment.getActivity().finish();
    }

    @Override
    public void onRightImgClick() {

    }

    //刷新
    @Override
    public void onRefresh() {
        mFragment.refreshData();
    }

    //加载更多
    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        mFragment.loadMore();
    }
}
