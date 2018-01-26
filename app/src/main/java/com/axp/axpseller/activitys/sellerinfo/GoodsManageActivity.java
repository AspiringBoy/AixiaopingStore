package com.axp.axpseller.activitys.sellerinfo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.activitys.TaoBaoActivity;
import com.axp.axpseller.fragments.sellerinfo.GoodCheckFragment;
import com.axp.axpseller.fragments.sellerinfo.SellingFragment;
import com.axp.axpseller.fragments.sellerinfo.WaitHandleFragment;
import com.axp.axpseller.utils.AppUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsManageActivity extends AppCompatActivity{

    @BindView(R.id.tool_bar_title)
    TextView toolBarTitle;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.tab_layout)
    RadioGroup radioGroup;
    @BindView(R.id.selling_rb)
    RadioButton sellingRb;
    @BindView(R.id.check_rb)
    RadioButton checkRb;
    @BindView(R.id.wait_handle_rb)
    RadioButton waitHandleRb;
    @BindView(R.id.wait_handle_line)
    View waitHandleLine;
    @BindView(R.id.selling_line)
    View sellingLine;
    @BindView(R.id.check_line)
    View checkLine;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.post_good_btn)
    Button post_good_btn;
    private List<String> mTabTitles = new ArrayList<>();
    private List<Fragment> mFgList = new ArrayList<>();
    private String extIntroduceUrl = "http://seller.aixiaoping.com/Share/Index/generalize";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_manage);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initData();
    }

    private void initData() {
        toolBarTitle.setText("商品管理");
        SellingFragment sellingFragment = new SellingFragment();
        mFgList.add(sellingFragment);
        mFgList.add(new GoodCheckFragment());
        mFgList.add(new WaitHandleFragment());
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFgList.get(position);
            }

            @Override
            public int getCount() {
                return mFgList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
//                container.removeView((View) object);
            }
        });
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        sellingRb.setChecked(true);
                        break;
                    case 1:
                        checkRb.setChecked(true);
                        break;
                    case 2:
                        waitHandleRb.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp.setOffscreenPageLimit(2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.selling_rb:
                        vp.setCurrentItem(0);
                        sellingRb.setTextColor(Color.parseColor("#ff7827"));
                        checkRb.setTextColor(Color.parseColor("#222222"));
                        waitHandleRb.setTextColor(Color.parseColor("#222222"));
                        sellingLine.setVisibility(View.VISIBLE);
                        checkLine.setVisibility(View.INVISIBLE);
                        waitHandleLine.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.check_rb:
                        vp.setCurrentItem(1);
                        checkRb.setTextColor(Color.parseColor("#ff7827"));
                        sellingRb.setTextColor(Color.parseColor("#222222"));
                        waitHandleRb.setTextColor(Color.parseColor("#222222"));
                        checkLine.setVisibility(View.VISIBLE);
                        sellingLine.setVisibility(View.INVISIBLE);
                        waitHandleLine.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.wait_handle_rb:
                        vp.setCurrentItem(2);
                        waitHandleRb.setTextColor(Color.parseColor("#ff7827"));
                        checkRb.setTextColor(Color.parseColor("#222222"));
                        sellingRb.setTextColor(Color.parseColor("#222222"));
                        waitHandleLine.setVisibility(View.VISIBLE);
                        checkLine.setVisibility(View.INVISIBLE);
                        sellingLine.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });
    }

    @OnClick({R.id.back_iv,R.id.post_good_btn,R.id.extension_introduce_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            //后退
            case R.id.back_iv:
                finish();
                break;
            //发布商品
            case R.id.post_good_btn:
                AppUtils.toActivity(this,PostGoodActivity.class);
                break;
            //推广说明
            case R.id.extension_introduce_btn:
                Intent intent = new Intent(this, TaoBaoActivity.class);
                intent.putExtra("URL",extIntroduceUrl);
                intent.putExtra("toolbar_title","推广说明");
                startActivity(intent);
                break;
        }
    }

    //更新列表数量
    @Subscribe
    public void updateCount(List<Integer> list) {
        ((RadioButton) radioGroup.getChildAt(0)).setText("出售中  ("+list.get(0)+")");
        ((RadioButton) radioGroup.getChildAt(2)).setText("商品审核  ("+list.get(1)+")");
        ((RadioButton) radioGroup.getChildAt(4)).setText("待处理  ("+list.get(2)+")");
    }
}
