package com.axp.axpseller.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.axp.axpseller.R;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.fragments.LazyFragment;
import com.axp.axpseller.models.bean.GoodManageResModel;
import com.axp.axpseller.models.bean.GoodsManageModel;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.view.IBaseListenner;
import com.axp.axpseller.views.adapters.WaitHandleLvAdapter;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YY on 2017/12/4.
 * 待处理fg
 */
public class WaitHandleFragment extends LazyFragment implements IBaseListenner {
    @BindView(R.id.wait_handle_lv)
    PullToRefreshListView waitHandleLv;
    @BindView(R.id.empty_layout)
    RelativeLayout emptyLayout;
    private View view;
    private List<GoodsManageModel> mLvList = new ArrayList<>();
    private LoadingDialog loadingDialog;
    private int page = 1;
    private int askType = 0;
    private WaitHandleLvAdapter mAdapter;
    private int pageSize = 1;
    private GoodManageResModel data;
    private boolean isPrepared = false;
    private int loadCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wait_handle, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        askNetData();
    }

    @Override
    public void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        askNetData();
    }

    private void askNetData() {
        if (loadCount == 1) {
            loadingDialog.show();
        }
        Request<GoodManageResModel> request = new Request<>();
        GoodManageResModel model = new GoodManageResModel();
        //待处理
        model.setStatusId(1003);
        model.setPageIndex(page);
        request.setData(model);
        RXUtils.request(getActivity(),request,"getGodosListByStatus", new SupportSubscriber<Response<GoodManageResModel>>() {

            @Override
            public void onNext(Response<GoodManageResModel> goodsManageModelResponse) {
                pageSize = goodsManageModelResponse.getData().getPageSize();
                data = goodsManageModelResponse.getData();
                getListSize();
                if (askType == 0) {
                    mLvList.clear();
                }
                mLvList.addAll(goodsManageModelResponse.getData().getDetailList());
                if (mLvList.size() == 0) {
                    waitHandleLv.setVisibility(View.GONE);
                    emptyLayout.setVisibility(View.VISIBLE);
                }else {
                    waitHandleLv.setVisibility(View.VISIBLE);
                    emptyLayout.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();
                }
                waitHandleLv.onRefreshComplete();
                loadCount++;
            }

            @Override
            public void onCompleted() {
                if (loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
            }
        });
    }

    private void initData() {
        loadingDialog = new LoadingDialog(getActivity());
        loadCount = 1;
        mAdapter = new WaitHandleLvAdapter(getActivity(),mLvList);
        waitHandleLv.setAdapter(mAdapter);
        waitHandleLv.setMode(PullToRefreshBase.Mode.BOTH);
        waitHandleLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                askType = 0;
                page = 1;
                askNetData();
            }

            //上拉加载
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                askType = 1;
                page++;
                if (page <= pageSize) {
                    askNetData();
                }else {
                    waitHandleLv.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            waitHandleLv.onRefreshComplete();
                            Toast.makeText(getActivity(), "没有更多数据了哦!", Toast.LENGTH_SHORT).show();
                        }
                    }, 1000);
                }
            }
        });
        mAdapter.setUpdateListenner(this);
        isPrepared = true;
        lazyLoad();
    }

    public void getListSize(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(data.getSellingCount());
        list.add(data.getCheckingCount());
        list.add(data.getWaitingCount());
        EventBus.getDefault().post(list);
    }

    //商品变动后数据更新
    @Override
    public void updateData() {
        askType = 0;
        page = 1;
        askNetData();
    }
}
