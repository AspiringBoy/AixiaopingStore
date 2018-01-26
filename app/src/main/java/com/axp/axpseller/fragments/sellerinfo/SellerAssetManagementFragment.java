package com.axp.axpseller.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axp.axpseller.R;
import com.axp.axpseller.activitys.sellerinfo.SellerAssetManagementActivity;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.RecyclerViewModel;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.Assets;
import com.axp.axpseller.models.bean.AssetBean;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.L;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.views.adapters.AssetManagementAdapter;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/12/8.
 */
public class SellerAssetManagementFragment extends BaseFragment {
    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_asset_management)
    SuperRecyclerView lvAssetManagement;
    AssetManagementAdapter adapter;
    DataList<RecyclerViewModel> dataList;
    AssetBean assetBean = new AssetBean();
    @BindView(R.id.nobar)
    Toolbar nobar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_seller_asset_management, container, false);

        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        reLoad();
    }

    private void init() {

        if (getActivity() instanceof SellerAssetManagementActivity){
            toolBar.setVisibility(View.VISIBLE);
            nobar.setVisibility(View.GONE);
        }else{
            toolBar.setVisibility(View.GONE);
            nobar.setVisibility(View.VISIBLE);
        }
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        dataList = new DataList<>();
        dataList.setDataList(new ArrayList<>());
        adapter = new AssetManagementAdapter(getActivity(), dataList);
        lvAssetManagement.setLayoutManager(new LinearLayoutManager(getActivity()));
        lvAssetManagement.setAdapter(adapter);
        lvAssetManagement.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> getMoreAssetList(), 3);
        lvAssetManagement.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reLoad();
            }
        });
    }

    private void reLoad() {
        dataList.getDataList().clear();
        assetBean.setPageIndex(0);
        getAssetList();
    }

    private void getAssetList() {
        Request<AssetBean> beanRequest = new Request<>();
        assetBean.setPageIndex(assetBean.getPageIndex() + 1);
        assetBean.setType("6");
        beanRequest.setData(assetBean);
        RXUtils.request(getActivity(), beanRequest, "getM", new SupportSubscriber<Response<DataList<Assets>>>() {

            @Override
            public void onNext(Response<DataList<Assets>> dataListResponse) {
                L.e("资产数据" + dataListResponse.getData());
                // dataList =  handlerData(assetMResponse);
                handlerData(dataListResponse);
                adapter.notifyDataSetChanged();
            }

        });
    }

    private void handlerData(Response<DataList<Assets>> response) {
        DataList<RecyclerViewModel> recyclerViewModel = new DataList<>();
        List<RecyclerViewModel> list = new ArrayList<>();
        RecyclerViewModel money = new RecyclerViewModel();
        List<String> data = new ArrayList<>();
        data.add(response.getData().getTotalMoneys());
        data.add(response.getData().getUnConfirmedMoney());
        money.setData(data);
        money.setItemType(AssetManagementAdapter.TYPE_HEAD);
        dataList.getDataList().add(money);
        //  list.add(money);
        if (response.getData().getDataList() != null && response.getData().getDataList().size() > 0) {
            for (Assets asset : response.getData().getDataList()) {
                RecyclerViewModel assetModle = new RecyclerViewModel();
                assetModle.setData(asset);
                assetModle.setItemType(AssetManagementAdapter.TYPE_ASSETS_CLASSIFYS);
                list.add(assetModle);
                dataList.getDataList().add(assetModle);
            }
        }
    }

    private void handlerMoreData(Response<DataList<Assets>> response) {
        if (response.getData().getDataList() != null && response.getData().getDataList().size() > 0) {
            for (Assets asset : response.getData().getDataList()) {
                RecyclerViewModel assetModle = new RecyclerViewModel();
                assetModle.setData(asset);
                assetModle.setItemType(AssetManagementAdapter.TYPE_ASSETS_CLASSIFYS);
                dataList.getDataList().add(assetModle);
            }
        }
    }

    private void getMoreAssetList() {
        Request<AssetBean> beanRequest = new Request<>();
        assetBean.setPageIndex(assetBean.getPageIndex() + 1);
        assetBean.setType("6");
        beanRequest.setData(assetBean);
        RXUtils.request(getActivity(), beanRequest, "getM", new SupportSubscriber<Response<DataList<Assets>>>() {

            @Override
            public void onNext(Response<DataList<Assets>> dataListResponse) {
                L.e("资产数据" + dataListResponse.getData());
                // dataList =  handlerData(assetMResponse);
                handlerMoreData(dataListResponse);
                adapter.notifyDataSetChanged();
            }

        });
    }


}
