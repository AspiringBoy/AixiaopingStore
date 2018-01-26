package com.axp.axpseller.activitys.sellerinfo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.axp.axpseller.utils.CalcUtils;
import com.axp.axpseller.utils.DialogUtil;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.ScreenConfigUtil;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.views.custom.GlideImageLoader;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.suke.widget.SwitchButton;
import com.youth.banner.Banner;

import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PtExtensionActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener {

    @BindView(R.id.pt_discount_edt)
    EditText ptDiscountEdt;
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
    @BindView(R.id.notice_tv)
    TextView notice_tv;
    @BindView(R.id.notice_view_line)
    View notice_view_line;
    @BindView(R.id.notice_ll)
    LinearLayout notice_ll;
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
    private int startYear, startMon, startDay, endYear, endMon, endDay;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pt_extension);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        toolBarTitle.setText("拼团活动");
        model = (GoodsManageModel) getIntent().getExtras().getSerializable("model");
        ptDiscountEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString() != null && charSequence.toString().length() > 0) {
                    notice_ll.setVisibility(View.VISIBLE);
                    notice_view_line.setVisibility(View.VISIBLE);
                    String price = goodPriceTv.getText().toString();
                    if (price != null && price.contains("-")) {
                        String prePrice = price.substring(0, price.lastIndexOf("-"));
                        String nxtPrice = price.substring(price.lastIndexOf("-") + 1);
                        //拼团价
                        double ptPrePrice = Double.parseDouble(prePrice) - Double.parseDouble(ptDiscountEdt.getText().toString());
                        double ptNxtPrice = Double.parseDouble(nxtPrice) - Double.parseDouble(ptDiscountEdt.getText().toString());
                        //推广费比例
                        double ptCommissionPercent = Double.parseDouble(model.getPtCommissionPercent());
                        notice_tv.setText("拼团价格为" + ptPrePrice + "-" + ptNxtPrice + "元;" + "扣除推广费" + (CalcUtils.multiply(ptPrePrice, ptCommissionPercent, 2, RoundingMode.HALF_UP)) + "-" + (CalcUtils.multiply(ptNxtPrice, ptCommissionPercent, 2, RoundingMode.HALF_UP)) + "元;" + "最终商家获得" + (ptPrePrice - CalcUtils.multiply(ptPrePrice, ptCommissionPercent, 2, RoundingMode.HALF_UP)) + "-" + (ptNxtPrice - CalcUtils.multiply(ptNxtPrice, ptCommissionPercent, 2, RoundingMode.HALF_UP)) + "元");
                    } else if (price != null && !price.contains("-")) {
                        //拼团价
                        double ptPrice = Double.parseDouble(goodPriceTv.getText().toString()) - Double.parseDouble(ptDiscountEdt.getText().toString());
                        //推广费比例
                        double ptCommissionPercent = Double.parseDouble(model.getPtCommissionPercent());
                        notice_tv.setText("拼团价格为" + ptPrice + "元;" + "扣除推广费" + (CalcUtils.multiply(ptPrice, ptCommissionPercent, 2, RoundingMode.HALF_UP)) + "元;" + "最终商家获得" + (ptPrice - CalcUtils.multiply(ptPrice, ptCommissionPercent, 2, RoundingMode.HALF_UP)) + "元");
                    }
                } else {
                    notice_ll.setVisibility(View.GONE);
                    notice_view_line.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        setData();
    }

    private void setData() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR) + "年";
        month = (calendar.get(Calendar.MONTH) + 1) + "月";
        day = calendar.get(Calendar.DAY_OF_MONTH) + "日";
        topBanner.setImageLoader(new GlideImageLoader());
        topBanner.isAutoPlay(false);
        topBanner.setImages(model.getCoverPics());
        topBanner.start();
        goodNameTv.setText(model.getGoodsName());
        goodPriceTv.setText(model.getPrice());
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

    @OnClick({R.id.back_iv, R.id.start_time_rll, R.id.end_time_rll, R.id.post_extension_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            //返回
            case R.id.back_iv:
                finish();
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

    private void postData() {
        String startTime = startTimeTv.getText().toString();
        String startTimeNew = startTime.replace("年", "-").replace("月", "-").replace("日", "");
        String endTime = endTimeTv.getText().toString();
        String endTimeNew = endTime.replace("年", "-").replace("月", "-").replace("日", "");
        Request<ApplyGoodModel> request = new Request<>();
        ApplyGoodModel applyGoodModel = new ApplyGoodModel();
        applyGoodModel.setType(Constants.EXTENSION_PINTUAN_TYPE);
        applyGoodModel.setGoodsOrder(this.model.getGoodsOrder());
        applyGoodModel.setTeamNumber("2");
        if (!StringUtils.isBlank(ptDiscountEdt.getText().toString())) {
            applyGoodModel.setDiscountPrice(ptDiscountEdt.getText().toString());
        } else {
            DialogUtil.showNoticDialog(this, "请填写优惠价格!");
            return;
        }
        if (!StringUtils.isBlank(postNumEdt.getText().toString())) {
            applyGoodModel.setStock(postNumEdt.getText().toString());
        } else {
            DialogUtil.showNoticDialog(this, "请填写发布数量!");
            return;
        }
        if (Double.parseDouble(postNumEdt.getText().toString()) > model.getStock()) {
            DialogUtil.showNoticDialog(this, "该商品发布数量不能大于库存数量!");
            return;
        }
        applyGoodModel.setRestrict(isRestrict);
        if (isRestrict) {
            if (!StringUtils.isBlank(limitNumEdt.getText().toString())) {
                applyGoodModel.setRestrictNum(limitNumEdt.getText().toString());
            } else {
                DialogUtil.showNoticDialog(this, "请填写限购数量!");
                return;
            }
        }
        if (!startTimeTv.getText().toString().equals("请选择开始时间")) {
            applyGoodModel.setStartTime(startTimeNew);
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
        String startDate = getTime(startYear, startMon, startDay);
        String endDate = getTime(endYear, endMon, endDay);
        try {
            Date startLong = simpleDateFormat.parse(startDate);
            Date endLong = simpleDateFormat.parse(endDate);
            if (startLong.getTime() > endLong.getTime()) {
                DialogUtil.showNoticDialog(this, "开始时间不能大于结束时间!");
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        request.setData(applyGoodModel);
        RXUtils.request(this, request, "applyGoods", new SupportSubscriber<Response<PostGoodResModel>>() {
            private LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(PtExtensionActivity.this);
                loadingDialog.show();
            }

            @Override
            public void onNext(Response<PostGoodResModel> postGoodResModelResponse) {
                if (postGoodResModelResponse.getStatus() == 1) {
                    Toast.makeText(PtExtensionActivity.this, "" + postGoodResModelResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    PtExtensionActivity.this.finish();
                }
            }

            @Override
            public void onResponseError(Response response) {
                Toast.makeText(PtExtensionActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
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
//            monthWv = (WheelView) startTimepwView.findViewById(R.id.sec_month_time_wv);
//            dayWv = (WheelView) startTimepwView.findViewById(R.id.sec_day_time_wv);
            startTimeCalBtn = (Button) startTimepwView.findViewById(R.id.cal_btn);
            startTimeSureBtn = (Button) startTimepwView.findViewById(R.id.sure_btn);
            yearWv.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this);
            startTimeCalBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startTimepw.dismiss();
                }
            });
            startTimeSureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    year = (String) yearWv.getSelectionItem();
//                    month = (String) monthWv.getSelectionItem();
//                    day = (String) dayWv.getSelectionItem();

                    if (type == 1) {
                        startTimeTv.setText(year + month + day);
                        startTimeTv.setTextColor(Color.parseColor("#222222"));
                    } else {
                        endTimeTv.setText(year + month + day);
                        endTimeTv.setTextColor(Color.parseColor("#222222"));
                    }
                    startTimepw.dismiss();
                }
            });
            /*yearWv.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
            yearWv.setSkin(WheelView.Skin.Holo); // common 皮肤
            yearWv.setLoop(true);
            yearWv.setWheelData(yearList);  // 数据集合
            monthWv.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
            monthWv.setSkin(WheelView.Skin.Holo); // common 皮肤
            monthWv.setLoop(true);
            monthWv.setWheelData(monthList);  // 数据集合
            dayWv.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
            dayWv.setSkin(WheelView.Skin.Holo); // common 皮肤
            dayWv.setLoop(true);
            dayWv.setWheelData(dayList);  // 数据集合*/
            startTimepw = new PopupWindow(startTimepwView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            startTimepw.setBackgroundDrawable(new ColorDrawable());
            startTimepw.setFocusable(true);
            startTimepw.setOutsideTouchable(false);
            startTimepw.setAnimationStyle(R.style.PwAnimBottom);
            startTimepw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    ScreenConfigUtil.setBackgroundAlpha(PtExtensionActivity.this, 1.0f);
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
                if (type == 1) {
                    startYear = i;
                    startMon = i1 + 1;
                    startDay = i2;
                } else {
                    endYear = i;
                    endMon = i1 + 1;
                    endDay = i2;
                }
                break;
        }
    }

    private String getTime(int year, int mon, int day) {
        return String.format("%02d", year) + "年" + String.format("%02d", mon) + "月" + String.format("%02d", day) + "日";
    }

}
