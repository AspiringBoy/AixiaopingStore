package com.axp.axpseller.fragments.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.axp.axpseller.Constants;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.models.GoodsList;
import com.axp.axpseller.models.bean.GetGoodsListBean;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.views.adapters.HomeScoreExchangeGoodsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/21.
 * 首页积分兑换
 */
public class HomeScoreExchangeGoodsFragment extends BaseFragment {

    View mView;
    @BindView(net.aixiaoping.library.R.id.list)
    RecyclerView mRecyclerView;
    SuperRecyclerViewAdapter mAdapter;
    GoodsList mData = new GoodsList();
    int mTag;

    public static final HomeScoreExchangeGoodsFragment newInstance(int tag) {
        HomeScoreExchangeGoodsFragment fragment = new HomeScoreExchangeGoodsFragment();
        fragment.mTag = tag;

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(net.aixiaoping.library.R.layout.fragment_home_score_exchange, container, false);
        ButterKnife.bind(this, mView);

        GetGoodsListBean request = new GetGoodsListBean();
        request.setPageIndex(1);
        request.setMallTyle(Constants.MALL_SOCRE);
        request.setTag(new int[]{mTag});

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new HomeScoreExchangeGoodsAdapter(getActivity(), mData);
        mRecyclerView.setAdapter(mAdapter);

        loadData(request);

        return mView;
    }

    private void loadData(GetGoodsListBean bean) {
        Request request = new Request();
        request.setData(bean);
        RXUtils.request(getContext(), request, "getGoodsList", new RecyclerViewSubscriber<Response<GoodsList>>(mAdapter, mData) {
            @Override
            public void onSuccess(Response<GoodsList> response) {
                mAdapter.addDataListNotifyDataSetChanged(response.getData());
            }

        });
    }

}
