package com.axp.axpseller.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.Apply;
import com.axp.axpseller.models.bean.FansBean;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.views.adapters.MyApplyAdapter;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/12/13.
 */
public class ApplyListFragment extends BaseFragment {
    @BindView(R.id.list)
    SuperRecyclerView list;
    private String isChecked;
    View mView;
    MyApplyAdapter adapter;
    DataList<Apply> apply = new DataList<>();
    FansBean mApplyListReqeust = new FansBean();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_apply, container, false);

        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init(){
        adapter = new MyApplyAdapter(getActivity(),apply);
        getApply();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        list.setLayoutManager(manager);

        list.setAdapter(adapter);

        list.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> getApply(), 2);

        list.setRefreshListener(() -> reLoadData());

        list.setDifferentSituationOptionListener(v -> reLoadData());
    }

    @Override
    public void onResume() {
        super.onResume();
        reLoadData();
    }

    private void reLoadData() {

        mApplyListReqeust.setPageIndex(0);
        getApply();
    }

    private void getApply() {
        Request<FansBean> request = new Request<>();
        mApplyListReqeust.setPageIndex(mApplyListReqeust.getPageIndex() + 1);
        request.setData(mApplyListReqeust);
        RXUtils.request(getActivity(), request, "getwithdrawalApply", new SupportSubscriber<Response<DataList<Apply>>>() {

            @Override
            public void onNext(Response<DataList<Apply>> dataListResponse) {
                isChecked = dataListResponse.getData().getIscheck();
                adapter.addDataListNotifyDataSetChanged(dataListResponse.getData());
            }
        });
    }
    public String isChecked(){
        return isChecked;

    }
}
