package com.axp.axpseller.activitys.sellerinfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.models.bean.GoodsManageModel;
import com.axp.axpseller.views.seller.AllCityExtensionView;
import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AllCityExtensionActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar_title)
    TextView toolBarTitle;
    @BindView(R.id.top_banner)
    Banner topBanner;
    @BindView(R.id.good_name_tv)
    TextView goodNameTv;
    @BindView(R.id.good_price_tv)
    TextView goodPriceTv;
    @BindView(R.id.send_type_tv)
    TextView sendTypeTv;
    @BindView(R.id.real_good_cb)
    CheckBox realGoodCb;
    @BindView(R.id.fast_send_cb)
    CheckBox fastSendCb;
    @BindView(R.id.after_careless_cb)
    CheckBox afterCarelessCb;
    @BindView(R.id.good_type_tv)
    TextView goodTypeTv;
    @BindView(R.id.sold_out_time_tv)
    TextView soldOutTimeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_city_extension);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        toolBarTitle.setText("全国特产");
        AllCityExtensionView allCityExtensionView = new AllCityExtensionView(this);
        allCityExtensionView.setData((GoodsManageModel) getIntent().getExtras().getSerializable("model"));
    }

    @OnClick({R.id.back_iv, R.id.post_extension_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.post_extension_btn:
                break;
        }
    }
}
