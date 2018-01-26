package com.axp.axpseller.views.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.axp.axpseller.activitys.ScoreExchangeActivity;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.fragments.mall.HomeScoreExchangeGoodsFragment;
import com.axp.axpseller.models.ImageText;
import com.axp.axpseller.utils.AppUtils;

import net.aixiaoping.library.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/6/4.
 * 首页积分兑换模块
 */
public class HomeScoreExchangeView extends FrameLayout {

    @BindView(R.id.stl_score_exchange_tab)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.vp_score_pager)
    ViewPager mPager;

    List<BaseFragment> mFragments = new ArrayList<>();
    List<String> mTitles = new ArrayList<>();

    public HomeScoreExchangeView(Context context) {
        super(context);
        initView();
    }

    public HomeScoreExchangeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HomeScoreExchangeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.home_view_score_exchange, this, true);

        ButterKnife.bind(this);

    }

    /**
     * 设置值
     *
     * @param data
     */
    public void bindData(List<ImageText> data) {

        mFragments.clear();
        mTitles.clear();

        for (ImageText imageText : data) {
            mFragments.add(HomeScoreExchangeGoodsFragment.newInstance(Integer.parseInt(imageText.getTypeId())));
            mTitles.add(imageText.getName());
        }

        mPager.setAdapter(new ScoreExchangeFragmentPageAdapter(((BaseActivity) getContext()).getSupportFragmentManager()));
        mTabLayout.setViewPager(mPager);

    }

    @OnClick(R.id.tv_more)
    public void onClick() {

        AppUtils.toActivity(getContext(), ScoreExchangeActivity.class);

    }

    class ScoreExchangeFragmentPageAdapter extends FragmentPagerAdapter {

        public ScoreExchangeFragmentPageAdapter(FragmentManager fm) {
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

