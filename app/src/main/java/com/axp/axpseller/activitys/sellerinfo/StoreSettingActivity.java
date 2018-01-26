package com.axp.axpseller.activitys.sellerinfo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.axp.axpseller.Constants;
import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.activitys.TaoBaoActivity;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.core.UploadSubscriber;
import com.axp.axpseller.models.bean.StoreSettingImgModel;
import com.axp.axpseller.models.bean.StoreSettingInfo;
import com.axp.axpseller.models.bean.UploadFileBean;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.DialogUtil;
import com.axp.axpseller.utils.L;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.ScreenConfigUtil;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.views.choisCityView.MainSelectCityDialog;
import com.axp.axpseller.views.custom.GlideImageLoader;
import com.axp.axpseller.views.custom.ScrollEditText;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.citywheel.CityPickerView;
import com.suke.widget.SwitchButton;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

public class StoreSettingActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar_title)
    TextView toolBarTitle;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.right_iv)
    ImageView rightIv;
    @BindView(R.id.top_banner)
    Banner topBanner;
    @BindView(R.id.web_site_edt)
    EditText webSiteEdt;
    @BindView(R.id.store_logo_iv)
    ImageView storeLogoIv;
    @BindView(R.id.store_name_edt)
    EditText storeNameEdt;
    @BindView(R.id.open_time_tv)
    TextView openTimeTv;
    @BindView(R.id.store_introduce_edt)
    ScrollEditText storeIntroduceEdt;
    @BindView(R.id.store_introduce_num)
    TextView storeIntroduceNum;
    @BindView(R.id.store_address_tag)
    TextView storeAddressTag;
    @BindView(R.id.store_address)
    TextView storeAddress;
    @BindView(R.id.store_address_dt_edt)
    EditText storeAddressDtEdt;
    @BindView(R.id.phone_edt)
    EditText phoneEdt;
    @BindView(R.id.bottom_advert_iv)
    ImageView bottomAdvertIv;
    @BindView(R.id.store_preview_btn)
    Button storePreviewBtn;
    @BindView(R.id.store_save_btn)
    Button storeSaveBtn;
    @BindView(R.id.address_iv)
    ImageView addressIv;
    @BindView(R.id.select_time_rll)
    RelativeLayout select_time_rll;
    @BindView(R.id.foot_view)
    RelativeLayout foot_view;
    private static final int REQUEST_CODE_GALLERY = 0;
    @BindView(R.id.is_add_vd_sb)
    SwitchButton isAddVdSb;
    @BindView(R.id.post_cover_btn)
    Button postCoverBtn;
    @BindView(R.id.vd_address_edt)
    EditText vdAddressEdt;
    @BindView(R.id.vd_cover_rll)
    RelativeLayout vdCoverRll;
    @BindView(R.id.vd_address_rll)
    LinearLayout vdAddressLl;
    @BindView(R.id.open_time_tag)
    TextView openTimeTag;
    @BindView(R.id.advert_place_iv)
    ImageView advert_place_iv;
    private List<PhotoInfo> picInfoList = new ArrayList<>();
    private MainSelectCityDialog cityDialog;
    private View time_select_view;
    private PopupWindow time_select_pw;
    private Button btn_cancle, btn_sure;
    private TimePicker time_picker_right, time_picker_left;
    private String leftHour, rightHour, leftMin, rightMin;
    private String placePicUrl = "http://www.aixiaoping.com:8080/aixiaopingRes/upload-res/message_icon/1/nomal/6566810202042629819.png";
    private List<String> imgsList = new ArrayList<>();
    private List<String> bannerList = new ArrayList<>();
    private List<String> linkUrlList = new ArrayList<>();
    private boolean hasVd = false;
    private int curBannerPos = 0;
    //0:上传头部店铺图,1:上传店铺logo，2：上传广告图
    private int postImgType = 0;
    private LoadingDialog loadingDialog;
    private GeoCoder geoCoder;
    private double longitude;
    private double latitude;
    private String sellerLogoUrl, advertUrl;
    private String cityName = "";
    private boolean isVdClick;
    private boolean hasVideo;
    private String sellerMainPageId;
    private String sellerDtUrl = "http://seller.aixiaoping.com/share/Index/previewStore?data=";
    private String vdImg = "";
    private String vdUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_setting);
        ButterKnife.bind(this);
        initData();
        askNetData();
    }

    private void askNetData() {
        RXUtils.request(this, new Request(), "getstoreInfo", new SupportSubscriber<Response<StoreSettingInfo>>() {
            @Override
            public void onNext(Response<StoreSettingInfo> storeSettingInfoResponse) {
                setData(storeSettingInfoResponse);
            }

            @Override
            public void onResponseError(Response response) {
                Log.d("雨落无痕丶", "onResponseError: " + response.getMessage());
                for (int i = 0; i < 5; i++) {
                    imgsList.add(placePicUrl);
                    linkUrlList.add("");
                }
                startBanner(bannerList);
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                Log.d("雨落无痕丶", "onError: " + e.toString());
                for (int i = 0; i < 5; i++) {
                    imgsList.add(placePicUrl);
                    linkUrlList.add("");
                }
                startBanner(bannerList);
                loadingDialog.dismiss();
            }
        });
    }

    private void setData(Response<StoreSettingInfo> storeSettingInfoResponse) {
        StoreSettingInfo storeSettingInfo = storeSettingInfoResponse.getData();
        List<StoreSettingImgModel> topAds = storeSettingInfo.getTopAds();
        if (!StringUtils.isBlank(storeSettingInfo.getSellerMainPageId())) {
            sellerMainPageId = storeSettingInfo.getSellerMainPageId();
        }
        hasVideo = storeSettingInfo.isHasVideo();
        if (hasVideo && storeSettingInfo.getSellerView().getImageUrl() != null && storeSettingInfo.getSellerView().getImageUrl().length() > 0) {
            hasVd = true;
            bannerList.add(storeSettingInfo.getSellerView().getImageUrl());
            String replaceAll = storeSettingInfo.getSellerView().getImageUrl().replaceAll("http://www.aixiaoping.com:8080/aixiaopingRes/", "");
            vdImg = replaceAll;
            vdUrl = storeSettingInfo.getSellerView().getLinkUrl();
            webSiteEdt.setText("");
            webSiteEdt.setEnabled(false);
            isAddVdSb.setChecked(true);
            vdCoverRll.setVisibility(View.VISIBLE);
            vdAddressLl.setVisibility(View.VISIBLE);
            vdAddressEdt.setText(storeSettingInfo.getSellerView().getLinkUrl());
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    bannerList.add(placePicUrl);
                    imgsList.add(placePicUrl.replaceAll("http://www.aixiaoping.com:8080/aixiaopingRes/", ""));
                    linkUrlList.add("");
                }
                if (topAds != null && topAds.size() > 0) {
                    for (int j = 0; j < topAds.size(); j++) {
                        if (hasVd) {
                            imgsList.remove(j);
                            bannerList.remove(j + 1);
                            bannerList.add(j + 1, topAds.get(j).getImageUrl());
                            String replaceAll = topAds.get(j).getImageUrl().replaceAll("http://www.aixiaoping.com:8080/aixiaopingRes/", "");
                            imgsList.add(j, replaceAll);
                            linkUrlList.remove(j);
                            linkUrlList.add(j, topAds.get(j).getLinkUrl());
                        } else {
                            imgsList.remove(j);
                            bannerList.remove(j);
                            bannerList.add(j, topAds.get(j).getImageUrl());
                            String replaceAll = topAds.get(j).getImageUrl().replaceAll("http://www.aixiaoping.com:8080/aixiaopingRes/", "");
                            imgsList.add(j, replaceAll);
                            linkUrlList.remove(j);
                            linkUrlList.add(j, topAds.get(j).getLinkUrl());
                        }
                    }
                }
                Log.d("雨落无痕丶", "bannerList: "+bannerList.size());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bannerList.size() < 6) {
                            webSiteEdt.setText(linkUrlList.get(curBannerPos));
                        }else {
                            webSiteEdt.setText("");
                        }
                        startBanner(bannerList);
                        loadingDialog.dismiss();
                    }
                });
            }
        }).start();
        String sellerLogo = storeSettingInfo.getSellerLogo();
        if (sellerLogo != null && sellerLogo.length() > 0) {
            sellerLogoUrl = sellerLogo.replaceAll("http://www.aixiaoping.com:8080/aixiaopingRes/", "");
            Glide.with(this).load(sellerLogo).into(storeLogoIv);
        } else storeLogoIv.setImageResource(R.drawable.logo);
        storeNameEdt.setText(storeSettingInfo.getSellerName());
        if (storeSettingInfo.getBeginTime() != null && storeSettingInfo.getBeginTime().length() > 0) {
            openTimeTv.setTextColor(getResources().getColor(R.color.black));
            openTimeTv.setText(storeSettingInfo.getBeginTime() + "-" + storeSettingInfo.getEndTime());
        } else {
            openTimeTv.setTextColor(Color.parseColor("#999999"));
            openTimeTv.setText("请选择时间范围");
        }
        storeIntroduceEdt.setText(storeSettingInfo.getRemark());
        storeIntroduceNum.setText(storeIntroduceEdt.getText().toString().length() + "/500");
        if (StringUtils.isBlank(storeSettingInfo.getZone())) {
            storeAddress.setText("     省     市     区");
        } else {
            storeAddress.setText(storeSettingInfo.getZone());
        }
        storeAddressDtEdt.setText(storeSettingInfo.getAddress());
        phoneEdt.setText(storeSettingInfo.getPhone());
        StoreSettingImgModel bottomAds = storeSettingInfo.getBottomAds();
        if (bottomAds != null && bottomAds.getImageUrl() != null && bottomAds.getImageUrl().length() > 0) {
            advertUrl = bottomAds.getImageUrl().replaceAll("http://www.aixiaoping.com:8080/aixiaopingRes/", "");
            Glide.with(this).load(bottomAds.getImageUrl()).into(bottomAdvertIv);
            advert_place_iv.setVisibility(View.GONE);
        }
    }

    private void startBanner(List<String> imgsList) {
        topBanner.setImages(imgsList);
        topBanner.start();
    }

    private void initData() {
        toolBarTitle.setText("店铺设置");
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        topBanner.setImageLoader(new GlideImageLoader());
        time_select_view = LayoutInflater.from(this).inflate(R.layout.time_select_pw_item, null);
        btn_cancle = ((Button) time_select_view.findViewById(R.id.btn_cal));
        btn_sure = ((Button) time_select_view.findViewById(R.id.btn_slt));
        time_picker_left = ((TimePicker) time_select_view.findViewById(R.id.time_picker_left));
        time_picker_right = ((TimePicker) time_select_view.findViewById(R.id.time_picker_right));
        time_select_pw = new PopupWindow(time_select_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        time_select_pw.setBackgroundDrawable(new ColorDrawable());
        time_select_pw.setOutsideTouchable(true);
        time_select_pw.setFocusable(true);
        time_select_pw.setAnimationStyle(R.style.PwAnimBottom);
        time_select_pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ScreenConfigUtil.setBackgroundAlpha(StoreSettingActivity.this, 1.0f);
            }
        });
        time_picker_left.setIs24HourView(true);
        time_picker_right.setIs24HourView(true);
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimeTv.setTextColor(getResources().getColor(R.color.black));
                time_picker_left.getCurrentHour();
                time_picker_left.getCurrentMinute();
                time_picker_right.getCurrentHour();
                time_picker_right.getCurrentMinute();
                if (time_picker_left.getCurrentHour() < 10) {
                    leftHour = "0" + time_picker_left.getCurrentHour();
                } else {
                    leftHour = "" + time_picker_left.getCurrentHour();
                }
                if (time_picker_left.getCurrentMinute() < 10) {
                    leftMin = "0" + time_picker_left.getCurrentMinute();
                } else {
                    leftMin = "" + time_picker_left.getCurrentMinute();
                }
                if (time_picker_right.getCurrentHour() < 10) {
                    rightHour = "0" + time_picker_right.getCurrentHour();
                } else {
                    rightHour = "" + time_picker_right.getCurrentHour();
                }
                if (time_picker_right.getCurrentMinute() < 10) {
                    rightMin = "0" + time_picker_right.getCurrentMinute();
                } else {
                    rightMin = "" + time_picker_right.getCurrentMinute();
                }
                openTimeTv.setText(leftHour + ":" + leftMin + "-" + rightHour + ":" + rightMin);
                time_select_pw.dismiss();
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_select_pw.dismiss();
            }
        });
        storeIntroduceEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && charSequence.toString().length() > 0) {
                    storeIntroduceNum.setText(storeIntroduceEdt.getText().toString().length() + "/500");
                } else {
                    storeIntroduceNum.setText("0/500");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        webSiteEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!StringUtils.isBlank(editable.toString())) {
                    if (bannerList.size() > 5 && curBannerPos > 0) {
                        linkUrlList.remove(curBannerPos - 1);
                        linkUrlList.add(curBannerPos - 1, editable.toString());
                    } else {
                        linkUrlList.remove(curBannerPos);
                        linkUrlList.add(curBannerPos, editable.toString());
                    }
                }
            }
        });
        vdAddressEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!StringUtils.isBlank(editable.toString())) {
                    vdUrl = editable.toString();
                }
            }
        });
        isAddVdSb.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                hasVideo = isChecked;
                if (isChecked) {
                    vdCoverRll.setVisibility(View.VISIBLE);
                    vdAddressLl.setVisibility(View.VISIBLE);
                } else {
                    vdCoverRll.setVisibility(View.GONE);
                    vdAddressLl.setVisibility(View.GONE);
//                    if (imgsList != null && imgsList.size() > 5) {
//                        imgsList.remove(0);
//                    }
//                    if (linkUrlList != null && linkUrlList.size() > 5) {
//                        linkUrlList.remove(0);
//                    }
                }
            }
        });

        topBanner.isAutoPlay(false);
        topBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                postImgType = 0;
                isVdClick = false;
                curBannerPos = position;
                openPic();
            }
        });
        topBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position > 0 && position <= bannerList.size()) {
                    curBannerPos = position - 1;
                    if (bannerList.size() > 5) {
                        if (curBannerPos == 0) {
                            webSiteEdt.setText("");
                            webSiteEdt.setEnabled(false);
                        } else {
                            webSiteEdt.setEnabled(true);
                            String link = linkUrlList.get(curBannerPos - 1);
                            webSiteEdt.setText(link);
                        }
                    } else {
                        webSiteEdt.setEnabled(true);
                        String link = linkUrlList.get(curBannerPos);
                        webSiteEdt.setText(link);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.address_rll, R.id.vd_address_rll, R.id.open_time_tv, R.id.back_iv, R.id.post_cover_btn, R.id.store_logo_iv, R.id.bottom_advert_iv, R.id.store_preview_btn, R.id.store_save_btn, R.id.web_site_rll, R.id.address_iv, R.id.select_time_rll})
    public void onClick(View view) {
        switch (view.getId()) {
            //后退
            case R.id.back_iv:
                finish();
                break;
            //网站链接edt获取焦点
            case R.id.web_site_rll:
                setEdtFocus(webSiteEdt);
                break;
            //视频链接edt获取焦点
            case R.id.vd_address_rll:
                setEdtFocus(vdAddressEdt);
                break;
            //上传广告
            case R.id.bottom_advert_iv:
                postImgType = 2;
                openPic();
                break;
            //预览
            case R.id.store_preview_btn:
                StoreSettingInfo webInfo = new StoreSettingInfo();
                ArrayList<StoreSettingImgModel> webImgModels = new ArrayList<>();
                for (int i = 0; i < imgsList.size(); i++) {
                    StoreSettingImgModel imgModel = new StoreSettingImgModel();
                    String imageUrl = imgsList.get(i);
                    if (!StringUtils.isBlank(imageUrl) && !imageUrl.contains("upload-res/message_icon/1/nomal/6566810202042629819.png")) {
                        imgModel.setImageUrl(imageUrl);
                        imgModel.setLinkUrl(linkUrlList.get(i));
                        webImgModels.add(imgModel);
                    }
                }
                webInfo.setTopAds(webImgModels);
                if (sellerLogoUrl != null) {
                    webInfo.setSellerLogo(sellerLogoUrl);
                } else {
                    webInfo.setSellerLogo("");
                }
                if (advertUrl != null) {
                    StoreSettingImgModel model = new StoreSettingImgModel();
                    model.setImageUrl(advertUrl);
                    model.setLinkUrl("");
                    webInfo.setBottomAds(model);
                }
                if (!StringUtils.isBlank(storeNameEdt.getText().toString())) {
                    webInfo.setSellerName(storeNameEdt.getText().toString());
                } else {
                    webInfo.setSellerName("");
                }
                String webOpenTime = openTimeTv.getText().toString();
                if (!StringUtils.isBlank(webOpenTime) && !"请选择时间范围".equals(webOpenTime)) {
                    webInfo.setBeginTime(webOpenTime.substring(0, webOpenTime.indexOf("-")));
                    webInfo.setEndTime(webOpenTime.substring(webOpenTime.indexOf("-") + 1));
                } else {
                    webInfo.setBeginTime("");
                    webInfo.setEndTime("");
                }
                if (!StringUtils.isBlank(storeIntroduceEdt.getText().toString())) {
                    webInfo.setRemark(storeIntroduceEdt.getText().toString());
                } else {
                    webInfo.setRemark("");
                    return;
                }
                if (!"     省     市     区".equals(storeAddress.getText().toString())) {
                    webInfo.setZone(storeAddress.getText().toString());
                } else {
                    webInfo.setZone("");
                }
                if (!StringUtils.isBlank(storeAddressDtEdt.getText().toString())) {
                    webInfo.setAddress(storeAddressDtEdt.getText().toString());
                } else {
                    webInfo.setAddress("");
                }
                if (!StringUtils.isBlank(phoneEdt.getText().toString())) {
                    webInfo.setPhone(phoneEdt.getText().toString());
                } else {
                    webInfo.setPhone("");
                }
                webInfo.setHasVideo(hasVideo);
                webInfo.setLat("");
                webInfo.setLng("");
                if (!StringUtils.isBlank(sellerMainPageId)) {
                    webInfo.setSellerMainPageId(sellerMainPageId);
                } else webInfo.setSellerMainPageId("");
                String webJson = new Gson().toJson(webInfo);
                Intent intent = new Intent(this, TaoBaoActivity.class);
                intent.putExtra("URL", sellerDtUrl + webJson+"&app=android");
                intent.putExtra("toolbar_title", "店铺详情");
                startActivity(intent);
                break;
            //保存
            case R.id.store_save_btn:
                StoreSettingInfo info = new StoreSettingInfo();
                ArrayList<StoreSettingImgModel> imgModels = new ArrayList<>();
                for (int i = 0; i < imgsList.size(); i++) {
                    StoreSettingImgModel imgModel = new StoreSettingImgModel();
                    String imageUrl = imgsList.get(i);
                    if (!StringUtils.isBlank(imageUrl) && !imageUrl.contains("upload-res/message_icon/1/nomal/6566810202042629819.png")) {
                        imgModel.setImageUrl(imageUrl);
                        imgModel.setLinkUrl(linkUrlList.get(i));
                        imgModels.add(imgModel);
                    }
                }
                info.setTopAds(imgModels);
                info.setHasVideo(hasVideo);
                StoreSettingImgModel sellerView = new StoreSettingImgModel();
                sellerView.setImageUrl(vdImg);
                sellerView.setLinkUrl(vdUrl);
                info.setSellerView(sellerView);
                if (sellerLogoUrl != null) {
                    info.setSellerLogo(sellerLogoUrl);
                } else {
                    DialogUtil.showNoticDialog(this, "请上传店铺头像!");
                    return;
                }
                if (advertUrl != null) {
                    StoreSettingImgModel model = new StoreSettingImgModel();
                    model.setImageUrl(advertUrl);
                    model.setLinkUrl("");
                    info.setBottomAds(model);
                }
                if (!StringUtils.isBlank(storeNameEdt.getText().toString())) {
                    info.setSellerName(storeNameEdt.getText().toString());
                } else {
                    DialogUtil.showNoticDialog(this, "请填写店铺名称!");
                    return;
                }
                String openTime = openTimeTv.getText().toString();
                if (!StringUtils.isBlank(openTime) && !"请选择时间范围".equals(openTime)) {
                    info.setBeginTime(openTime.substring(0, openTime.indexOf("-")));
                    info.setEndTime(openTime.substring(openTime.indexOf("-") + 1));
                } else {
                    DialogUtil.showNoticDialog(this, "请选择营业时间!");
                    return;
                }
                if (!StringUtils.isBlank(storeIntroduceEdt.getText().toString())) {
                    info.setRemark(storeIntroduceEdt.getText().toString());
                } else {
                    DialogUtil.showNoticDialog(this, "请填写店铺介绍!");
                    return;
                }
                if (!"     省     市     区".equals(storeAddress.getText().toString())) {
                    info.setZone(storeAddress.getText().toString());
                } else {
                    DialogUtil.showNoticDialog(this, "请填写店铺地址!");
                    return;
                }
                if (!StringUtils.isBlank(storeAddressDtEdt.getText().toString())) {
                    info.setAddress(storeAddressDtEdt.getText().toString());
                } else {
                    DialogUtil.showNoticDialog(this, "请填写店铺详细地址!");
                    return;
                }
                if (!StringUtils.isBlank(phoneEdt.getText().toString())) {
                    info.setPhone(phoneEdt.getText().toString());
                } else {
                    DialogUtil.showNoticDialog(this, "请填写联系电话!");
                    return;
                }
                info.setLat("");
                info.setLng("");
                if (!StringUtils.isBlank(sellerMainPageId)) {
                    info.setSellerMainPageId(sellerMainPageId);
                } else info.setSellerMainPageId(null);
                //反地理编码获取经纬度
                /*geoCoder = GeoCoder.newInstance();
                geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

                    @Override
                    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                        if (geoCodeResult != null) {
                            latitude = geoCodeResult.getLocation().latitude;
                            longitude = geoCodeResult.getLocation().longitude;
                            info.setLat(latitude + "");
                            info.setLng(longitude + "");
                        }
                        Request<StoreSettingInfo> request = new Request<>();
                        request.setData(info);
                        RXUtils.request(StoreSettingActivity.this, request, "shopManage", new SupportSubscriber<Response>() {

                            private LoadingDialog loadingDialog;

                            @Override
                            public void onStart() {
                                loadingDialog = new LoadingDialog(StoreSettingActivity.this);
                                loadingDialog.show();
                            }

                            @Override
                            public void onNext(Response response) {
                                if (response.getStatus() == 1) {
                                    loadingDialog.dismiss();
                                    DialogUtil.showNoticDialog(StoreSettingActivity.this, "" + response.getMessage());
                                }
                            }

                            @Override
                            public void onResponseError(Response response) {
                                loadingDialog.dismiss();
                                DialogUtil.showNoticDialog(StoreSettingActivity.this, "" + response.getMessage());
                            }

                            @Override
                            public void onCompleted() {
                                if (loadingDialog != null && loadingDialog.isShowing()) {
                                    loadingDialog.dismiss();
                                }
                            }
                        });
                    }

                    @Override
                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                    }
                });
                geoCoder.geocode(new GeoCodeOption().city(cityName).address(storeAddressDtEdt.getText().toString()));*/

                Request<StoreSettingInfo> request = new Request<>();
                request.setData(info);
                RXUtils.request(this, request, "shopManage", new SupportSubscriber<Response>() {

                    private LoadingDialog loadingDialog;

                    @Override
                    public void onStart() {
                        loadingDialog = new LoadingDialog(StoreSettingActivity.this);
                        loadingDialog.show();
                    }

                    @Override
                    public void onNext(Response response) {
                        if (response.getStatus() == 1) {
                            DialogUtil.showNoticDialog(StoreSettingActivity.this, "" + response.getMessage(), StoreSettingActivity.this);
                        }
                    }

                    @Override
                    public void onResponseError(Response response) {
                        DialogUtil.showNoticDialog(StoreSettingActivity.this, "" + response.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        loadingDialog.dismiss();
                    }
                });
                break;
            //上传店铺logo
            case R.id.store_logo_iv:
                postImgType = 1;
                openPic();
                break;
            //上传视频封面
            case R.id.post_cover_btn:
                postImgType = 0;
                isVdClick = true;
                openPic();
                break;
            //选择地址
            case R.id.address_rll:
            case R.id.address_iv:
                String localPovince = ContextParameter.getCurrentLocation().getProvince();
                String localCity = ContextParameter.getCurrentLocation().getCity();
                String localDistrict = ContextParameter.getCurrentLocation().getDistrict();
                if (StringUtils.isBlank(localCity)) {
                    localPovince = "直辖市";
                    localCity = "北京";
                    localDistrict = "东城区";
                } else {
                    if (localPovince.contains("省")) {
                        localPovince = localPovince.substring(0, localPovince.indexOf("省"));
                    }
                    if (localCity.contains("市")) {
                        localCity = localCity.substring(0, localCity.indexOf("市"));
                    }
                }
                CityConfig cityConfig = new CityConfig.Builder(this)
                        .title("选择地区")
                        .titleBackgroundColor("#E9E9E9")
                        .textSize(18)
                        .titleTextColor("#585858")
                        .textColor("#000000")
                        .confirTextColor("#000000")
                        .cancelTextColor("#000000")
                        .province(localPovince)
                        .city(localCity)
                        .district(localDistrict)
                        .visibleItemsCount(5)
                        .showBackground(true)
                        .itemPadding(5)
                        .setCityInfoType(CityConfig.CityInfoType.BASE)
                        .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)
                        .build();
                CityPickerView cityPicker = new CityPickerView(cityConfig);
                cityPicker.show();
                cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {

                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        cityName = city.getName();
                        if (district != null && district.getName() != null) {
                            storeAddress.setText(province.getName() + "-" + city.getName() + "-" + district.getName());
                        } else {
                            storeAddress.setText(province.getName() + "-" + city.getName());
                        }
                    }
                });
                break;
            //选择营业时间
            case R.id.open_time_tv:
            case R.id.select_time_rll:
                ScreenConfigUtil.setBackgroundAlpha(this, 0.5f);
                time_select_pw.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
        }

    }

    private void setEdtFocus(EditText vdAddressEdt) {
        vdAddressEdt.requestFocus();
        InputMethodManager imm = (InputMethodManager) vdAddressEdt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }

    public void openPic() {
        FunctionConfig config = new FunctionConfig.Builder().setEnableCamera(true).setMutiSelectMaxSize(1).setEnableCrop(true).setEnableEdit(true).build();
        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, config, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if (resultList != null && resultList.size() > 0) {
                    picInfoList.clear();
                    picInfoList.addAll(resultList);
                    updateImg();
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }

    private void updateImg() {
        List<UploadFileBean> uploadFileBeen = new ArrayList<>();
        if (picInfoList != null && picInfoList.size() > 0) {
            for (int i = 0; i < picInfoList.size(); i++) {
                UploadFileBean bean = new UploadFileBean();
                bean.setFile(new File(picInfoList.get(i).getPhotoPath()));
                bean.setUserId(ContextParameter.getUserInfo().getUserId());
                bean.setFileUse(Constants.UPLOAD_FEED_BACK);
                uploadFileBeen.add(bean);
            }
        }
        RXUtils.uploadImages(StoreSettingActivity.this, uploadFileBeen, new UploadSubscriber() {
            LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(StoreSettingActivity.this);
                loadingDialog.show();
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }

            @Override
            public void onNext(List<Response<UploadFileBean>> responses) {
                switch (postImgType) {
                    case 0:
                        if (isVdClick) {
                            vdImg = responses.get(0).getData().getOppositeUrl();
                            if (bannerList.size() < 6) {
                                bannerList.add(0, responses.get(0).getData().getAbsoluteUrl());
                            } else {
                                bannerList.remove(0);
                                bannerList.add(0, responses.get(0).getData().getAbsoluteUrl());
                            }
                        } else {
                            if (bannerList.size() < 6) {
                                imgsList.remove(curBannerPos);
                                imgsList.add(curBannerPos, responses.get(0).getData().getOppositeUrl());
                                bannerList.remove(curBannerPos);
                                bannerList.add(curBannerPos, responses.get(0).getData().getAbsoluteUrl());
                            } else {
                                if (curBannerPos > 0) {
                                    imgsList.remove(curBannerPos - 1);
                                    imgsList.add(curBannerPos - 1, responses.get(0).getData().getOppositeUrl());
                                } else {
                                    vdImg = responses.get(0).getData().getOppositeUrl();
                                }
                                bannerList.remove(curBannerPos);
                                bannerList.add(curBannerPos, responses.get(0).getData().getAbsoluteUrl());
                            }
                        }
                        startBanner(bannerList);
                        break;
                    case 1:
                        sellerLogoUrl = responses.get(0).getData().getOppositeUrl();
                        Glide.with(StoreSettingActivity.this).load(responses.get(0).getData().getAbsoluteUrl()).into(storeLogoIv);
                        break;
                    case 2:
                        advertUrl = responses.get(0).getData().getOppositeUrl();
                        Glide.with(StoreSettingActivity.this).load(responses.get(0).getData().getAbsoluteUrl()).into(bottomAdvertIv);
                        advert_place_iv.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                L.e("==上传图片返回的错误码:" + response.getMessage());
                Log.d("雨落无痕丶", "上传图片返回的错误码: " + response.getMessage());
            }

            @Override
            public void onError(Throwable e) {
                Log.d("雨落无痕丶", "上传图片出现的异常: " + e.toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        geoCoder.destroy();
    }
}
