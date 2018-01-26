package com.axp.axpseller.activitys.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.flyco.tablayout.SegmentTabLayout;
import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.fragments.mall.SellerListFragment;
import com.axp.axpseller.fragments.mall.ShopConcernFragment;
import com.axp.axpseller.utils.AppUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/7/23.
 * 店铺列表
 */
public class SellerListActivity extends BaseActivity {

    /**
     * 默认选中
     * 0：全部店铺
     * 1：收藏店铺
     */
    public static final String KEY_SELECT = "KEY_SELECT";

    int select = 0;

    @BindView(R.id.tl_tab)
    SegmentTabLayout tlTab;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    String[] mTiles = new String[]{"全部店铺", "收藏店铺"};
    ArrayList<Fragment> mFragments = new ArrayList<>();

    public static ArrayList sellerList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_seller_list);
        ButterKnife.bind(this);

        loadBundle();

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mFragments.add(new SellerListFragment());
        mFragments.add(new ShopConcernFragment());

        tlTab.setTabData(mTiles, this, R.id.container, mFragments);

        tlTab.setCurrentTab(select);

    }

    private void loadBundle(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            select = bundle.getInt(KEY_SELECT, 0);
        }
    }

    @OnClick(R.id.btn_sellers_map)
    public void onClick() {

        if(sellerList == null || sellerList.size() == 0){
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(AmbitusSellerActivity.KEY_SELLER_LIST, sellerList);
        AppUtils.toActivity(this, AmbitusSellerActivity.class, bundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sellerList = null;
    }
}
