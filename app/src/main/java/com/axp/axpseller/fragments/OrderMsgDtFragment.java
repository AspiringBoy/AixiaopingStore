package com.axp.axpseller.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.activitys.TaoBaoActivity;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.OrderMsgDtGoodsModel;
import com.axp.axpseller.models.OrderMsgDtModel;
import com.axp.axpseller.models.OrderMsgDtObModel;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by YY on 2017/5/9.
 */
public class OrderMsgDtFragment extends BaseFragment {
    private View view;
    private int msgId;
    private Toolbar toolbar;
    private int typeId;
    private LinearLayout container;
    private TextView orderNum;
    private TextView orderState;
    private TextView order_action;
    private TextView yundan_num;
    private TextView order_date;
    private LoadingDialog loadingDialog;
    private String goodsDtUrl = "http://seller.aixiaoping.com/Home/Index/detail";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_msg_dt, container, false);
        initView();
        initData();
        return view;
    }

    private void initView() {
        toolbar = ((Toolbar) view.findViewById(R.id.order_msg_dt_toolbar));
        container = ((LinearLayout) view.findViewById(R.id.order_msg_dt_containner));
        orderNum = ((TextView) view.findViewById(R.id.order_msg_dt_orderNumber));
        orderState = ((TextView) view.findViewById(R.id.order_msg_dt_orderState));
        order_action = ((TextView) view.findViewById(R.id.order_msg_dt_action));
        yundan_num = ((TextView) view.findViewById(R.id.order_msg_dt_yundanNum));
        order_date = ((TextView) view.findViewById(R.id.order_msg_dt_order_date));
    }

    private void initData() {
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.show();
        getBundleData();
        getOrderMsgDtData(msgId + "", typeId + "");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    private void getOrderMsgDtData(String msgId, String typeId) {
        Request<OrderMsgDtModel> request = new Request<>();
        OrderMsgDtModel orderMsgDtModel = new OrderMsgDtModel();
        orderMsgDtModel.setUserId(ContextParameter.getUserInfo().getUserId());
        orderMsgDtModel.setTypeId(typeId);
        orderMsgDtModel.setMsgId(msgId);
        request.setData(orderMsgDtModel);
        RXUtils.request(getActivity(), request, "getMsgDetailOrder", new SupportSubscriber<Response<OrderMsgDtModel>>() {
            @Override
            public void onNext(Response<OrderMsgDtModel> orderMsgDtModelResponse) {
                OrderMsgDtObModel msgDetail = orderMsgDtModelResponse.getData().getMsgDetail();
                List<OrderMsgDtGoodsModel> goods = msgDetail.getGoods();
                for (OrderMsgDtGoodsModel good : goods) {
                    View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.order_msg_dt_order_item, null);
                    ImageView order_icon = (ImageView) inflate.findViewById(R.id.order_msg_dt_order_icon);
                    TextView order_name = (TextView) inflate.findViewById(R.id.order_msg_dt_order_name);
                    TextView order_price = (TextView) inflate.findViewById(R.id.order_msg_dt_order_price);
                    TextView order_extraDesc = (TextView) inflate.findViewById(R.id.order_msg_dt_order_extraDesc);
                    TextView order_freighPrice = (TextView) inflate.findViewById(R.id.order_msg_dt_order_freightPrice);
                    TextView order_count = (TextView) inflate.findViewById(R.id.order_msg_dt_order_count);
                    LinearLayout order_item_ll = (LinearLayout) inflate.findViewById(R.id.order_item_ll);
                    Glide.with(getActivity()).load(good.getIcon()).into(order_icon);
                    order_name.setText(good.getName());
                    order_price.setText(good.getPrice());
                    order_extraDesc.setText(good.getSid());
                    order_freighPrice.setText(good.getFreightPrice());
                    order_count.setText(good.getCount());
                    inflate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), TaoBaoActivity.class);
                            intent.putExtra("URL", goodsDtUrl + "?id=" + good.getGoodsId());
                            getActivity().startActivity(intent);
                        }
                    });
                    container.addView(inflate);
                }
                orderNum.setText("订单编号:" + msgDetail.getOrderNumber());
                String orderStatusId = msgDetail.getOrderStatusId();
                if (orderStatusId.equals("60")) {
                    yundan_num.setVisibility(View.GONE);
                    orderState.setTextColor(Color.parseColor("#ff0000"));
                } else if (orderStatusId.equals("30")) {
                    yundan_num.setVisibility(View.GONE);
                    orderState.setTextColor(Color.parseColor("#398fe2"));
                } else if (orderStatusId.equals("20")) {
                    yundan_num.setVisibility(View.GONE);
                    orderState.setTextColor(Color.parseColor("#ff6600"));
                } else if (orderStatusId.equals("50")) {
                    orderState.setTextColor(Color.parseColor("#45d95f"));
                    yundan_num.setVisibility(View.VISIBLE);
                    yundan_num.setText(msgDetail.getOrderFreightNumber());
                }
                List<String> list = msgDetail.getContent();
                StringBuilder stringBuilder = new StringBuilder();
                for (String s : list) {
                    stringBuilder.append(s);
                }
                order_action.setText(stringBuilder.toString());
                orderState.setText(msgDetail.getOrderStatus());
                order_date.setText(msgDetail.getTime());
                loadingDialog.dismiss();
            }
        });
    }

    private void getBundleData() {
        msgId = getActivity().getIntent().getIntExtra("msgId", 1);
        typeId = getActivity().getIntent().getIntExtra("typeId", 1);
    }
}
