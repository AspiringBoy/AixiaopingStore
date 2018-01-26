package com.axp.axpseller.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.activitys.sellerinfo.WithdrawDetailsActivity;
import com.axp.axpseller.activitys.sellerinfo.WithdrawInfoActivity;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.utils.AppUtils;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/12/12.
 * 提现列表  未确认跟确认
 */
public class WithdrawFragment extends BaseFragment {

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    List<BaseFragment> mFragments = new ArrayList<>();
    ApplyListFragment fragment2 = new ApplyListFragment();
    ApplyForPayListFragment fragment1 = new ApplyForPayListFragment();
    List<String> mTitles = new ArrayList<>();
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.stl_score_exchange_tab)
    SlidingTabLayout mTab;
    @BindView(R.id.vp_main_mall_search_shop_list)
    ViewPager vp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_without_list, container, false);

        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mTitles.add("已确认");
        mTitles.add("未确认");
        vp.setAdapter(new ApplyFragmentPageAdapter(getActivity().getSupportFragmentManager()));
        mTab.setViewPager(vp);
    }

    @OnClick({R.id.tv_info, R.id.btn_withdraw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_info:
                if (fragment2.isChecked() != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("isChecked", fragment2.isChecked());
                    //跳转到资料界面
                    AppUtils.toActivity(getActivity(), WithdrawInfoActivity.class, bundle);
                }

                break;
            case R.id.btn_withdraw:
                //跳到提现界面
                Log.d("雨落无痕丶", "isChecked: " + fragment2.isChecked());
                if (fragment2.isChecked().equals("1") || fragment2.isChecked().equals("2")) {
                    AppUtils.toActivity(getActivity(), WithdrawDetailsActivity.class);
                } else if (fragment2.isChecked().equals("0")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("isChecked", fragment2.isChecked());
                    AppUtils.toActivity(getActivity(), WithdrawInfoActivity.class, bundle);
                }
                break;
        }
    }

    class ApplyFragmentPageAdapter extends FragmentPagerAdapter {

        public ApplyFragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        @Override
        public int getCount() {
            return mTitles.size();
        }
    }
}
