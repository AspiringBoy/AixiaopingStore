package com.axp.axpseller.views.order;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.axp.axpseller.Constants;
import com.axp.axpseller.R;
import com.axp.axpseller.models.Order;
import com.axp.axpseller.models.OrderDetail;
import com.axp.axpseller.models.OrderItem;
import com.axp.axpseller.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/6/29.
 * 订单视图
 */
public class OrderView extends FrameLayout {

    @BindView(R.id.tv_seller_name)
    TextView tvSellerName;
    @BindView(R.id.tv_order_status)
    TextView tvExpressTactics;
    @BindView(R.id.layout_order_goods)
    LinearLayout layoutOrderGoods;
    @BindView(R.id.tv_order_total_money)
    TextView tvOrderTotalMoney;
    @BindView(R.id.layout_order_details)
    LinearLayout layoutOrderDetails;
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_reality_money)
    TextView tvRealityMoney;

    Order mOrder;

    public OrderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public OrderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public OrderView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.order_view_order, this, true);
        ButterKnife.bind(this);

    }

    /**
     * 绑定数据
     * @param order
     */
    public void bindView(Order order, boolean canSelect){
        mOrder = order;

        tvSellerName.setText(mOrder.getSeller().getSellerName());
        tvExpressTactics.setText(mOrder.getExpressTactics());
        L.e("======红包======"+mOrder.getExpressTactics());
        tvOrderTotalMoney.setText(mOrder.getMoney());
        tvOrderDate.setText(mOrder.getOrderDate());
        if (!mOrder.getRealityMoney().equals("0.0")) {
            tvRealityMoney.setText(mOrder.getRealityMoney());
        }else {
            tvRealityMoney.setText(mOrder.getScore()+"积分");
        }
        Log.d("雨落无痕丶", "bindView: "+mOrder.getRealityMoney());
        if(mOrder.getOrderItems() != null) {
            for (OrderItem orderItem : mOrder.getOrderItems()) {
                GoodsItemView itemView = new GoodsItemView(getContext());
                itemView.bindView(orderItem);
                if(canSelect && orderItem.isBack() || mOrder.getStatus().getStatusId().equals(Constants.ORDER_STATUS_WAIT_COMMENT)){
                    //显示多选框
                    itemView.showCheckBox();
                }
                layoutOrderGoods.addView(itemView);
            }
        }

        if(mOrder.getDetails() != null){
            for (OrderDetail detail : mOrder.getDetails()) {
                if(detail.getName().equals("运费") || detail.getName().equals("优惠券抵扣")) {
                    OrderDetailView detailView = new OrderDetailView(getContext());
                    detailView.bindView(detail);
                    layoutOrderDetails.addView(detailView);
                }
            }
        }

    }

    /**
     * 绑定数据
     * @param order
     */
    public void bindView(Order order){
        bindView(order, false);

    }
}
