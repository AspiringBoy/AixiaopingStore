package com.axp.axpseller.activitys.sellerinfo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.axp.axpseller.Constants;
import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.bean.ApplyGoodModel;
import com.axp.axpseller.models.bean.GoodsManageModel;
import com.axp.axpseller.models.bean.PostGoodResModel;
import com.axp.axpseller.models.bean.SpecificationModel;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.DialogUtil;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.views.adapters.ExchangeExtensionSpecGvAdapter;
import com.axp.axpseller.views.custom.GlideImageLoader;
import com.axp.axpseller.views.custom.ScrollGridView;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.youth.banner.Banner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExchangeExtensionActivity extends BaseActivity implements AdapterView.OnItemClickListener {

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
    @BindView(R.id.right_protect_ll)
    LinearLayout right_protect_ll;
    @BindView(R.id.good_num_ll)
    LinearLayout goodNumLl;
    @BindView(R.id.spec_container)
    LinearLayout specContainer;
    @BindView(R.id.stock_ll)
    LinearLayout stockLl;
    @BindView(R.id.exchange_desc_edt)
    EditText exchangeDescEdt;
    @BindView(R.id.good_spec1_edt)
    EditText goodSpecEdt1;
    @BindView(R.id.good_spec2_edt)
    EditText goodSpecEdt2;
    @BindView(R.id.good_num_edt)
    EditText goodNumEdt;
    @BindView(R.id.stock_edt)
    EditText stockEdt;
    @BindView(R.id.spec_gv)
    ScrollGridView specGv;
    private GoodsManageModel model;
    private ArrayList<SpecificationModel> specifications;
    private ExchangeExtensionSpecGvAdapter mAdapter;
    private int curPos = 0;
    private ArrayList<String> noteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_extension);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        toolBarTitle.setText("换货活动");
        model = (GoodsManageModel) getIntent().getExtras().getSerializable("model");
        setData();
    }

    private void setData() {
        right_protect_ll.setVisibility(View.GONE);
        topBanner.setImageLoader(new GlideImageLoader());
        topBanner.isAutoPlay(false);
        topBanner.setImages(model.getCoverPics());
        topBanner.start();
        goodNameTv.setText(model.getGoodsName());
        goodPriceTv.setText(model.getPrice());
        sendTypeTv.setText("双方协商");
        stockEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!StringUtils.isBlank(editable.toString())) {
                    specifications.get(curPos).setStock(editable.toString());
                }
            }
        });
        specifications = model.getSpecifications();
        if (model.isHasSpecStr()) {
            goodNumLl.setVisibility(View.GONE);
            specContainer.setVisibility(View.VISIBLE);
            stockLl.setVisibility(View.VISIBLE);
            if (!StringUtils.isBlank(specifications.get(0).getStock())) {
                stockEdt.setText(specifications.get(0).getStock());
            }
            mAdapter = new ExchangeExtensionSpecGvAdapter(this, specifications);
            specGv.setAdapter(mAdapter);
            specGv.setOnItemClickListener(this);
        }else {
            goodNumLl.setVisibility(View.VISIBLE);
            specContainer.setVisibility(View.GONE);
            stockLl.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.back_iv, R.id.post_extension_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            //返回
            case R.id.back_iv:
                finish();
                break;
            //发布推广
            case R.id.post_extension_btn:
                postData();
                break;
        }
    }

    private void postData() {
        Request<ApplyGoodModel> request = new Request<>();
        ApplyGoodModel applyGoodModel = new ApplyGoodModel();
        if (StringUtils.isBlank(exchangeDescEdt.getText().toString())) {
            DialogUtil.showNoticDialog(this,"请填写商品描述");
            return;
        }
        if (goodNumLl.getVisibility() == View.VISIBLE) {
            if (StringUtils.isBlank(goodNumEdt.getText().toString())) {
                DialogUtil.showNoticDialog(this,"请填写商品数量");
                return;
            }
            applyGoodModel.setStock(goodNumEdt.getText().toString());//无规格时商品数量
        }else {
            applyGoodModel.setSpecifications(specifications);//规格
        }
        applyGoodModel.setChangeDesc(exchangeDescEdt.getText().toString());//换货商品描述
        if (!StringUtils.isBlank(goodSpecEdt1.getText().toString())) {
            noteList.add(goodSpecEdt1.getText().toString());
        }
        if (!StringUtils.isBlank(goodSpecEdt2.getText().toString())) {
            noteList.add(goodSpecEdt2.getText().toString());
        }
        applyGoodModel.setWant(noteList);//标签
        applyGoodModel.setType(Constants.EXTENSION_EXCHANGE_TYPE);
        applyGoodModel.setGoodsOrder(this.model.getGoodsOrder());
        request.setData(applyGoodModel);
        RXUtils.request(this, request, "applyGoods", new SupportSubscriber<Response<PostGoodResModel>>() {
            private LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(ExchangeExtensionActivity.this);
                loadingDialog.show();
            }

            @Override
            public void onNext(Response<PostGoodResModel> postGoodResModelResponse) {
                if (postGoodResModelResponse.getStatus() == 1) {
                    DialogUtil.showNoticDialog(ExchangeExtensionActivity.this,postGoodResModelResponse.getMessage(),ExchangeExtensionActivity.this);
                }
            }

            @Override
            public void onResponseError(Response response) {
                DialogUtil.showNoticDialog(ExchangeExtensionActivity.this,response.getMessage());
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }
        });
    }

    /**
     *
     * @param adapterView
     * @param view
     * @param i
     * @param l
     * GrideView点击监听
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        curPos = i;
        if (!StringUtils.isBlank(specifications.get(i).getStock())) {
            stockEdt.setText(specifications.get(i).getStock());
        }else {
            stockEdt.setText("");
        }
        mAdapter.changeSelect(curPos);
    }
}
