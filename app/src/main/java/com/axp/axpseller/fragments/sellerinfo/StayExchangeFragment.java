package com.axp.axpseller.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.axp.axpseller.activitys.order.OrderDetailActivity;
import com.gc.materialdesign.views.ButtonRectangle;
import com.axp.axpseller.R;
import com.axp.axpseller.activitys.sellerinfo.StayExchangeActivity;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.LoadingSubscriber;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.Order;
import com.axp.axpseller.models.OrderItem;
import com.axp.axpseller.models.bean.SellerConfirmExchangeBean;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.T;
import com.axp.axpseller.views.order.GoodsItemView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/7/14.
 * 待兑换，积分兑换
 */
public class StayExchangeFragment extends BaseFragment {

    View mView;

    GoodsItemView itemView;
    @BindView(R.id.iv_two_dimension_code)
    ImageView ivTwoDimensionCode;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_order_code)
    TextView tvOrderCode;
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_order_item)
    LinearLayout llOrderItem;
    @BindView(R.id.edt_exchange_code)
    EditText edtExchangeCode;
    @BindView(R.id.btn_sure)
    ButtonRectangle btnSure;
    private String exchangeCode, orderId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_stay_exchange, container, false);

        ButterKnife.bind(this, mView);
        itemView = new GoodsItemView(getActivity());
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            orderId = bundle.getString(StayExchangeActivity.KEY_ORDER_ID);
            exchangeCode = bundle.getString(StayExchangeActivity.KEY_EXCHANGE_CODE, "");
        }

        edtExchangeCode.setText(exchangeCode);

        getOrder();
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return mView;
    }

    @OnClick(R.id.btn_sure)
    public void onClick() {
        stayExchange();
    }

    private void stayExchange() {

        Request<SellerConfirmExchangeBean> request = new Request<>();
        SellerConfirmExchangeBean bean = new SellerConfirmExchangeBean();
        exchangeCode = edtExchangeCode.getText().toString();
        bean.setOrderId(orderId);
        bean.setExchangeCode(exchangeCode);
        request.setData(bean);
        RXUtils.request(getActivity(), request, "sellerConfirmExchange", new LoadingSubscriber<Response>(getActivity()) {

            @Override
            public void onNext(Response response) {
                T.showShort(getActivity(), response.getMessage());
                getActivity().finish();
                if (OrderDetailActivity.orderDetailActivity != null)
                    OrderDetailActivity.orderDetailActivity.finish();
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                T.showShort(getActivity(), response.getMessage());
            }
        });
    }

    /**
     * 获得商品
     */
    private void getOrder() {
        Request<Order> request = new Request<>();
        Order mOrder = new Order();
        mOrder.setOrderId(orderId);
        request.setData(mOrder);
        RXUtils.request(getActivity(), request, "getOrder", new SupportSubscriber<Response<Order>>() {

            @Override
            public void onNext(Response<Order> orderResponse) {
                tvName.setText(orderResponse.getData().getUsername());
                tvPhone.setText(orderResponse.getData().getPhone());
                tvOrderDate.setText(orderResponse.getData().getOrderDate());
                tvOrderCode.setText(orderResponse.getData().getOrderNumber());
                tvAddress.setText(orderResponse.getData().getAddress());
                List<OrderItem> orderData = orderResponse.getData().getOrderItems();
                for (int i = 0; i < orderData.size(); i++) {
                    OrderItem orderItem = orderData.get(i);
                    itemView.bindView(orderItem);
                    llOrderItem.addView(itemView);
                }
            }
        });
    }

}
