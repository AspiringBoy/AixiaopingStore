package com.axp.axpseller.activitys.sellerinfo;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.axp.axpseller.Constants;
import com.axp.axpseller.R;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.bean.ApplyGoodModel;
import com.axp.axpseller.models.bean.GoodsManageModel;
import com.axp.axpseller.models.bean.PostGoodResModel;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.DialogUtil;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.ScreenConfigUtil;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.views.custom.CustomDialog;
import com.axp.axpseller.views.custom.GlideImageLoader;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NormalCouponExtensionActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar_title)
    TextView toolBarTitle;
    @BindView(R.id.good_type_tv)
    TextView goodTypeTv;
    @BindView(R.id.duration_time_tv)
    TextView durationTimeTv;
    @BindView(R.id.pt_discount_edt)
    EditText ptDiscountEdt;
    @BindView(R.id.post_num_edt)
    EditText postNumEdt;
    @BindView(R.id.limit_num_edt)
    EditText limitNumEdt;
    @BindView(R.id.around_cb)
    CheckBox aroundCb;
    @BindView(R.id.all_city_cb)
    CheckBox allCityCb;
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
    private GoodsManageModel model;
    private List<String> durationList = new ArrayList<>();
    private PopupWindow startTimepw;
    private View startTimepwView;
    private WheelView yearWv;
    private Button startTimeCalBtn, startTimeSureBtn;
    private String durationTime;
    private String disPlay;//发布在0 周边 1 全国
    private boolean hasPermission;
    private CustomDialog customDialog;
    private CustomDialog postDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_coupon_extension);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        toolBarTitle.setText("普通优惠券");
        model = (GoodsManageModel) getIntent().getExtras().getSerializable("model");
        hasPermission = getIntent().getExtras().getBoolean("hasPermission");
        setData();
    }

    private void setData() {
        topBanner.setImageLoader(new GlideImageLoader());
        topBanner.isAutoPlay(false);
        topBanner.setImages(model.getCoverPics());
        topBanner.start();
        goodNameTv.setText(model.getGoodsName());
        goodPriceTv.setText(model.getPrice());
        goodTypeTv.setText(model.getCateName());
        String transportationType = model.getTransportationType();
        switch (Integer.parseInt(transportationType)) {
            case 1:
                sendTypeTv.setText("包邮");
                break;
            case 2:
                sendTypeTv.setText("不包邮" + "    邮费:" + model.getTransportationPrice());
                break;
            case 3:
                sendTypeTv.setText("上门自取");
                break;
            case 4:
                sendTypeTv.setText("到店消费");
                break;
        }
        aroundCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    disPlay = "0";
                    allCityCb.setChecked(false);
                }
            }
        });
        allCityCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (hasPermission) {
                        disPlay = "1";
                        aroundCb.setChecked(false);
                    } else {
                        allCityCb.setChecked(false);
                        showNoticDialog("该商品没有推广全国的权限，不能投放到全国!");
                    }
                }
            }
        });
        for (Integer integer : model.getRightsProtect()) {
            switch (integer) {
                case 1:
                    realGoodCb.setChecked(true);
                    break;
                case 2:
                    fastSendCb.setChecked(true);
                    break;
                case 3:
                    afterCarelessCb.setChecked(true);
                    break;
            }
        }
        durationList.add("03天");
        durationList.add("07天");
        durationList.add("15天");
        durationList.add("30天");
        durationList.add("90天");
    }

    @OnClick({R.id.back_iv, R.id.post_extension_btn, R.id.duration_time_rll})
    public void onClick(View view) {
        switch (view.getId()) {
            //返回
            case R.id.back_iv:
                finish();
                break;
            //券有效期
            case R.id.duration_time_rll:
                showStartTimePw();
                break;
            //发布推广
            case R.id.post_extension_btn:
                String price = goodPriceTv.getText().toString();
                if (price != null && price.contains("-")) {
                    price = price.substring(0, price.lastIndexOf("-"));
                }
                if (!StringUtils.isBlank(price) && !StringUtils.isBlank(postNumEdt.getText().toString())) {
                    if (Double.parseDouble(postNumEdt.getText().toString()) < Double.parseDouble(price) * 0.02) {
                        showNoticDialog("推广费用不能少于券后价的2%:" + Double.parseDouble(price) * 0.02 + "元");
                    } else {
                        showPostDialog();
                    }
                } else {
                    DialogUtil.showNoticDialog(this, "请先完善商品信息再进行提交!");
                    return;
                }
                break;
        }
    }

    private void postData() {
        checkNull();
        Request<ApplyGoodModel> request = new Request<>();
        ApplyGoodModel applyGoodModel = new ApplyGoodModel();
        applyGoodModel.setType(Constants.EXTENSION_NORMAL_COUPON_TYPE);
        applyGoodModel.setGoodsOrder(this.model.getGoodsOrder());
        applyGoodModel.setDisplay(disPlay);
        applyGoodModel.setTicketprice(ptDiscountEdt.getText().toString());
        applyGoodModel.setCommission(postNumEdt.getText().toString());
        applyGoodModel.setTicketnum(limitNumEdt.getText().toString());
        if (durationTime.contains("天")) {
            durationTime = durationTime.substring(0,durationTime.indexOf("天"));
        }
        applyGoodModel.setValidtime(durationTime);
        request.setData(applyGoodModel);
        RXUtils.request(this, request, "applyGoods", new SupportSubscriber<Response<PostGoodResModel>>() {
            private LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(NormalCouponExtensionActivity.this);
                loadingDialog.show();
            }

            @Override
            public void onNext(Response<PostGoodResModel> postGoodResModelResponse) {
                if (postGoodResModelResponse.getStatus() == 1) {
                    DialogUtil.showNoticDialog(NormalCouponExtensionActivity.this, "" + postGoodResModelResponse.getMessage(), NormalCouponExtensionActivity.this);
                }
            }

            @Override
            public void onResponseError(Response response) {
                DialogUtil.showNoticDialog(NormalCouponExtensionActivity.this, "" + response.getMessage());
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }
        });
    }

    private void checkNull() {
        if (StringUtils.isBlank(ptDiscountEdt.getText().toString())) {
            DialogUtil.showNoticDialog(this, "请填写优惠券价!");
            return;
        }
        if (StringUtils.isBlank(postNumEdt.getText().toString())) {
            DialogUtil.showNoticDialog(this, "请填写推广费!");
            return;
        }
        if (StringUtils.isBlank(limitNumEdt.getText().toString())) {
            DialogUtil.showNoticDialog(this, "请填写发券数量!");
            return;
        }
        if ("请选择券上架后的有效时间".equals(durationTimeTv.getText().toString())) {
            DialogUtil.showNoticDialog(this, "请选择券有效期!");
            return;
        }
    }

    private void showStartTimePw() {
        ScreenConfigUtil.setBackgroundAlpha(this, 0.5f);
        if (startTimepw == null) {
            startTimepwView = LayoutInflater.from(this).inflate(R.layout.sec_kill_time_pw_item, null);
            yearWv = (WheelView) startTimepwView.findViewById(R.id.sec_kill_time_wv);
            startTimeCalBtn = (Button) startTimepwView.findViewById(R.id.cal_btn);
            startTimeSureBtn = (Button) startTimepwView.findViewById(R.id.sure_btn);
            startTimeCalBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startTimepw.dismiss();
                }
            });
            startTimeSureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    durationTime = (String) yearWv.getSelectionItem();
                    durationTimeTv.setText(durationTime);
                    startTimepw.dismiss();
                }
            });
            yearWv.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
            yearWv.setSkin(WheelView.Skin.Holo); // common 皮肤
            yearWv.setLoop(false);
            yearWv.setWheelData(durationList);  // 数据集合
            yearWv.setSelection(2);
            startTimepw = new PopupWindow(startTimepwView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            startTimepw.setBackgroundDrawable(new ColorDrawable());
            startTimepw.setFocusable(true);
            startTimepw.setOutsideTouchable(false);
            startTimepw.setAnimationStyle(R.style.PwAnimBottom);
            startTimepw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    ScreenConfigUtil.setBackgroundAlpha(NormalCouponExtensionActivity.this, 1.0f);
                }
            });
        }
        startTimepw.showAtLocation(goodNameTv, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 显示提示的dialog
     *
     * @param desc 提示信息
     */
    private void showNoticDialog(String desc) {
        customDialog = DialogUtil.showCustomDialog(this, R.style.customDialogStyle, R.layout.dialog_notice_item, 260, 130, desc, R.id.dialog_notice_msg, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_sure:
                        customDialog.dismiss();
                        break;
                }
            }
        }, R.id.btn_sure);
    }

    private void showPostDialog() {
        checkNull();
        String price = model.getPrice();
        String fansPrice = price.contains("-") ? (Double.parseDouble(price.substring(0, price.indexOf("-"))) - Double.parseDouble(ptDiscountEdt.getText().toString())) + "-" + (Double.parseDouble(price.substring(price.indexOf("-") + 1)) - Double.parseDouble(ptDiscountEdt.getText().toString())) + "元" : (Double.parseDouble(price) - Double.parseDouble(ptDiscountEdt.getText().toString())) + "元";
        postDialog = new CustomDialog.Builder(this)
                .view(R.layout.normal_extension_post_dialog)
                .cancelTouchOut(false)
                .styleId(R.style.customDialogStyle)
                .heightDp(210)
                .widthDp(240)
                .msg(R.id.original_good_price, model.getPrice() + "元")
                .msg(R.id.extension_all_price, (Double.parseDouble(postNumEdt.getText().toString()) * Double.parseDouble(limitNumEdt.getText().toString())) + "元")
                .msg(R.id.fans_price, fansPrice)
                .msg(R.id.partner_price, postNumEdt.getText().toString() + "元")
                .addViewClick(R.id.btn_cal, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        postDialog.dismiss();
                    }
                })
                .addViewClick(R.id.btn_sure, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        postDialog.dismiss();
                        postData();
                    }
                }).build();
        postDialog.show();
    }

}
