package com.axp.axpseller.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.models.Live;
import com.axp.axpseller.models.bean.MessageBean;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.L;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.views.adapters.MyLiveAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/10/18.
 */
public class LiveFragment extends BaseFragment {

    @BindView(R.id.lv_live)
    SuperRecyclerView lvLive;
    private View mView;
    MyLiveAdapter adapter;
    DataList<Live> dataList = new DataList<>();
    MessageBean mMessageListReqeust = new MessageBean();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_live_list, container, false);

        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init() {
        adapter = new MyLiveAdapter(getActivity(),dataList);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        lvLive.setLayoutManager(manager);

        lvLive.setAdapter(adapter);


        getLiveList();
        lvLive.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> getLiveList(), 2);

        lvLive.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
                L.e("====执行了老子=====");
            }
        });
    }

    private void load(){
        dataList.getDataList().clear();
        mMessageListReqeust.setPageIndex(0);
        getLiveList();
    }

    private void getLiveList() {
        Request<MessageBean> request = new Request<>();
        mMessageListReqeust.setPageIndex(mMessageListReqeust.getPageIndex() + 1);
        request.setData(mMessageListReqeust);
        RXUtils.request(getActivity(), request, "getLive", new RecyclerViewSubscriber<Response<DataList<Live>>>(adapter, dataList){


            @Override
            public void onSuccess(Response<DataList<Live>> dataListResponse) {
                adapter.addDataListNotifyDataSetChanged(dataListResponse.getData());
            }
        });
    }
}
