package com.axp.axpseller.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.utils.L;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YY on 2017/9/1.
 */
public class SellerHandlerBackOrderStatusFragment extends BaseFragment {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tl_tab)
    SegmentTabLayout tlTab;
    @BindView(R.id.vp_pager)
    ViewPager vpPager;

    private View mFgView;
    private String[] mTitles = new String[]{"待审核","待支付","已退单"};
    private List<SellerHandlerBackOrderListFragment> fragmentList = new ArrayList<>();
    //待审核
    private static final String ORDER_STATUS_WAIT_AUDIT = "10";
    //待支付
    private static final String ORDER_STATUS_WAIT_PAY = "20";
    //已退单
    private static final String ORDER_STATUS_HAS_FINISH = "30";
    public static int type = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFgView = inflater.inflate(R.layout.fragment_seller_handler_back_order_status,container,false);
        ButterKnife.bind(this,mFgView);
        init();
        return mFgView;
    }

    private void init() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        Bundle args1 = new Bundle();
        args1.putString("type",ORDER_STATUS_WAIT_AUDIT);
        SellerHandlerBackOrderListFragment fragment1 = new SellerHandlerBackOrderListFragment();
        fragment1.setArguments(args1);

        Bundle args2 = new Bundle();
        args2.putString("type",ORDER_STATUS_WAIT_PAY);
        SellerHandlerBackOrderListFragment fragment2 = new SellerHandlerBackOrderListFragment();
        fragment2.setArguments(args2);

        Bundle args3 = new Bundle();
        args3.putString("type",ORDER_STATUS_HAS_FINISH);
        SellerHandlerBackOrderListFragment fragment3 = new SellerHandlerBackOrderListFragment();
        fragment3.setArguments(args3);

        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        tlTab.setTabData(mTitles);
        vpPager.setOffscreenPageLimit(0);
        vpPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tlTab.setCurrentTab(position);
                type = position;
                L.e("加载数据：" + position);
//                fragmentList.get(position).reloadDtaa();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tlTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpPager.setCurrentItem(position);
                type = position;
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }
}
