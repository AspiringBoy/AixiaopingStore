package com.axp.axpseller.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.axp.axpseller.Constants;
import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.models.Goods;
import com.axp.axpseller.models.bean.GetGoodsListBean;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.views.adapters.SecondKillAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/7/21.
 */
public class SecondKillFragment extends BaseFragment {
    View mView;
    GetGoodsListBean mGoodsListReqeust = new GetGoodsListBean();
    DataList<Goods> mDataList = new DataList<>();
    SecondKillAdapter mAdapter;
    @BindView(R.id.lv_scoond_kill)
    SuperRecyclerView lvScoondKill;
    private String mSecondKillId;
    public boolean startSecondKill;
    public SecondKillFragment(){

    }



    public static final SecondKillFragment newInstance(String secondKillId) {
        SecondKillFragment fragment = new SecondKillFragment();
        fragment.mSecondKillId = secondKillId;
        return fragment;
    }
    public void setStartSecondKill(boolean startSecondKill){
        this.startSecondKill = startSecondKill;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_secondkill, container, false);


        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init() {
        mAdapter = new SecondKillAdapter(getActivity(), mDataList,SecondKillFragment.this);
        mGoodsListReqeust.setMallTyle(Constants.MALL_SECOND_KILL);
        reLoadData();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        lvScoondKill.setLayoutManager(manager);

        lvScoondKill.setAdapter(mAdapter);

        lvScoondKill.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> getGoodList(), 2);

        lvScoondKill.setRefreshListener(() -> reLoadData());
        lvScoondKill.setDifferentSituationOptionListener(v -> reLoadData());

    }

    public void reLoadData(){
        mGoodsListReqeust.setPageIndex(0);
        getGoodList();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void getGoodList() {
        Request<GetGoodsListBean> request = new Request<>();
        mGoodsListReqeust.setPageIndex(mGoodsListReqeust.getPageIndex() + 1);
        mGoodsListReqeust.setSecondKillId(mSecondKillId);
        request.setData(mGoodsListReqeust);

        RXUtils.request(getActivity(), request, "getGoodsList", new RecyclerViewSubscriber<Response<DataList<Goods>>>(mAdapter, mDataList) {

            @Override
            public void onSuccess(Response<DataList<Goods>> goodsResponse) {

             mAdapter.addDataListNotifyDataSetChanged(goodsResponse.getData());
            }
        });
    }
}
