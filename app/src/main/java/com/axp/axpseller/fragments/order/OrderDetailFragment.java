package com.axp.axpseller.fragments.order;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.axp.axpseller.Constants;
import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.fragments.sellerinfo.SellerOrderListFragment;
import com.axp.axpseller.models.Order;
import com.axp.axpseller.models.eventbus_message.UpdateOrderListMessage;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.views.order.OrderAddress;
import com.axp.axpseller.views.order.OrderView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/7/18.
 * 订单详情界面
 */
public class OrderDetailFragment extends BaseFragment {

    /**
     * 状态
     */
    public static final String KEY_STATUS = "KEY_STATUS";
    /**
     * 订单id
     */
    public static final String KEY_ORDER_ID = "KEY_ORDER_ID";

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.oa_address)
    OrderAddress oaAddress;
    @BindView(R.id.layout_orders)
    LinearLayout layoutOrders;
    //待确认
    @BindView(R.id.layout_wait_confirm)
    LinearLayout layout_wait_confirm;
    //待发货
    @BindView(R.id.layout_receipt_of_send_out_goods)
    LinearLayout layout_receipt_of_send_out_goods;
    //待兑换
    @BindView(R.id.layout_wait_exchange)
    LinearLayout layout_wait_exchange;
    //待收货
    @BindView(R.id.layout_wait_of_goods)
    LinearLayout layout_wait_of_goods;
    //已评价
    @BindView(R.id.layout_comment)
    LinearLayout layout_comment;

    @BindView(R.id.layout_order_option)
    LinearLayout layoutOrderOption;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.progress)
    FrameLayout progress;

    Context mContext;
    Order mOrder;
    String mStatus;
    String mOrderId;
    @BindView(R.id.tv_express_tactics)
    TextView tvExpressTactics;
    @BindView(R.id.tv_order_code)
    TextView tvOrderCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        EventBus.getDefault().register(this);

        mContext = getActivity();
        mView = inflater.inflate(R.layout.fragment_order_detail, container, false);
        ButterKnife.bind(this, mView);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        loadBundle();
        loadData();

        return mView;
    }

    private void loadBundle() {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            mStatus = bundle.getString(KEY_STATUS);
            mOrderId = bundle.getString(KEY_ORDER_ID);
        }
    }

    private void loadData() {

        Request request = new Request();
        Map<String, String> value = new HashMap<>();
        value.put("orderId", mOrderId);
        request.setData(value);
        RXUtils.request(getActivity(), request, "getOrder", new SupportSubscriber<Response<Order>>() {

            @Override
            public void onStart() {
                progress.setVisibility(View.VISIBLE);
                container.setVisibility(View.GONE);
            }

            @Override
            public void onCompleted() {
                progress.setVisibility(View.GONE);
                container.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onCompleted();
            }

            @Override
            public void onNext(Response<Order> orderResponse) {
                bindView(orderResponse.getData());
            }
        });

    }

    public void bindView(Order order) {

        mOrder = order;


        layoutOrders.removeAllViews();
        OrderView orderView = new OrderView(getActivity());
        boolean canSelect = false;
        if (mStatus.equals(Constants.ORDER_STATUS_WAIT_OF_GOODS) || mStatus.equals(Constants.ORDER_STATUS_WAIT_SEND_OUT_GOODS) || mStatus.equals(Constants.ORDER_STATUS_WAIT_COMMENT) || mStatus.equals(Constants.ORDER_STATUS_WAIT_EXCHANGE)) {
            //显示多选框
            canSelect = true;
        }
        orderView.bindView(mOrder, canSelect);
        layoutOrders.addView(orderView);
        tvExpressTactics.setText(mOrder.getExpressTactics());
        tvOrderCode.setText("订单号："+mOrder.getOrderNumber());
        if (StringUtils.isEmpty(mOrder.getUsername())) {
            oaAddress.setVisibility(View.GONE);
        } else {
            oaAddress.bindView(mOrder, false);
        }


        switch (mStatus) {
            case Constants.ORDER_STATUS_WAIT_CONFIRM: //待确认
                layoutOrderOption.setVisibility(View.VISIBLE);
                layout_wait_confirm.setVisibility(View.VISIBLE);
                break;
            case Constants.ORDER_STATUS_WAIT_SEND_OUT_GOODS: //待发货
                layoutOrderOption.setVisibility(View.VISIBLE);
                layout_receipt_of_send_out_goods.setVisibility(View.VISIBLE);
                break;
            case Constants.ORDER_STATUS_WAIT_EXCHANGE: //待兑换
                layoutOrderOption.setVisibility(View.VISIBLE);
                layout_wait_exchange.setVisibility(View.VISIBLE);
                break;
            case Constants.ORDER_STATUS_WAIT_OF_GOODS: //待收货
                layoutOrderOption.setVisibility(View.VISIBLE);
                layout_wait_of_goods.setVisibility(View.VISIBLE);
                break;
            case Constants.ORDER_STATUS_COMMENT: //已评价
                layoutOrderOption.setVisibility(View.VISIBLE);
                layout_comment.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Subscribe
    public void onEvent(UpdateOrderListMessage message) {

        if (mStatus.equals(message.getStatus())) {
            getActivity().finish();
        }
    }

    @OnClick({R.id.btn_confirm, R.id.btn_contact_user_wait_confirm, R.id.btn_send_out_goods, R.id.btn_contact_user_send_out_goods,
            R.id.btn_exchange, R.id.btn_contact_user_wait_exchange, R.id.btn_logistics, R.id.btn_contact_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:

                SellerOrderListFragment.sellerConfirmOrder(mContext, mOrder, mStatus);

                break;
            case R.id.btn_contact_user_wait_confirm:
            case R.id.btn_contact_user:
            case R.id.btn_contact_user_send_out_goods:
            case R.id.btn_contact_user_wait_exchange:
                SellerOrderListFragment.contactUser(mContext, mOrder);
                break;
            case R.id.btn_send_out_goods:
                SellerOrderListFragment.sendOutGoods(mContext, mOrder);
                break;

            case R.id.btn_exchange:
                SellerOrderListFragment.exchange(mContext, mOrder);
                break;

            case R.id.btn_logistics:
                SellerOrderListFragment.toLogistics(mContext, mOrder);

                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        EventBus.getDefault().unregister(this);
    }
}
