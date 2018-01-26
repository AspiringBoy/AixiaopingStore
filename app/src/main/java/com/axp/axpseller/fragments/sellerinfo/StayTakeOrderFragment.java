package com.axp.axpseller.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.axp.axpseller.activitys.order.OrderDetailActivity;
import com.gc.materialdesign.views.ButtonRectangle;
import com.axp.axpseller.Constants;
import com.axp.axpseller.R;
import com.axp.axpseller.activitys.sellerinfo.StayTakeOrderActivity;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.LoadingSubscriber;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.Order;
import com.axp.axpseller.models.OrderItem;
import com.axp.axpseller.models.bean.SellerConfirmReceiptBean;
import com.axp.axpseller.models.eventbus_message.UpdateSellerOrderListMessage;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.T;
import com.axp.axpseller.views.order.GoodsItemView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/7/14.
 * 待发货
 */
public class StayTakeOrderFragment extends BaseFragment {

    View mView;
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
    @BindView(R.id.tv_take_order)
    EditText edtTakeOrder;
    @BindView(R.id.tv_return_order)
    EditText edtReturnOrder;
    @BindView(R.id.edt_logistics_company)
    EditText edtLogisticsCompany;
    @BindView(R.id.edt_waybill_number)
    EditText edtWaybillNumber;
    @BindView(R.id.ll_order_item)
    LinearLayout llOrderItem;
    @BindView(R.id.btn_sure)
    ButtonRectangle btnSure;
    private String expressName, expressNumber, orderId, sendGoodsAddress, backGoodsAddress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_stay_take_order, container, false);

        ButterKnife.bind(this, mView);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            orderId = bundle.getString(StayTakeOrderActivity.KEY_ORDER_ID);
        }
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
        stayTakeOrder();
    }

    private void stayTakeOrder() {
        expressName = edtLogisticsCompany.getText().toString();
        expressNumber = edtWaybillNumber.getText().toString();
        sendGoodsAddress = edtTakeOrder.getText().toString();
        backGoodsAddress = edtReturnOrder.getText().toString();
        if (expressName != null && expressName.length()>0 && expressNumber != null && expressNumber.length()>0/* && sendGoodsAddress != null && sendGoodsAddress.length()>0*/ ) {
            Request<SellerConfirmReceiptBean> request = new Request<>();
            SellerConfirmReceiptBean bean = new SellerConfirmReceiptBean();
            bean.setOrderId(orderId);
            bean.setSendGoodsAddress(sendGoodsAddress);
            bean.setBackGoodsAddress(backGoodsAddress);
            bean.setExpressName(expressName);
            bean.setExpressNumber(expressNumber);
            request.setData(bean);
            RXUtils.request(getActivity(), request, "sellerConfirmReceipt", new LoadingSubscriber<Response>(getActivity()) {

                @Override
                public void onNext(Response response) {
                    T.showShort(getActivity(), response.getMessage());

                    //发送列表数据更新消息
                    UpdateSellerOrderListMessage message = new UpdateSellerOrderListMessage();
                    message.setStatus(Constants.ORDER_STATUS_WAIT_SEND_OUT_GOODS);
                    EventBus.getDefault().post(message);

                    if (OrderDetailActivity.orderDetailActivity != null) {
                        OrderDetailActivity.orderDetailActivity.finish();
                    }
                    getActivity().finish();
                }
            });
        }else {
            T.showShort(getActivity(),"请完善信息!");
        }
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
                tvOrderCode.setText(orderResponse.getData().getOrderNumber());
                tvOrderDate.setText(orderResponse.getData().getOrderDate());
                tvName.setText(orderResponse.getData().getUsername());
                tvPhone.setText(orderResponse.getData().getPhone());
                tvAddress.setText(orderResponse.getData().getAddress());
                List<OrderItem> orderData = orderResponse.getData().getOrderItems();
                for (int i = 0; i < orderData.size(); i++) {
                    OrderItem orderItem = orderData.get(i);
                    GoodsItemView itemView = new GoodsItemView(getActivity());
                    itemView.bindView(orderItem);
                    llOrderItem.addView(itemView);
                }
            }
        });
    }
}
