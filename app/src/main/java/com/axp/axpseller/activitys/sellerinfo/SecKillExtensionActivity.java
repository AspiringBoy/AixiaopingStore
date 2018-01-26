package com.axp.axpseller.activitys.sellerinfo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.axp.axpseller.Constants;
import com.axp.axpseller.R;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.bean.ApplyGoodModel;
import com.axp.axpseller.models.bean.GoodsManageModel;
import com.axp.axpseller.models.bean.PostGoodResModel;
import com.axp.axpseller.models.bean.SecKillTimeResModel;
import com.axp.axpseller.models.bean.Secondskilltime;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.DialogUtil;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.ScreenConfigUtil;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.views.custom.GlideImageLoader;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.youth.banner.Banner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecKillExtensionActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener {

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
    @BindView(R.id.sec_kill_prie_edt)
    EditText secKillPrieEdt;
    @BindView(R.id.post_num_edt)
    EditText postNumEdt;
    @BindView(R.id.post_extension_btn)
    Button postExtensionBtn;
    @BindView(R.id.sec_kill_time_tv)
    TextView secKillTimeTv;
    @BindView(R.id.start_time_tv)
    TextView startTimeTv;
    @BindView(R.id.end_time_tv)
    TextView endTimeTv;
    private GoodsManageModel model;
    private ArrayList<Secondskilltime> secondskilltimeList;
    private ArrayList<String> timeList = new ArrayList<>();
    private ArrayList<String> yearList = new ArrayList<>();
    private ArrayList<String> monthList = new ArrayList<>();
    private ArrayList<String> dayList = new ArrayList<>();
    private View secTimepwView;
    private PopupWindow secTimepw, startTimepw;
    private int mIndex, mYearIndex, mMonthIndex, mDayIndex;
    private Button secTimeCalBtn, secTimeSureBtn, startTimeCalBtn, startTimeSureBtn;
    private View startTimepwView;
    private String timeId, year, month, day;
    private int type;
    private DatePicker yearWv;
    private WheelView wv;
    private Calendar calendar;
    private int startYear, startMon, startDay, endYear, endMon, endDay;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec_kill_extension);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        toolBarTitle.setText("秒杀活动");
        model = (GoodsManageModel) getIntent().getExtras().getSerializable("model");
        getCashOfTime();
    }

    private void getCashOfTime() {
        RXUtils.request(this, new Request(), "getCashOfTime", new SupportSubscriber<Response<SecKillTimeResModel>>() {

            @Override
            public void onNext(Response<SecKillTimeResModel> secKillTimeResModelResponse) {
                secondskilltimeList = secKillTimeResModelResponse.getData().getSecondskilltime();
                setData();
            }
        });
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
        for (Secondskilltime secondskilltime : secondskilltimeList) {
            timeList.add(secondskilltime.getStartTime() + "~" + secondskilltime.getEndTime() + "场");
        }
    }

    @OnClick({R.id.back_iv, R.id.sec_kill_time_rll, R.id.start_time_rll, R.id.end_time_rll, R.id.post_extension_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            //回退
            case R.id.back_iv:
                finish();
                break;
            //秒杀时段选择
            case R.id.sec_kill_time_rll:
                showSecKillTimePw();
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
        applyGoodModel.setType(Constants.EXTENSION_SEC_KILL_TYPE);
        applyGoodModel.setGoodsOrder(this.model.getGoodsOrder());
        if (!StringUtils.isBlank(secKillPrieEdt.getText().toString())) {
            applyGoodModel.setPrice(secKillPrieEdt.getText().toString());
        } else {
            DialogUtil.showNoticDialog(this, "请填写秒杀价格在进行提交!");
            return;
        }
        if (!StringUtils.isBlank(postNumEdt.getText().toString())) {
            applyGoodModel.setStock(postNumEdt.getText().toString());
        } else {
            DialogUtil.showNoticDialog(this, "请填写发布数量在进行提交!");
            return;
        }
        if (Double.parseDouble(postNumEdt.getText().toString()) > model.getStock()) {
            DialogUtil.showNoticDialog(this, "该商品发布数量不能大于库存数量!");
            return;
        }
        applyGoodModel.setTransportationType(model.getTransportationType());
        if (!startTimeTv.getText().equals("请选择开始时间")) {
            applyGoodModel.setStartedTime(startTimeNew);
        } else {
            DialogUtil.showNoticDialog(this, "请选择开始时间在进行提交!");
            return;
        }
        if (!endTimeTv.getText().toString().equals("请选择结束时间")) {
            applyGoodModel.setEndedTime(endTimeNew);
        } else {
            DialogUtil.showNoticDialog(this, "请选择结束时间在进行提交!");
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
        applyGoodModel.setSecondskilltimeId(timeId);
        request.setData(applyGoodModel);
        RXUtils.request(this, request, "applyGoods", new SupportSubscriber<Response<PostGoodResModel>>() {
            private LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(SecKillExtensionActivity.this);
                loadingDialog.show();
            }

            @Override
            public void onNext(Response<PostGoodResModel> postGoodResModelResponse) {
                if (postGoodResModelResponse.getStatus() == 1) {
                    Toast.makeText(SecKillExtensionActivity.this, "" + postGoodResModelResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    SecKillExtensionActivity.this.finish();
                }
            }

            @Override
            public void onResponseError(Response response) {
                Toast.makeText(SecKillExtensionActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }
        });
    }

    private void showSecKillTimePw() {
        ScreenConfigUtil.setBackgroundAlpha(this, 0.5f);
        if (secTimepw == null) {
            secTimepwView = LayoutInflater.from(this).inflate(R.layout.sec_kill_time_pw_item, null);
            wv = (com.wx.wheelview.widget.WheelView) secTimepwView.findViewById(R.id.sec_kill_time_wv);
            secTimeCalBtn = (Button) secTimepwView.findViewById(R.id.cal_btn);
            secTimeSureBtn = (Button) secTimepwView.findViewById(R.id.sure_btn);
            secTimeCalBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    secTimepw.dismiss();
                }
            });
            secTimeSureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mIndex = wv.getCurrentPosition();
                    for (int i = 0; i < timeList.size(); i++) {
                        if (mIndex == i) {
                            secKillTimeTv.setText(timeList.get(i));
                            secKillTimeTv.setTextColor(Color.parseColor("#222222"));
                            timeId = secondskilltimeList.get(i % secondskilltimeList.size()).getTimeId();
                        }
                    }
                    secTimepw.dismiss();
                }
            });
            wv.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
            wv.setSkin(WheelView.Skin.Holo); // common 皮肤
            wv.setLoop(true);
            wv.setWheelData(timeList);  // 数据集合
            secTimepw = new PopupWindow(secTimepwView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            secTimepw.setBackgroundDrawable(new ColorDrawable());
            secTimepw.setFocusable(true);
            secTimepw.setOutsideTouchable(false);
            secTimepw.setAnimationStyle(R.style.PwAnimBottom);
            secTimepw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    ScreenConfigUtil.setBackgroundAlpha(SecKillExtensionActivity.this, 1.0f);
                }
            });
        }
        secTimepw.showAtLocation(goodNameTv, Gravity.BOTTOM, 0, 0);
    }

    private void showStartTimePw() {
        ScreenConfigUtil.setBackgroundAlpha(this, 0.5f);
        if (startTimepw == null) {
            startTimepwView = LayoutInflater.from(this).inflate(R.layout.sec_start_time_pw_item, null);
            yearWv = (DatePicker) startTimepwView.findViewById(R.id.sec_year_time_wv);
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
            startTimepw = new PopupWindow(startTimepwView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            startTimepw.setBackgroundDrawable(new ColorDrawable());
            startTimepw.setFocusable(true);
            startTimepw.setOutsideTouchable(false);
            startTimepw.setAnimationStyle(R.style.PwAnimBottom);
            startTimepw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    ScreenConfigUtil.setBackgroundAlpha(SecKillExtensionActivity.this, 1.0f);
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
