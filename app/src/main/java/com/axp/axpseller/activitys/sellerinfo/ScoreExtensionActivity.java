package com.axp.axpseller.activitys.sellerinfo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.axp.axpseller.views.custom.GlideImageLoader;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.suke.widget.SwitchButton;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScoreExtensionActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.score_num_edt)
    TextView scoreNumEdt;
    @BindView(R.id.good_type_rll)
    RelativeLayout goodTypeRll;
    @BindView(R.id.post_num_edt)
    EditText postNumEdt;
    @BindView(R.id.is_limit_sb)
    SwitchButton isLimitSb;
    @BindView(R.id.limit_num_edt)
    EditText limitNumEdt;
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
    @BindView(R.id.start_time_tv)
    TextView startTimeTv;
    @BindView(R.id.end_time_tv)
    TextView endTimeTv;
    @BindView(R.id.transportation_notic)
    TextView sendWayTv;
    @BindView(R.id.limit_ll)
    LinearLayout limit_ll;
    @BindView(R.id.send_type_ll)
    LinearLayout send_type_ll;
    private GoodsManageModel model;
    private List<String> yearList = new ArrayList<>();
    private List<String> monthList = new ArrayList<>();
    private List<String> dayList = new ArrayList<>();
    private int type;
    private PopupWindow startTimepw;
    private View startTimepwView;
    private DatePicker yearWv, monthWv, dayWv;
    private Button startTimeCalBtn, startTimeSureBtn;
    private String year, month, day;
    private boolean isRestrict;
    private Calendar calendar;
    private View pwView;
    private PopupWindow pw;
    private TextView title, freeTv, postageTv, selfTv, moneyTv;
    private ImageView cancelIv;
    private Button sureBtn;
    private CheckBox freeCb, postageCb, selfCb, goBuyCb;
    private LinearLayout goBuyLl;
    private EditText moneyEdt;
    private int transportationType = 0;
    private double transportationPrice = 0;
    private SparseBooleanArray booleanArray = new SparseBooleanArray();
    private int cbCheckPos;
    private long scoreNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_extension);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        toolBarTitle.setText("积分活动");
        model = (GoodsManageModel) getIntent().getExtras().getSerializable("model");
        setData();
    }

    private void setData() {
        send_type_ll.setVisibility(View.GONE);
        booleanArray.put(0, false);
        booleanArray.put(1, false);
        booleanArray.put(2, false);
        booleanArray.put(3, false);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR) + "年";
        month = (calendar.get(Calendar.MONTH) + 1) + "月";
        day = calendar.get(Calendar.DAY_OF_MONTH) + "日";
        topBanner.setImageLoader(new GlideImageLoader());
        topBanner.isAutoPlay(false);
        topBanner.setImages(model.getCoverPics());
        topBanner.start();
        goodNameTv.setText(model.getGoodsName());
        String price = model.getPrice();
        goodPriceTv.setText(price);
        if (price.contains("-")) {
            long prePrice = Math.round(Double.parseDouble(price.substring(0, price.indexOf("-"))));
            long nxtPrice = Math.round(Double.parseDouble(price.substring(price.indexOf("-") + 1)));
            scoreNum = nxtPrice;
            scoreNumEdt.setText(prePrice + "-" + nxtPrice + "积分");
        } else {
            double parseDouble = Double.parseDouble(price);
            scoreNum = Math.round(parseDouble);
            scoreNumEdt.setText(Math.round(parseDouble) + "积分");
        }
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
        isLimitSb.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isRestrict = isChecked;
                if (isChecked) {
                    limit_ll.setVisibility(View.VISIBLE);
                } else limit_ll.setVisibility(View.GONE);
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
    }

    @OnClick({R.id.back_iv, R.id.start_time_rll, R.id.send_type_rll, R.id.end_time_rll, R.id.post_extension_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            //返回
            case R.id.back_iv:
                finish();
                break;
            //配送方式
            case R.id.send_type_rll:
                ScreenConfigUtil.setBackgroundAlpha(this, 0.5f);
                showPw();
                break;
            //开始时间
            case R.id.start_time_rll:
                type = 1;
                showStartTimePw();
                break;
            //结束时间
            case R.id.end_time_rll:
                type = 2;
                showStartTimePw();
                break;
            //发布推广
            case R.id.post_extension_btn:
                postData();
                break;
        }
    }

    private void showPw() {
        if (pw == null) {
            pwView = LayoutInflater.from(this).inflate(R.layout.send_way_pw_item, null);
            title = (TextView) pwView.findViewById(R.id.pw_title);
            freeTv = (TextView) pwView.findViewById(R.id.free_tv);
            postageTv = (TextView) pwView.findViewById(R.id.postage_tv);
            selfTv = (TextView) pwView.findViewById(R.id.self_tv);
            moneyTv = (TextView) pwView.findViewById(R.id.money_tv);
            cancelIv = (ImageView) pwView.findViewById(R.id.calcel_iv);
            sureBtn = (Button) pwView.findViewById(R.id.sure_btn);
            freeCb = (CheckBox) pwView.findViewById(R.id.free_cb);
            postageCb = (CheckBox) pwView.findViewById(R.id.postage_cb);
            selfCb = (CheckBox) pwView.findViewById(R.id.self_cb);
            goBuyCb = (CheckBox) pwView.findViewById(R.id.go_to_buy_cb);
            goBuyLl = (LinearLayout) pwView.findViewById(R.id.go_to_buy_ll);
            moneyEdt = (EditText) pwView.findViewById(R.id.post_money_edt);
            freeCb.setOnCheckedChangeListener(this);
            selfCb.setOnCheckedChangeListener(this);
            postageCb.setOnCheckedChangeListener(this);
            goBuyCb.setOnCheckedChangeListener(this);
            sureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getResult();
                    if (transportationType == 2 && StringUtils.isBlank(moneyEdt.getText().toString())) {
                        Toast.makeText(ScoreExtensionActivity.this, "请填写邮费!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (transportationType == 2) {
                        transportationPrice = Double.parseDouble(moneyEdt.getText().toString());
                    }
                    pw.dismiss();
                }
            });
            cancelIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (pw != null && pw.isShowing()) {
                        pw.dismiss();
                    }
                }
            });
            pw = new PopupWindow(pwView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            pw.setBackgroundDrawable(new ColorDrawable());
            pw.setFocusable(true);
            pw.setOutsideTouchable(false);
            pw.setAnimationStyle(R.style.PwAnimBottom);
            pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    ScreenConfigUtil.setBackgroundAlpha(ScoreExtensionActivity.this, 1.0f);
                }
            });
        }
        title.setText("请选择您的配送方式");
        freeTv.setText("包邮");
        postageTv.setText("邮费");
        selfTv.setText("到店自提");
        moneyTv.setVisibility(View.VISIBLE);
        freeCb.setChecked(false);
        postageCb.setChecked(false);
        selfCb.setChecked(false);
        goBuyCb.setChecked(false);
        pw.showAtLocation(goodPriceTv, Gravity.BOTTOM, 0, 0);
    }

    private void getResult() {
        for (int i = 0; i < booleanArray.size(); i++) {
            if (booleanArray.get(i)) {
                sendWayTv.setTextColor(Color.parseColor("#222222"));
                if (i == 0) {
                    transportationType = 1;
                    sendWayTv.setText("包邮");
                } else if (i == 1) {
                    transportationType = 3;
                    sendWayTv.setText("到店自提");
                } else if (i == 2) {
                    transportationType = 2;
                    sendWayTv.setText("邮费:" + transportationPrice);
                } else if (i == 3) {
                    transportationType = 4;
                    sendWayTv.setText("到店消费");
                }
                return;
            }
        }
    }

    private void postData() {
        String startTime = startTimeTv.getText().toString();
        String startTimeNew = startTime.replace("年", "-").replace("月", "-").replace("日", "");
        String endTime = endTimeTv.getText().toString();
        String endTimeNew = endTime.replace("年", "-").replace("月", "-").replace("日", "");
        Request<ApplyGoodModel> request = new Request<>();
        ApplyGoodModel applyGoodModel = new ApplyGoodModel();
        applyGoodModel.setType(Constants.EXTENSION_SCORE_TYPE);
        applyGoodModel.setGoodsOrder(this.model.getGoodsOrder());
        if (scoreNum > 0) {
            applyGoodModel.setScore(scoreNum + "");
        } else {
            DialogUtil.showNoticDialog(this, "积分售价异常!");
            return;
        }

        if (transportationType != 0) {
            applyGoodModel.setTransportationType(transportationType + "");
        } else {
            DialogUtil.showNoticDialog(this, "请选择配送方式!");
            return;
        }
        if (transportationType == 2) {//不包邮
            if (transportationPrice > 0) {//邮费
                applyGoodModel.setTransportationPrice(transportationPrice + "");
            } else {
                DialogUtil.showNoticDialog(this, "邮费不能为0!");
                return;
            }
        } else {
            applyGoodModel.setTransportationPrice("0");
        }
        if (!StringUtils.isBlank(postNumEdt.getText().toString())) {
            applyGoodModel.setStock(postNumEdt.getText().toString());
        } else {
            DialogUtil.showNoticDialog(this, "请输入发布数量!");
            return;
        }
        if (isRestrict && !StringUtils.isBlank(limitNumEdt.getText().toString())) {
            applyGoodModel.setPurchaseNum(limitNumEdt.getText().toString());
        } else if (isRestrict && StringUtils.isBlank(limitNumEdt.getText().toString())) {
            DialogUtil.showNoticDialog(this, "请输入限购数量!");
            return;
        }
        if (!startTimeTv.getText().toString().equals("请选择开始时间")) {
            applyGoodModel.setStartedTime(startTimeNew);
        } else {
            DialogUtil.showNoticDialog(this, "请选择开始时间!");
            return;
        }
        if (!endTimeTv.getText().toString().equals("请选择结束时间")) {
            applyGoodModel.setEndTime(endTimeNew);
        } else {
            DialogUtil.showNoticDialog(this, "请选择结束时间!");
            return;
        }
        applyGoodModel.setRestrict(isRestrict);
        request.setData(applyGoodModel);
        RXUtils.request(this, request, "applyGoods", new SupportSubscriber<Response<PostGoodResModel>>() {
            private LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(ScoreExtensionActivity.this);
                loadingDialog.show();
            }

            @Override
            public void onNext(Response<PostGoodResModel> postGoodResModelResponse) {
                if (postGoodResModelResponse.getStatus() == 1) {
                    Toast.makeText(ScoreExtensionActivity.this, "" + postGoodResModelResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    ScoreExtensionActivity.this.finish();
                }
            }

            @Override
            public void onResponseError(Response response) {
                Toast.makeText(ScoreExtensionActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }
        });
    }

    private void showStartTimePw() {
        ScreenConfigUtil.setBackgroundAlpha(this, 0.5f);
        if (startTimepw == null) {
            startTimepwView = LayoutInflater.from(this).inflate(R.layout.sec_start_time_pw_item, null);
            yearWv = (DatePicker) startTimepwView.findViewById(R.id.sec_year_time_wv);
            startTimeCalBtn = (Button) startTimepwView.findViewById(R.id.cal_btn);
            startTimeSureBtn = (Button) startTimepwView.findViewById(R.id.sure_btn);
            int year = calendar.get(Calendar.YEAR);
            int i = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            yearWv.init(year, i, dayOfMonth, this);
            startTimeCalBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startTimepw.dismiss();
                }
            });
            startTimeSureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (type == 1) {
                        startTimeTv.setText(ScoreExtensionActivity.this.year + month + day);
                        startTimeTv.setTextColor(Color.parseColor("#222222"));
                    } else {
                        endTimeTv.setText(ScoreExtensionActivity.this.year + month + day);
                        endTimeTv.setTextColor(Color.parseColor("#222222"));
                    }
                    startTimepw.dismiss();
                }
            });
            startTimepw = new PopupWindow(startTimepwView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            startTimepw.setBackgroundDrawable(new ColorDrawable());
            startTimepw.setFocusable(true);
            startTimepw.setOutsideTouchable(false);
            startTimepw.setAnimationStyle(R.style.PwAnimBottom);
            startTimepw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    ScreenConfigUtil.setBackgroundAlpha(ScoreExtensionActivity.this, 1.0f);
                }
            });
        }
        startTimepw.showAtLocation(goodNameTv, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        switch (datePicker.getId()) {
            case R.id.sec_year_time_wv:
                year = i + "年";
                month = i1 + 1 + "月";
                day = i2 + "日";
                break;
        }
    }

    //配送方式pw里面的CheckBox变化监听
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            //包邮/正品保障
            case R.id.free_cb:
                booleanArray.put(0, b);
                cbCheckPos = 0;
                break;
            //到店自提/快速发货
            case R.id.self_cb:
                booleanArray.put(1, b);
                cbCheckPos = 1;
                break;
            //邮费/售后无忧
            case R.id.postage_cb:
                booleanArray.put(2, b);
                cbCheckPos = 2;
                break;
            //到店消费
            case R.id.go_to_buy_cb:
                booleanArray.put(3, b);
                cbCheckPos = 3;
                break;
        }
        if (b) {
            checkOtherFalse();
        }
    }

    private void checkOtherFalse() {
        switch (cbCheckPos) {
            case 0:
                selfCb.setChecked(false);
                postageCb.setChecked(false);
                goBuyCb.setChecked(false);
                break;
            case 1:
                freeCb.setChecked(false);
                postageCb.setChecked(false);
                goBuyCb.setChecked(false);
                break;
            case 2:
                selfCb.setChecked(false);
                freeCb.setChecked(false);
                goBuyCb.setChecked(false);
                break;
            case 3:
                selfCb.setChecked(false);
                freeCb.setChecked(false);
                postageCb.setChecked(false);
                break;
        }
    }

}
