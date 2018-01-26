package com.axp.axpseller.views.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.activitys.sellerinfo.ChargebacKauditActivity;
import com.axp.axpseller.fragments.sellerinfo.ChargebacKauditFragment;
import com.axp.axpseller.models.BackOrder;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.utils.T;
import com.axp.axpseller.views.adapters.viewholder.BaseViewHolder;
import com.axp.axpseller.views.order.GoodsItemView;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/7/25.
 * 商家处理用户退单信息列表
 */
public class SellerHandlerBackOrderListAdapter extends SuperRecyclerViewAdapter<BackOrder, SellerHandlerBackOrderListViewHolder> {
    private String type = "10";

    public SellerHandlerBackOrderListAdapter(Context context, DataList<BackOrder> dataList, String type) {
        super(context, dataList);
        this.dataList = dataList;
        this.type = type;
    }

    private DataList<BackOrder> dataList;

    @Override
    public SellerHandlerBackOrderListViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        return new SellerHandlerBackOrderListViewHolder(mContext, parent,type);
    }

    @Override
    public void onSuperBindViewHolder(SellerHandlerBackOrderListViewHolder holder, int position) {
        if (mList != null) {
            holder.bindView(mList.get(position));
//            Log.d("雨落无痕丶", "onSuperBindViewHolder: ttttttttttttt");
        }
    }
}

class SellerHandlerBackOrderListViewHolder extends BaseViewHolder<BackOrder> {

    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.tv_line)
    View tvLine;
    @BindView(R.id.tv_order_order_number)
    TextView tvOrderOrderNumber;
    @BindView(R.id.layout_order_goods)
    LinearLayout layoutOrderGoods;
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_reality_money)
    TextView tvRealityMoney;
    @BindView(R.id.btn_verify)
    Button btn_verify;
    @BindView(R.id.btn_contact_user)
    Button btn_contact_user;

    private String mType;
    BackOrder mOrder;

    public SellerHandlerBackOrderListViewHolder(Context context, ViewGroup parent,String type) {
        super(context, parent, R.layout.seller_order_view_handler_back_order_list_item);
        ButterKnife.bind(this, itemView);
        mType = type;
    }

    @Override
    public void bindView(BackOrder order) {
        mOrder = order;

        String orderDate = order.getOrderDate();
        if (mType.equals("10")) {
            btn_verify.setVisibility(View.VISIBLE);
            btn_contact_user.setVisibility(View.VISIBLE);
        }else {
            btn_verify.setVisibility(View.GONE);
            btn_contact_user.setVisibility(View.GONE);
        }
        tvOrderDate.setText(orderDate);
        tvOrderOrderNumber.setText(order.getOrderNumber());
        tvOrderStatus.setText(order.getBackOrderStatus().getName());
        if (!order.getRealityMoney().equals("0.0")) {
            tvRealityMoney.setText(order.getRealityMoney());
        } else {
            tvRealityMoney.setText(order.getScore() + "积分");
        }
        tvUserName.setText(order.getUser().getName());

        GoodsItemView itemView = new GoodsItemView(getContext());
        itemView.bindView(mOrder.getBackOrderItem());
        layoutOrderGoods.removeAllViews();
        layoutOrderGoods.addView(itemView);
    }

    @OnClick({R.id.btn_verify, R.id.btn_contact_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_verify:
                Bundle bundle = new Bundle();
                bundle.putString(ChargebacKauditFragment.KEY_BACK_ORDER_ITEM_ID, mOrder.getBackOrderVerify().getBackOrderItemId());
                AppUtils.toActivity(getContext(), ChargebacKauditActivity.class, bundle);

                break;
            case R.id.btn_contact_user:
                if (StringUtils.isEmpty(mOrder.getUser().getPhone())) {
                    T.showShort(getContext(), "该用户还没有填写手机号码");
                    return;
                }
                AppUtils.toCallPhone(getContext(), mOrder.getSeller().getSellerPhone());
                break;
        }
    }
}
