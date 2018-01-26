package com.axp.axpseller.fragments.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.models.SellerList;
import com.axp.axpseller.models.eventbus_message.UploadShopConcernMessage;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.views.adapters.SellerListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/23.
 * 收藏店铺
 */
public class ShopConcernFragment extends BaseFragment {

    View mView;
    @BindView(R.id.list)
    SuperRecyclerView list;
    SellerListAdapter mAdapter;
    SellerList mSellerList = new SellerList();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_shop_concern, container, false);
        EventBus.getDefault().register(this);

        ButterKnife.bind(this, mView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(layoutManager);

        mAdapter = new SellerListAdapter(getActivity(), mSellerList);

        list.setAdapter(mAdapter);

        //刷新
        list.setRefreshListener(() -> {
            loadData();
        });
        //数据请求失败后的数据重新载入
        list.setDifferentSituationOptionListener(view -> {
            loadData();
        });

        loadData();
        return mView;
    }

    @Subscribe
    public void onEvent(UploadShopConcernMessage message){
        loadData();
    }

    public void loadData(){

        Request request = new Request();
        RXUtils.request(getActivity(), request, "getConcernSellerList", new RecyclerViewSubscriber<Response<SellerList>>(mAdapter, mSellerList) {
            @Override
            public void onSuccess(Response<SellerList> sellerListResponse) {
                mSellerList.getDataList().clear();
                mAdapter.addDataListNotifyDataSetChanged(sellerListResponse.getData());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
