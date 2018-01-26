package com.axp.axpseller.activitys.sellerinfo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.axp.axpseller.Constants;
import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.activitys.TaoBaoActivity;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.core.UploadSubscriber;
import com.axp.axpseller.models.ShopcategoryInfo;
import com.axp.axpseller.models.bean.GoodDetailModel;
import com.axp.axpseller.models.bean.PostGoodResModel;
import com.axp.axpseller.models.bean.SpecificationModel;
import com.axp.axpseller.models.bean.UploadFileBean;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.DensityUtils;
import com.axp.axpseller.utils.DialogUtil;
import com.axp.axpseller.utils.L;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.ScreenConfigUtil;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.views.custom.PostGoodTypeView;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.suke.widget.SwitchButton;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import yy.custombanner.BannerVp;

public class PostGoodActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, PostGoodTypeView.IDelete {

    @BindView(R.id.vp_banner)
    BannerVp topBanner;
    @BindView(R.id.good_name_edt)
    EditText goodNameEdt;
    @BindView(R.id.good_name_num)
    TextView goodNameNum;
    @BindView(R.id.right_protect_tv)
    TextView rightProtectTv;
    @BindView(R.id.send_way_tv)
    TextView sendWayTv;
    @BindView(R.id.good_type_tv)
    TextView goodTypeTv;
    @BindView(R.id.good_dt_tv)
    TextView good_dt_tv;
    @BindView(R.id.tool_bar_title)
    TextView toolBarTitle;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.camera_replace_iv)
    ImageView camera_replace_iv;
    @BindView(R.id.type_rll)
    RelativeLayout typeRll;
    @BindView(R.id.original_price)
    EditText originalPrice;
    @BindView(R.id.now_price)
    EditText nowPrice;
    @BindView(R.id.stock_edt)
    EditText stockEdt;
    @BindView(R.id.type_edt)
    EditText typeEdt;
    @BindView(R.id.type_containner)
    LinearLayout typeContainner;
    @BindView(R.id.add_type_ll)
    LinearLayout addTypeLl;
    @BindView(R.id.ori_price_ll)
    LinearLayout ori_price_ll;
    @BindView(R.id.now_price_ll)
    LinearLayout now_price_ll;
    @BindView(R.id.good_type_ll)
    RelativeLayout good_type_ll;
    @BindView(R.id.good_detail_rll)
    RelativeLayout goodDetailRll;
    @BindView(R.id.send_type_rll)
    RelativeLayout sendTypeRll;
    @BindView(R.id.sec_promiss_rll)
    RelativeLayout secPromissRll;
    @BindView(R.id.remove_time_rll)
    RelativeLayout removeTimeRll;
    @BindView(R.id.preview_btn)
    Button previewBtn;
    @BindView(R.id.post_btn)
    Button postBtn;
    @BindView(R.id.exchange_sb)
    SwitchButton exchangeSb;
    private boolean isExchangeGood = false;
    private ShopcategoryInfo getCategoryItems;
    //型号个数
    private int typeCount = 0;
    private int typeNum = 1;
    private int typeNum2 = 1;
    private int pwType;
    private View pwView;
    private TextView title, freeTv, postageTv, selfTv, moneyTv;
    private ImageView cancelIv;
    private Button sureBtn;
    private CheckBox freeCb, postageCb, selfCb, goBuyCb;
    private EditText moneyEdt;
    private PopupWindow pw;
    private SparseBooleanArray booleanArray = new SparseBooleanArray();
    private int cbCheckPos = 0;
    private LinearLayout goBuyLl;
    private int transportationType = 0;
    private List<Integer> rightProtectList = new ArrayList<>();
    private ArrayList<GoodDetailModel> goodDtList;
    private String baseGoodsId = "";
    private String goodsOrder = "";
    private List<PhotoInfo> picInfoList = new ArrayList<>();
    private List<String> imgList = new ArrayList<>();
    private List<String> topPicList = new ArrayList<>();
    private double transportationPrice = 0;
    private List<SpecificationModel> specificationList = new ArrayList<>();
    private String categoryId;
    private String specId = "";
    private LoadingDialog mLoadingDialog;
    private int bannerPos = -1;
    private String goodDtUrl = "http://seller.aixiaoping.com/share/Index/previewGoods?data=";
    private String[] rightProtects = new String[]{"正品保障", "快速发货", "售后无忧"};
    private String placeCameraUrl = "http://www.aixiaoping.com:8080/aixiaopingRes/upload-res/message_icon/1/nomal/6782271114022242819.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_good);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.show();
        toolBarTitle.setText("发布商品");
        Bundle bundle = getIntent().getExtras();
        Glide.with(this).load(placeCameraUrl).into(camera_replace_iv);
        exchangeSb.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isExchangeGood = isChecked;
            }
        });
        goodNameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                goodNameNum.setText(charSequence.toString().length() + "/50");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        booleanArray.put(0, true);
        booleanArray.put(1, false);
        booleanArray.put(2, false);
        booleanArray.put(3, false);
//        topBanner.setImageLoader(new GlideImageLoader());
//        topBanner.isAutoPlay(false);
//        startBanner(imgList);
        if (bundle != null) {
            baseGoodsId = bundle.getString("baseGoodsId");
            goodsOrder = bundle.getString("goodsOrder");
            getNetData();
        } else mLoadingDialog.dismiss();
    }

    private void getNetData() {
        Request request = new Request();
        PostGoodResModel model = new PostGoodResModel();
        model.setBaseGoodsId(baseGoodsId);
        model.setGoodsOrder(goodsOrder);
        request.setData(model);
        RXUtils.request(this, request, "getGoodsdetails", new SupportSubscriber<Response<PostGoodResModel>>() {
            @Override
            public void onNext(Response<PostGoodResModel> postGoodResModelResponse) {
                if (postGoodResModelResponse.getStatus() == 1) {
                    showView(postGoodResModelResponse.getData());
                }
            }

            @Override
            public void onResponseError(Response response) {
                mLoadingDialog.dismiss();
            }
        });
    }

    private void showView(PostGoodResModel data) {
//        startBanner(data.getTopPics());
        List<String> topPics = data.getTopPics();
        if (topPics != null && topPics.size() > 0) {
            topPicList.clear();
            imgList.clear();
            imgList.addAll(topPics);
            topBanner.setBanner(imgList, 0);
            camera_replace_iv.setVisibility(View.GONE);
            for (int i = 0; i < topPics.size(); i++) {
                String replaceAll = topPics.get(i).replaceAll("http://www.aixiaoping.com:8080/aixiaopingRes/", "");
                topPicList.add(replaceAll);
            }
        }
        goodNameEdt.setText(data.getGoodsName());
        goodTypeTv.setText(data.getCategoryName());
        categoryId = data.getCategoryId();
        originalPrice.setText(data.getOrieignPrice());
        transportationType = data.getTransportationType();
        transportationPrice = data.getTransportationPrice();
        rightProtectList.clear();
        rightProtectList.addAll(data.getRightsProtect());
        List<SpecificationModel> specificationList = data.getSpecifications();
        if (data.isHasSpecStr()) {
            typeCount = specificationList.size();
            typeNum = specificationList.size();
        }
        if (specificationList != null && specificationList.size() > 0) {
            for (int i = 0; i < specificationList.size(); i++) {
                SpecificationModel model = specificationList.get(i);
                if (i == 0) {
                    nowPrice.setText(model.getPrice());
                    stockEdt.setText(model.getStock());
                    if (!StringUtils.isBlank(model.getSpecStr())) {
                        good_type_ll.setVisibility(View.VISIBLE);
                        typeEdt.setText(model.getSpecStr());
                    }
                } else {
                    typeNum2++;
                    PostGoodTypeView goodTypeView = new PostGoodTypeView(this);
                    goodTypeView.setTypeNum(typeNum2);
                    goodTypeView.setNowPrice(model.getPrice());
                    goodTypeView.setStock(model.getStock());
                    goodTypeView.setType(model.getSpecStr());
                    goodTypeView.setOnCutListenner(this);
                    typeContainner.addView(goodTypeView);
                }
            }
        }
        mLoadingDialog.dismiss();
    }

    @OnClick({R.id.back_iv, R.id.exchange_question, R.id.del_type_iv, R.id.open_pic_iv, R.id.type_rll, R.id.add_type_ll, R.id.good_detail_rll, R.id.send_type_rll, R.id.sec_promiss_rll, R.id.remove_time_rll, R.id.preview_btn, R.id.post_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            //返回
            case R.id.back_iv:
                finish();
                break;
            //换货提示
            case R.id.exchange_question:
                DialogUtil.showNoticDialog(this, "如果您发布的商品只用作换货商品且不在周边店铺显示，请打开换货商品!");
                break;
            //打开相册
            case R.id.open_pic_iv:
                openPic();
                break;
            //类别
            case R.id.type_rll:
                Intent intent = new Intent(this, InputStoreTypeActivity.class);
                intent.putExtra("type", "PostGoodActivity");
                startActivityForResult(intent, 5);
                break;
            //删除第一个规格
            case R.id.del_type_iv:
                good_type_ll.setVisibility(View.GONE);
                typeCount--;
                resetType();
                break;
            //添加型号
            case R.id.add_type_ll:
                typeCount++;
                if (typeCount == 1) {
                    showFirstGoodType();
                } else {//如果规格1已经显示，则正常往下添加规格
                    if (good_type_ll.getVisibility() == View.VISIBLE) {
                        typeNum++;
                        PostGoodTypeView goodTypeView = new PostGoodTypeView(this);
                        goodTypeView.setTypeNum(typeNum);
                        goodTypeView.setOnCutListenner(this);
                        typeContainner.addView(goodTypeView);
                    } else {//如果规格1没显示(被手动删除的情况下),则优先显示规格1
                        showFirstGoodType();
                        resetType();
                    }
                }
                break;
            //商品详情
            case R.id.good_detail_rll:
                Intent goodDtIntent = new Intent(this, GoodDetailActivity.class);
                startActivityForResult(goodDtIntent, 8);
                break;
            //配送方式
            case R.id.send_type_rll:
                pwType = 1;
                ScreenConfigUtil.setBackgroundAlpha(this, 0.5f);
                showPw();
                break;
            //权益保障
            case R.id.sec_promiss_rll:
                pwType = 2;
                ScreenConfigUtil.setBackgroundAlpha(this, 0.5f);
                showPw();
                break;
            //下架时间
            case R.id.remove_time_rll:

                break;
            //预览
            case R.id.preview_btn:
                if (typeCount > 0) {
                    for (int i = 0; i < typeCount; i++) {
                        SpecificationModel specificationModel = new SpecificationModel();
                        if (i == 0) {
                            String price = nowPrice.getText().toString();
                            String stock = stockEdt.getText().toString();
                            String specStr = typeEdt.getText().toString();
                            if (StringUtils.isBlank(price) || StringUtils.isBlank(stock) || StringUtils.isBlank(specStr)) {
                                DialogUtil.showNoticDialog(this, "请先完善规格1中的商品信息再提交审核!");
                                return;
                            }
                            specificationModel.setPrice(price);
                            specificationModel.setStock(stock);
                            specificationModel.setSpecStr(specStr);
                            specificationModel.setSpecId(specId);
                        } else {
                            PostGoodTypeView typeView = (PostGoodTypeView) typeContainner.getChildAt(i - 1);
                            String nowPrice = typeView.getNowPrice();
                            String stock = typeView.getStock();
                            String goodType = typeView.getGoodType();
                            if (StringUtils.isBlank(nowPrice) || StringUtils.isBlank(stock) || StringUtils.isBlank(goodType)) {
                                DialogUtil.showNoticDialog(this, "请先完善规格" + (i + 1) + "中的商品信息再提交审核!");
                                return;
                            }
                            specificationModel.setPrice(nowPrice);
                            specificationModel.setStock(stock);
                            specificationModel.setSpecStr(goodType);
                            specificationModel.setSpecId(specId);
                        }
                        specificationList.add(specificationModel);
                    }
                } else {
                    SpecificationModel specificationModel = new SpecificationModel();
                    specificationModel.setPrice(nowPrice.getText().toString());
                    specificationModel.setStock(stockEdt.getText().toString());
                    specificationModel.setSpecStr("");
                    specificationModel.setSpecId(specId);
                    specificationList.add(specificationModel);
                }
                PostGoodResModel webModel = new PostGoodResModel();
                webModel.setBaseGoodsId(baseGoodsId);
                webModel.setGoodsOrder(goodsOrder);
                if (!StringUtils.isBlank(goodNameEdt.getText().toString())) {
                    webModel.setGoodsName(goodNameEdt.getText().toString());
                } else {
                    webModel.setGoodsName("");
                }
                if (!StringUtils.isBlank(categoryId)) {
                    webModel.setCategoryId(categoryId);
                } else {
                    webModel.setCategoryId("");
                }
                if (transportationType == 2) {
                    if (transportationPrice > 0) {
                        webModel.setTransportationPrice(transportationPrice);
                    } else {
                        webModel.setTransportationPrice(0);
                    }
                }
                if (transportationType != 0) {
                    webModel.setTransportationType(transportationType);
                } else {
                    webModel.setTransportationType(0);
                }
                if (originalPrice.getText().toString() != null) {
                    webModel.setOrieignPrice(originalPrice.getText().toString());
                } else {
                    webModel.setOrieignPrice("");
                }
                if (goodDtList != null) {
                    webModel.setGoodsDetail(goodDtList);
                } else {
                    webModel.setGoodsDetail(new ArrayList<GoodDetailModel>());
                }
                webModel.setTopPics(topPicList);
                webModel.setSpecifications(specificationList);
                webModel.setRightsProtect(rightProtectList);
                if (typeCount > 0) {
                    webModel.setHasSpecStr(true);
                } else {
                    webModel.setHasSpecStr(false);
                }
                String webJson = new Gson().toJson(webModel);
                Intent webIntent = new Intent(this, TaoBaoActivity.class);
                //将json参数进行url编码成web端能识别的参数
//                String encoded = toURLEncoded(webJson).replaceAll("\\+","%20");
                webIntent.putExtra("URL", goodDtUrl + webJson + "&app=android");
                webIntent.putExtra("toolbar_title", "商品详情");
                startActivity(webIntent);
                break;
            //提交审核
            case R.id.post_btn:
                if (typeCount > 0) {
                    for (int i = 0; i < typeCount; i++) {
                        SpecificationModel specificationModel = new SpecificationModel();
                        if (i == 0) {
                            String price = nowPrice.getText().toString();
                            String stock = stockEdt.getText().toString();
                            String specStr = typeEdt.getText().toString();
                            if (StringUtils.isBlank(price) || StringUtils.isBlank(stock) || StringUtils.isBlank(specStr)) {
                                DialogUtil.showNoticDialog(this, "请先完善规格1中的商品信息再提交审核!");
                                return;
                            }
                            if (!StringUtils.isBlank(originalPrice.getText().toString()) && (Double.parseDouble(originalPrice.getText().toString()) - Double.parseDouble(price)) < 0) {
                                DialogUtil.showNoticDialog(this, "商品现价不能大于原价!");
                                return;
                            }
                            specificationModel.setPrice(price);
                            specificationModel.setStock(stock);
                            specificationModel.setSpecStr(specStr);
                            specificationModel.setSpecId(specId);
                        } else {
                            PostGoodTypeView typeView = (PostGoodTypeView) typeContainner.getChildAt(i - 1);
                            String nowPrice = typeView.getNowPrice();
                            String stock = typeView.getStock();
                            String goodType = typeView.getGoodType();
                            if (StringUtils.isBlank(nowPrice) || StringUtils.isBlank(stock) || StringUtils.isBlank(goodType)) {
                                DialogUtil.showNoticDialog(this, "请先完善规格" + (i + 1) + "中的商品信息再提交审核!");
                                return;
                            }
                            if (!StringUtils.isBlank(originalPrice.getText().toString()) && (Double.parseDouble(originalPrice.getText().toString()) - Double.parseDouble(nowPrice)) < 0) {
                                DialogUtil.showNoticDialog(this, "商品现价不能大于原价!");
                                return;
                            }
                            specificationModel.setPrice(nowPrice);
                            specificationModel.setStock(stock);
                            specificationModel.setSpecStr(goodType);
                            specificationModel.setSpecId(specId);
                        }
                        specificationList.add(specificationModel);
                    }
                } else {
                    SpecificationModel specificationModel = new SpecificationModel();
                    if (StringUtils.isBlank(nowPrice.getText().toString())) {
                        DialogUtil.showNoticDialog(this, "请填写商品现价!");
                        return;
                    }
                    if (StringUtils.isBlank(stockEdt.getText().toString())) {
                        DialogUtil.showNoticDialog(this, "请填写商品库存!");
                        return;
                    }
                    specificationModel.setPrice(nowPrice.getText().toString());
                    specificationModel.setStock(stockEdt.getText().toString());
                    specificationModel.setSpecStr("");
                    specificationModel.setSpecId(specId);
                    specificationList.add(specificationModel);
                }
                Request<PostGoodResModel> request = new Request<>();
                PostGoodResModel model = new PostGoodResModel();
                model.setBaseGoodsId(baseGoodsId);
                model.setGoodsOrder(goodsOrder);
                if (!StringUtils.isBlank(goodNameEdt.getText().toString())) {
                    model.setGoodsName(goodNameEdt.getText().toString());
                } else {
                    DialogUtil.showNoticDialog(this, "商品名称不能为空!");
                    return;
                }
                if (!StringUtils.isBlank(categoryId)) {
                    model.setCategoryId(categoryId);
                } else {
                    DialogUtil.showNoticDialog(this, "商品类型不能为空!");
                    return;
                }

                if (transportationType == 2) {
                    if (transportationPrice > 0) {
                        model.setTransportationPrice(transportationPrice);
                    } else {
                        DialogUtil.showNoticDialog(this, "邮费不能为0!");
                        return;
                    }
                }
                if (transportationType != 0) {
                    model.setTransportationType(transportationType);
                } else {
                    DialogUtil.showNoticDialog(this, "请选择配送方式!");
                    return;
                }
                model.setOrieignPrice(originalPrice.getText().toString());
                if (topPicList.size() > 0) {
                    model.setTopPics(topPicList);
                } else {
                    DialogUtil.showNoticDialog(this, "请上传至少一张商品展示图!");
                    return;
                }
                if (goodDtList == null || goodDtList.size() == 0) {
                    DialogUtil.showNoticDialog(this, "请先给该商品添加商品详情再提交审核!");
                    return;
                }
                model.setGoodsDetail(goodDtList);
                model.setSpecifications(specificationList);
                model.setRightsProtect(rightProtectList);
                if (typeCount > 0) {
                    model.setHasSpecStr(true);
                } else {
                    model.setHasSpecStr(false);
                }
                model.setChang(isExchangeGood);
                LoadingDialog loading = new LoadingDialog(this);
                loading.show();
                request.setData(model);
                RXUtils.request(this, request, "publishGoods", new SupportSubscriber<Response<PostGoodResModel>>() {
                    @Override
                    public void onNext(Response<PostGoodResModel> postGoodResModelResponse) {
                        if (postGoodResModelResponse.getStatus() == 1) {
//                            PostGoodActivity.this.getSharedPreferences(Constants.REFRESH_GOOD_MANAGE_GOOD_LIST,MODE_PRIVATE).edit().putInt(Constants.KEY_OF_GOOD_MANAGE_LIST,Constants.REFRESH_GOOD_CHECK_LIST).commit();
                            DialogUtil.showNoticDialog(PostGoodActivity.this, "" + postGoodResModelResponse.getMessage(), PostGoodActivity.this);
                        }
                    }

                    @Override
                    public void onResponseError(Response response) {
                        DialogUtil.showNoticDialog(PostGoodActivity.this, "" + response.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        loading.dismiss();
                    }
                });
                break;

        }
    }

    private void showFirstGoodType() {
        good_type_ll.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ori_price_ll.getLayoutParams());
        params1.topMargin = 0;
        ori_price_ll.setLayoutParams(params1);

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(now_price_ll.getLayoutParams());
        params2.topMargin = DensityUtils.dp2px(this, 10);
        now_price_ll.setLayoutParams(params2);
    }

    private void getResult() {
        if (pwType == 1) {//配送方式
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
                        sendWayTv.setText("邮费");
                    } else if (i == 3) {
                        transportationType = 4;
                        sendWayTv.setText("到店消费");
                    }
                    return;
                }
            }
        } else if (pwType == 2) {//权益保障
            rightProtectList.clear();
            rightProtectTv.setTextColor(Color.parseColor("#222222"));
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < booleanArray.size(); j++) {
                if (booleanArray.get(j)) {
                    rightProtectList.add(j + 1);
                    if (j != booleanArray.size() - 1) {
                        builder.append(rightProtects[j]).append("、");
                    } else builder.append(rightProtects[j]);
                }
            }
            rightProtectTv.setText(builder.toString());
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
                        Toast.makeText(PostGoodActivity.this, "请填写邮费!", Toast.LENGTH_SHORT).show();
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
                    ScreenConfigUtil.setBackgroundAlpha(PostGoodActivity.this, 1.0f);
                }
            });
        }
        if (pwType == 1) {
            title.setText("请选择您的配送方式");
            freeTv.setText("包邮");
            postageTv.setText("邮费");
            selfTv.setText("到店自提");
            moneyTv.setVisibility(View.VISIBLE);
            moneyEdt.setVisibility(View.VISIBLE);
            goBuyLl.setVisibility(View.VISIBLE);
            freeCb.setChecked(false);
            postageCb.setChecked(false);
            selfCb.setChecked(false);
            goBuyCb.setChecked(false);
        } else if (pwType == 2) {
            title.setText("请选择您的权益保障的方式");
            freeTv.setText("正品保障");
            postageTv.setText("售后无忧");
            selfTv.setText("快速发货");
            moneyTv.setVisibility(View.GONE);
            moneyEdt.setVisibility(View.GONE);
            goBuyLl.setVisibility(View.INVISIBLE);
            freeCb.setChecked(false);
            postageCb.setChecked(false);
            selfCb.setChecked(false);
        }
        pw.showAtLocation(addTypeLl, Gravity.BOTTOM, 0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 6 && requestCode == 5) {
            getCategoryItems = (ShopcategoryInfo) data.getSerializableExtra("type");
            goodTypeTv.setText(getCategoryItems.getCategoryName());
            goodTypeTv.setTextColor(Color.parseColor("#222222"));
            categoryId = getCategoryItems.getCategoryId();
        }
        if (resultCode == 8 && requestCode == 8) {
            ArrayList<GoodDetailModel> resultList = (ArrayList<GoodDetailModel>) data.getExtras().getSerializable("goodDtList");
            if (resultList != null && resultList.size() > 0) {
                goodDtList = resultList;
                good_dt_tv.setText("已添加商品详情");
                good_dt_tv.setTextColor(Color.parseColor("#222222"));
            }
        }
    }

    //配送方式或权益保障pw里面的CheckBox变化监听
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
        if (pwType == 1 && b) {
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

    public void openPic() {
        if (topPicList.size() >= 5) {
            DialogUtil.showNoticDialog(this, "最多只能上传5张轮播图!");
            return;
        }
        FunctionConfig config = new FunctionConfig.Builder().setEnableCamera(true).setMutiSelectMaxSize(1).setEnableCrop(true).setEnableEdit(true).build();
        GalleryFinal.openGalleryMuti(0, config, new GalleryFinal.OnHanlderResultCallback() {
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
        RXUtils.uploadImages(PostGoodActivity.this, uploadFileBeen, new UploadSubscriber() {
            LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(PostGoodActivity.this);
                loadingDialog.show();
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }

            @Override
            public void onNext(List<Response<UploadFileBean>> responses) {
                imgList.add(responses.get(0).getData().getAbsoluteUrl());
                topPicList.add(responses.get(0).getData().getOppositeUrl());
                bannerPos++;
//                startBanner(imgList);
                if (camera_replace_iv.getVisibility() == View.VISIBLE) {
                    camera_replace_iv.setVisibility(View.GONE);
                }
                topBanner.setBanner(imgList, bannerPos);
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

    //删除规格型号
    @Override
    public void onCut(PostGoodTypeView view) {
        typeContainner.removeView(view);
        typeCount--;
        resetType();
    }

    //删除型号后重新计算规格顺序
    private void resetType() {
        int childCount = typeContainner.getChildCount();
        if (good_type_ll.getVisibility() == View.VISIBLE) {
            typeNum = childCount + 1;
            for (int i = 0; i < childCount; i++) {
                ((PostGoodTypeView) typeContainner.getChildAt(i)).setTypeNum(i + 2);
            }
        } else {
            typeNum = childCount;
            for (int i = 0; i < childCount; i++) {
                ((PostGoodTypeView) typeContainner.getChildAt(i)).setTypeNum(i + 1);
            }
        }
    }

    public static String toURLEncoded(String paramString) {
//        return Uri.encode(paramString, "-![.:/,%?&=]");
        if (paramString == null || paramString.equals("")) {
            return "";
        }
        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "utf-8");
            return str;
        } catch (Exception localException) {
        }
        return "";
    }

//    private void startBanner(List<String> imgsList) {
//        topBanner.setImages(imgsList);
//        topBanner.start();
//    }

}
