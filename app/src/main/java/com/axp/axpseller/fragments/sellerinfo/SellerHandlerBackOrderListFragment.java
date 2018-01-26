package com.axp.axpseller.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.models.BackOrderList;
import com.axp.axpseller.models.bean.GetBackOrderListBean;
import com.axp.axpseller.models.eventbus_message.UpdateSellerBackOrderMessage;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.DensityUtils;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.views.adapters.SellerHandlerBackOrderListAdapter;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.malinskiy.superrecyclerview.decoration.SpaceItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/25.
 * 商家处理用户退单列表
 */
public class SellerHandlerBackOrderListFragment extends BaseFragment {

    private String type = "10";
    View mView;
//    @BindView(R.id.tool_bar)
//    Toolbar toolBar;
    @BindView(R.id.list)
    SuperRecyclerView list;
    SellerHandlerBackOrderListAdapter mAdapter;
    BackOrderList mBackOrderList = new BackOrderList();
    GetBackOrderListBean getBackOrderListBean = new GetBackOrderListBean();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        EventBus.getDefault().register(this);
        mView = inflater.inflate(R.layout.fragment_seller_handler_back_order_list, container, false);

        ButterKnife.bind(this, mView);

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        type = getArguments().getString("type");
//        String sellerId = ContextParameter.getSellersInfo().getSellerId();
        String sellerId = ContextParameter.getUserInfo().getSellerId();
//        if (HomeActivity.SELLER_ID != null) {
//            getBackOrderListBean.setSellerId(HomeActivity.SELLER_ID);
//        }
        getBackOrderListBean.setSellerId(sellerId);
//        Log.d("雨落无痕丶", "onCreateView:yyyyyyyy " + sellerId);

        /*toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });*/

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(layoutManager);
        //添加项的间隔
        mAdapter = new SellerHandlerBackOrderListAdapter(getActivity(), mBackOrderList,type);
        list.setAdapter(mAdapter);
        list.addItemDecoration(new SpaceItemDecoration(DensityUtils.dp2px(getActivity(), 8)));
//        Log.d("雨落无痕丶", "onCreateView: yyyyyyyyyyyyy");

        //加载更多
        list.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                loadData();
            }
        });

        //下拉刷新
        list.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBackOrderListBean.setPageIndex(0);
                loadData();
            }
        });

        //重新加载
        list.setDifferentSituationOptionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBackOrderListBean.setPageIndex(0);
                loadData();
            }
        });

        loadData();
    }


    @Subscribe
    public void onEvent(UpdateSellerBackOrderMessage message) {
        getBackOrderListBean.setPageIndex(0);
        loadData();
    }


    private void loadData() {
        getBackOrderListBean.setPageIndex(getBackOrderListBean.getPageIndex() + 1);
        getBackOrderListBean.setBackType(type);
        RXUtils.request(getActivity(), new Request().setData(getBackOrderListBean), "getSellerBackOrderList", new RecyclerViewSubscriber<Response<BackOrderList>>(mAdapter, mBackOrderList) {

            @Override
            public void onSuccess(Response<BackOrderList> backOrderListResponse) {
                mAdapter.addDataListNotifyDataSetChanged(backOrderListResponse.getData());
            }
        });
    }

    public void reloadDtaa() {
        getBackOrderListBean.setPageIndex(0);
        loadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        EventBus.getDefault().unregister(this);
    }
}
