package com.axp.axpseller.fragments.mall;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.axp.axpseller.Constants;
import com.axp.axpseller.R;
import com.axp.axpseller.activitys.mall.GoodsSearchActivity;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.RecyclerViewModel;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.Goods;
import com.axp.axpseller.models.GoodsList;
import com.axp.axpseller.models.GoodsType;
import com.axp.axpseller.models.SpecialLocalProduct;
import com.axp.axpseller.models.bean.GetGoodsListBean;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.DensityUtils;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.views.adapters.Preferential99Adapter;
import com.axp.axpseller.views.adapters.SpecialLocalProductAdapter;
import com.axp.axpseller.views.dialogs.SecondaryClassificationDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/7/19.
 * 特产速递
 */
public class SpecialLocalProductFragment extends BaseFragment {

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.list)
    SuperRecyclerView list;
    SpecialLocalProductAdapter mAdapter;
    DataList<RecyclerViewModel> mDataList = new DataList<>();
    List<GoodsType> mDataType = new ArrayList<>();
    GetGoodsListBean mGoodsListReqeust = new GetGoodsListBean();
    GetGoodsListBean mGoodsTypeReqeust = new GetGoodsListBean();
    @BindView(R.id.layout_classification)
    LinearLayout layoutClassification;
    @BindView(R.id.btn_search)
    ImageView btnSearch;
    @BindView(R.id.tv_all_classifi)
    TextView tvAllClassifi;
    @BindView(R.id.tv_sales_volume)
    TextView tvSalesVolume;
    @BindView(R.id.tv_value)
    TextView tvValue;
    String typeId;
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_special_local_product, container, false);
        ButterKnife.bind(this, mView);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //如果是商品占用一格，如果不是占用一行
                return mAdapter.isGoods(position) ? 1 : layoutManager.getSpanCount();
            }
        });
        list.setLayoutManager(layoutManager);

        mAdapter = new SpecialLocalProductAdapter(getActivity(), mDataList);

        list.setAdapter(mAdapter);

        //添加分割
        list.addItemDecoration(new RecyclerView.ItemDecoration() {

            int goodsIndex = 0;

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {


                int position = parent.getChildLayoutPosition(view);
                int itemType = mAdapter.getItemViewType(position);

                if (itemType == SpecialLocalProductAdapter.VIEW_TYPE_FILTER_TYPE) {
                    int space = DensityUtils.dp2px(getActivity(), 4);
                    outRect.top = space;
                    outRect.bottom = space;
                } else if (itemType == SpecialLocalProductAdapter.VIEW_TYPE_GOODS_ITEM) {
                    int space = DensityUtils.dp2px(getActivity(), 4);

                    outRect.top = space;
                    //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0
                    if (goodsIndex % 2 == 0) {
                        outRect.left = space;
                        outRect.right = space / 2;
                    } else {
                        outRect.right = space;
                        outRect.left = space / 2;
                    }

                    goodsIndex++;
                }


            }
        });

        list.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> loadGoodsList(), 2);
        //数据请求失败后的数据重新载入
        list.setDifferentSituationOptionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });

        load();
        loadType();
        return mView;
    }


    private void load() {
        mDataList.getDataList().clear();
        loadSpecialLocalProduct();
    }

    private void loadSpecialLocalProduct() {
        Request request = new Request();
        RXUtils.request(getActivity(), request, "specialLocalProduct", new RecyclerViewSubscriber<Response<SpecialLocalProduct>>(mAdapter, mDataList) {

            @Override
            public void onSuccess(Response<SpecialLocalProduct> preferential99Response) {
                handlerSpecialLocalProduct(preferential99Response);
                loadGoodsList();
            }
        });
    }

    private void loadGoodsList() {
        Request<GetGoodsListBean> request = new Request<>();
        mGoodsListReqeust.setPageIndex(mGoodsListReqeust.getPageIndex() + 1);
        mGoodsListReqeust.setMallTyle(Constants.MALL_SPECIAL_LOCAL_PRODUCT);
        request.setData(mGoodsListReqeust);

        RXUtils.request(getActivity(), request, "getGoodsList", new RecyclerViewSubscriber<Response<GoodsList>>(mAdapter, mDataList) {

            @Override
            public void onSuccess(Response<GoodsList> goodsListResponse) {
                handlerGoodsItem(goodsListResponse);
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    @OnClick({R.id.layout_all_classifi, R.id.layout_sales_volume, R.id.layout_value})
    public void onClick(View view) {

        if (mDataType == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.layout_all_classifi:

                if (mDataType == null || mDataType.size() == 0) {
                    return;
                }

                int top = layoutClassification.getMeasuredHeight() + toolBar.getMeasuredHeight();

                SecondaryClassificationDialog dialog = new SecondaryClassificationDialog(getActivity(), mDataType, top);
                dialog.setOnClassificationSelectListener(new ClassificationSelectListener());
                dialog.show();
                break;
            case R.id.layout_sales_volume:
                long currentTime = Calendar.getInstance().getTimeInMillis();
                if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                    lastClickTime = currentTime;

                    mGoodsListReqeust.setPageIndex(0);
                    mGoodsTypeReqeust.setTypeId(typeId);
                    mGoodsListReqeust.setValue("0");
                    tvValue.setText("价格");

                    if (mGoodsListReqeust.getSalesVolume().equals("0") || mGoodsListReqeust.getSalesVolume().equals("1")) {
                        mGoodsListReqeust.setSalesVolume("2");
                        tvSalesVolume.setText("从低到高");
                    } else if (mGoodsListReqeust.getSalesVolume().equals("2")) {
                        mGoodsListReqeust.setSalesVolume("1");
                        tvSalesVolume.setText("从高到低");
                    }
                    load();
                }
                break;
            case R.id.layout_value:
                long currentTime1 = Calendar.getInstance().getTimeInMillis();
                if (currentTime1 - lastClickTime > MIN_CLICK_DELAY_TIME) {
                    lastClickTime = currentTime1;
                    mGoodsListReqeust.setPageIndex(0);
                    mGoodsTypeReqeust.setTypeId(typeId);
                    mGoodsListReqeust.setSalesVolume("0");
                    tvSalesVolume.setText("销量");

                    if (mGoodsListReqeust.getValue().equals("1")) {
                        mGoodsListReqeust.setValue("2");
                        tvValue.setText("从低到高");
                    } else if (mGoodsListReqeust.getValue().equals("0") || mGoodsListReqeust.getValue().equals("2")) {
                        mGoodsListReqeust.setValue("1");
                        tvValue.setText("从高到低");
                    }
                    load();
                }
                break;
        }
    }

    /**
     * 处理商城数据
     *
     * @param response
     * @return
     */
    public void handlerSpecialLocalProduct(Response<SpecialLocalProduct> response) {


        RecyclerViewModel VIEW_TYPE_BANNER = new RecyclerViewModel();
        VIEW_TYPE_BANNER.setData(response.getData());
        VIEW_TYPE_BANNER.setItemType(Preferential99Adapter.VIEW_TYPE_BANNER);

//        RecyclerViewModel VIEW_TYPE_FILTER_TYPE = new RecyclerViewModel();
//        VIEW_TYPE_FILTER_TYPE.setData(response.getData());
//        VIEW_TYPE_FILTER_TYPE.setItemType(Preferential99Adapter.VIEW_TYPE_FILTER_TYPE);

        mDataList.getDataList().add(VIEW_TYPE_BANNER);
//        mDataList.getDataList().add(VIEW_TYPE_FILTER_TYPE);

    }

    /**
     * 处理商品列表
     *
     * @param response
     * @return
     */
    public void handlerGoodsItem(Response<GoodsList> response) {
        setResponseData(mDataList, response.getData());

        for (Goods goods : response.getData().getDataList()) {
            RecyclerViewModel recyclerViewModel = new RecyclerViewModel();
            recyclerViewModel.setData(goods);
            recyclerViewModel.setItemType(Preferential99Adapter.VIEW_TYPE_GOODS_ITEM);

            mDataList.getDataList().add(recyclerViewModel);
        }

    }

    /**
     * 设置通用的响应数据
     *
     * @param oldData
     * @param newData
     */
    private void setResponseData(DataList oldData, DataList newData) {
        oldData.setPageIndex(newData.getPageIndex());
        oldData.setPageItemCount(newData.getPageItemCount());
        oldData.setPageSize(newData.getPageSize());
    }

    @OnClick(R.id.btn_search)
    public void onClick() {
        Bundle bundle = new Bundle();
        bundle.putString(GoodsSearchActivity.KEY_MALL_TYPE, Constants.MALL_SPECIAL_LOCAL_PRODUCT);
        AppUtils.toActivity(getActivity(), GoodsSearchActivity.class, bundle);

    }

    private void loadType() {
        mGoodsTypeReqeust.setType("91");
        mGoodsTypeReqeust.setPageIndex(1);
        Request<GetGoodsListBean> request = new Request();
        request.setData(mGoodsTypeReqeust);
        RXUtils.request(getActivity(), request, "getGoodsType", new SupportSubscriber<Response<List<GoodsType>>>() {


            @Override
            public void onNext(Response<List<GoodsType>> listResponse) {
                mDataType = listResponse.getData();
            }
        });
    }

    class ClassificationSelectListener implements SecondaryClassificationDialog.OnClassificationSelectListener {
        @Override
        public void select(GoodsType type) {
            mGoodsListReqeust.setPageIndex(0);
            mGoodsListReqeust.setValue("0");
            tvValue.setText("价格");
            mGoodsListReqeust.setSalesVolume("0");
            tvSalesVolume.setText("销量");

            mGoodsListReqeust.setTypeId(type.getTypeId());
            typeId = type.getTypeId();
            //    toolBar.setTitle(type.getTypeName());
            load();
        }
    }
}
