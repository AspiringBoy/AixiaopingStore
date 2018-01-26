package com.axp.axpseller.fragments.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.axp.axpseller.Constants;
import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.activitys.HomeActivity;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.utils.UserUtils;
import com.axp.axpseller.views.order.OrderListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/7/1.
 * 订单列表Fragment
 */
public class OrderFragment extends BaseFragment {

    /**
     * 用于传递订单状态
     */
    public static final String KEY_ORDER_STATUS = "KEY_ORDER_STATUS";
    @BindView(R.id.layout_no_login)
    RelativeLayout layoutNoLogin;
    @BindView(R.id.layout)
    LinearLayout layout;
    /**
     * 默认显示
     */
    private String showOrderStatus = Constants.ORDER_STATUS_WAIT_PAY;

    View mView;
    @BindView(R.id.tl_tab)
    SegmentTabLayout tlTab;
    @BindView(R.id.tool_bar_return)
    Toolbar toolBarReturn;
    @BindView(R.id.tool_bar_no_return)
    Toolbar toolBarNoReturn;
    @BindView(R.id.vp_pager)
    ViewPager vpPager;

    String[] mTitles = new String[]{"待支付", "待发货", "待兑换", "待收货", "待评价", "已完成 "};
    ArrayList<OrderListView> mViews = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, mView);
        loadBundle();
        init();

        return mView;
    }

    private void loadBundle() {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            showOrderStatus = bundle.getString(KEY_ORDER_STATUS, Constants.ORDER_STATUS_WAIT_PAY);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!ContextParameter.isLogin()) {
            layoutNoLogin.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);

        } else {

            layoutNoLogin.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
        }
    }

    private void init() {

        if (getActivity() instanceof HomeActivity) {
            toolBarReturn.setVisibility(View.GONE);
            toolBarNoReturn.setVisibility(View.VISIBLE);
        } else {

            toolBarReturn.setVisibility(View.VISIBLE);
            toolBarNoReturn.setVisibility(View.GONE);
            toolBarReturn.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

        }


        /** 待支付 */
        OrderListView waitPay = new OrderListView(getActivity(), Constants.ORDER_STATUS_WAIT_PAY);
        /** 待发货 */
        OrderListView waiSendOutGoods = new OrderListView(getActivity(), Constants.ORDER_STATUS_WAIT_SEND_OUT_GOODS);
        /** 待兑换 */
        OrderListView waiExchange = new OrderListView(getActivity(), Constants.ORDER_STATUS_WAIT_EXCHANGE);
        /** 待收货 */
        OrderListView waitOfGoods = new OrderListView(getActivity(), Constants.ORDER_STATUS_WAIT_OF_GOODS);
        /** 待评价 */
        OrderListView waitComment = new OrderListView(getActivity(), Constants.ORDER_STATUS_WAIT_COMMENT);
        /** 已完成 */
        OrderListView waitEnd = new OrderListView(getActivity(), Constants.ORDER_STATUS_END);

        mViews.add(waitPay);
        mViews.add(waiSendOutGoods);
        mViews.add(waiExchange);
        mViews.add(waitOfGoods);
        mViews.add(waitComment);
        mViews.add(waitEnd);

        tlTab.setTabData(mTitles);

        vpPager.setOffscreenPageLimit(0);

        vpPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mViews.get(position));
                return mViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }

        });

        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mViews.get(position).reLoadData();
                tlTab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tlTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        switch (showOrderStatus) {
            case Constants.ORDER_STATUS_WAIT_PAY:
                tlTab.setCurrentTab(0);
                vpPager.setCurrentItem(0);
                mViews.get(0).reLoadData();
                break;
            case Constants.ORDER_STATUS_WAIT_SEND_OUT_GOODS:
                tlTab.setCurrentTab(1);
                vpPager.setCurrentItem(1);
                break;
            case Constants.ORDER_STATUS_WAIT_EXCHANGE:
                tlTab.setCurrentTab(2);
                vpPager.setCurrentItem(2);
                break;
            case Constants.ORDER_STATUS_WAIT_OF_GOODS:
                tlTab.setCurrentTab(3);
                vpPager.setCurrentItem(3);
                break;
            case Constants.ORDER_STATUS_WAIT_COMMENT:
                tlTab.setCurrentTab(4);
                vpPager.setCurrentItem(4);
                break;
            case Constants.ORDER_STATUS_END:
                tlTab.setCurrentTab(5);
                vpPager.setCurrentItem(5);
                break;
        }

    }

    @OnClick(R.id.btn_to_login)
    public void onClick() {
        UserUtils.login(getActivity());
    }
}
