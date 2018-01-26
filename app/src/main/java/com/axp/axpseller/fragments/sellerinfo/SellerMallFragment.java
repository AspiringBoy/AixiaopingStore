package com.axp.axpseller.fragments.sellerinfo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.axp.axpseller.Constants;
import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.URIResolve;
import com.axp.axpseller.activitys.CommodityWebActivity;
import com.axp.axpseller.activitys.MsgHomeActivity;
import com.axp.axpseller.activitys.sellerinfo.GoodsManageActivity;
import com.axp.axpseller.activitys.sellerinfo.InputStatusActivity;
import com.axp.axpseller.activitys.sellerinfo.MyExtensionActivity;
import com.axp.axpseller.activitys.sellerinfo.OpenShopActivity;
import com.axp.axpseller.activitys.sellerinfo.SellerAssetManagementActivity;
import com.axp.axpseller.activitys.sellerinfo.SellerEnvelopeActivity;
import com.axp.axpseller.activitys.sellerinfo.SellerHandlerBackOrderListActivity;
import com.axp.axpseller.activitys.sellerinfo.SellerOrderActivity;
import com.axp.axpseller.activitys.sellerinfo.SellerRedPackActivity;
import com.axp.axpseller.activitys.sellerinfo.ShopsPreviewActivity;
import com.axp.axpseller.activitys.sellerinfo.StoreSettingActivity;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.ImageAndText;
import com.axp.axpseller.models.ImageText;
import com.axp.axpseller.models.SellerMall;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.QRCodeUtil;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.T;
import com.axp.axpseller.views.Banner;
import com.axp.axpseller.views.custom.SuperGridView;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.axp.axpseller.views.seller.OnAdapterClickListener;
import com.axp.axpseller.views.seller.ScrollTopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/12/5.
 * 商家版首页
 */
public class SellerMallFragment extends BaseFragment {

    View mView;
    @BindView(R.id.tv_totle_money)
    TextView tvTotleMoney;
    @BindView(R.id.page_vvpAdvert)
    ScrollTopView topView;
    @BindView(R.id.tv_off_the_stocks)
    TextView tvOffTheStocks;
    @BindView(R.id.gv_layout)
    SuperGridView gvLayout;
    @BindView(R.id.banner_home_banner)
    Banner homeBanner;
    @BindView(R.id.tv_vistor)
    TextView tvVistor;
    @BindView(R.id.tv_ordernum)
    TextView tvOrdernum;
    @BindView(R.id.gv_layout2)
    SuperGridView gvLayout2;
    int verifyStatus;
    @BindView(R.id.rl_input)
    RelativeLayout rlInput;
    @BindView(R.id.seller_mall_msg_iv)
    ImageView seller_mall_msg_iv;
    @BindView(R.id.seller_mall_msg_num_tv)
    TextView seller_mall_msg_num_tv;
    @BindView(R.id.no_click_bg)
    FrameLayout no_click_bg;
    @BindView(R.id.top_alpha)
    FrameLayout top_alpha;

    private List<ImageAndText> data, datas;
    private int[] drawint = new int[]{
            R.drawable.dpsz,
            R.drawable.spgl,
            R.drawable.wdtg,
            R.drawable.hbcz,
            R.drawable.zcgl,
            R.drawable.yhzy
    };
    private String[] userString = new String[]{
            "装修和设置我的店铺", "管理与发布自己的商品","已经发布的商品进行推广", "充值公报、推广引流","账号金额的使用及管理","新人使用手册"
    };

    private int[] draws = new int[]{
            R.drawable.dpyl,
            R.drawable.wykd
    };
    private String[] storeString = new String[]{
            "", ""
    };

    private String goodsUrl, sellerUrl, directionUrl;
    private boolean isNormal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_seller_home_mall, container, false);
        ButterKnife.bind(this, mView);
        init();
//        getSellerInfo();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        topView.startAuto();
        getSellerInfo();
    }

    @Override
    public void onPause() {
        super.onPause();
        topView.cancelAuto();
    }

    private void init() {
        data = new ArrayList<>();

        datas = new ArrayList<>();

        topView.setClickListener(new OnAdapterClickListener<ImageText>() {
            @Override
            public void onAdapterClick(View v, ImageText imageText) {
                URIResolve.resolve(getActivity(), imageText.getUri());
            }
        });
        String unreadCount = getContext().getSharedPreferences("msg", Context.MODE_PRIVATE).getString("unreadCount", "");
        if (unreadCount == null || unreadCount.length() == 0) {
            seller_mall_msg_num_tv.setVisibility(View.GONE);
        } else {
            seller_mall_msg_num_tv.setVisibility(View.VISIBLE);
            seller_mall_msg_num_tv.setText(unreadCount);
        }
//        addData();
//        addStoreData();
//        gvLayout.setAdapter(userBaseAdapter);
//        gvLayout2.setAdapter(storeBaseAdapter);
    }

    private void getSellerInfo() {
        //获取商家信息
        Request<SellerMall> request = new Request<>();
        SellerMall sellerMall = new SellerMall();
        String adminuserId = ContextParameter.getUserInfo().getAdminuserId();
        if (adminuserId != null) {
            sellerMall.setAdminuserId(adminuserId);
        } else {
            sellerMall.setAdminuserId("");
        }
        String sellerId = ContextParameter.getUserInfo().getSellerId();
        if (sellerId != null) {
            sellerMall.setSellerId(sellerId);
        } else {
            sellerMall.setSellerId("");
        }
        request.setData(sellerMall);
        RXUtils.request(getActivity(), request, "getSellerHomepage", new SupportSubscriber<Response<SellerMall>>() {

            LoadingDialog dialog;

            @Override
            public void onStart() {
                dialog = new LoadingDialog(getActivity());
                dialog.show();
            }

            @Override
            public void onCompleted() {
                dialog.dismiss();
            }

            @Override
            public void onNext(Response<SellerMall> sellerMallResponse) {
//                Log.d("雨落无痕丶", "onNext: sss");
                setData(sellerMallResponse.getData());
            }
        });
    }


    private void setData(SellerMall sellerMall) {
        datas.clear();
        data.clear();
        goodsUrl = sellerMall.getGoodsUrl();
        sellerUrl = sellerMall.getSellerUrl();
        directionUrl = sellerMall.getDirectionUrl();
        tvTotleMoney.setText(sellerMall.getTotalMoney());

        topView.setData(sellerMall.getNews());

        tvVistor.setText(sellerMall.getVsitors());

        tvOrdernum.setText(sellerMall.getTodayOrderNum());
        List<ImageText> bottomBanners = sellerMall.getBottomBanners();
        if (bottomBanners != null && bottomBanners.size() > 0) {
            homeBanner.setImageTexts(bottomBanners);
        }
        verifyStatus = sellerMall.getVerifyStatus();
        if (verifyStatus == 1) {
            isNormal = true;
            addData();
            addStoreData();
            gvLayout.setAdapter(userBaseAdapter);
            gvLayout2.setAdapter(storeBaseAdapter);
            rlInput.setVisibility(View.GONE);
            no_click_bg.setVisibility(View.GONE);
            top_alpha.setVisibility(View.GONE);
        } else {
            isNormal = false;
            addData2();
            addStoreData2();
            gvLayout.setAdapter(userBaseAdapter);
            gvLayout2.setAdapter(storeBaseAdapter);
            rlInput.setVisibility(View.VISIBLE);
            no_click_bg.setVisibility(View.VISIBLE);
            top_alpha.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.tv_look_at_order, R.id.tv_to_evaluate, R.id.tv_acknowledged, R.id.tv_have_evaluation, R.id.tv_off_the_stocks, R.id.iv_message, R.id.seller_mall_msg_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_look_at_order:
                AppUtils.toActivity(getActivity(), SellerOrderActivity.class);

                break;
            case R.id.tv_to_evaluate:
                Bundle bundle = new Bundle();
                bundle.putString(SellerOrderActivity.KEY_ORDER_STATUS, Constants.ORDER_STATUS_WAIT_CONFIRM);
                AppUtils.toActivity(getActivity(), SellerOrderActivity.class, bundle);

                break;
            case R.id.tv_acknowledged:

                Bundle bundle1 = new Bundle();
                bundle1.putString(SellerOrderActivity.KEY_ORDER_STATUS, Constants.ORDER_STATUS_WAIT_SEND_OUT_GOODS);
                AppUtils.toActivity(getActivity(), SellerOrderActivity.class, bundle1);

                break;
            case R.id.tv_have_evaluation:

                Bundle bundle2 = new Bundle();
                bundle2.putString(SellerOrderActivity.KEY_ORDER_STATUS, Constants.ORDER_STATUS_WAIT_EXCHANGE);
                AppUtils.toActivity(getActivity(), SellerOrderActivity.class, bundle2);

                break;
            case R.id.tv_off_the_stocks:

                AppUtils.toActivity(getActivity(), SellerHandlerBackOrderListActivity.class);

                break;

            case R.id.iv_message:
                QRCodeUtil.scan(getActivity());
//                startActivity(new Intent(CaptureActivity));
                break;
            case R.id.seller_mall_msg_iv:
                AppUtils.toActivity(getActivity(), MsgHomeActivity.class);
                if (seller_mall_msg_num_tv.getVisibility() == View.VISIBLE) {
                    seller_mall_msg_num_tv.setVisibility(View.GONE);
                }
                break;
        }
    }

    /**
     * 适配器
     */
    private BaseAdapter userBaseAdapter = new BaseAdapter() {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final int Localposition = position;
            if (convertView == null) {
                convertView = getActivity()
                        .getLayoutInflater()
                        .inflate(R.layout.item_user_personal_center, parent, false);
            }
            ImageView imageview = (ImageView) convertView
                    .findViewById(R.id.imageview_item_user_center);
            TextView textView = (TextView) convertView
                    .findViewById(R.id.textview_item_grid_user_center);
            imageview.setImageResource(data.get(position).getImageId()); // 图片
            textView.setText(data.get(position).getText());

            RelativeLayout lItem = (RelativeLayout) convertView
                    .findViewById(R.id.ll_item);// 线性布局
            lItem.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    switch (data.get(position).getId()) {
                        case 4:
                            if (verifyStatus == 1) {
                                AppUtils.toActivity(getActivity(), SellerAssetManagementActivity.class);
                            } else {
                                T.showShort(getActivity(), "您的商家身份未验证");
                            }

                            break;
                        case 1:
                            if (verifyStatus == 1) {
                                //网页版
                                /*Bundle bundle = new Bundle();
                                bundle.putString(CommodityWebActivity.KEY_LOAD_URL, goodsUrl);
                                AppUtils.toActivity(getActivity(), CommodityWebActivity.class, bundle);*/

                                //原生
                                AppUtils.toActivity(getActivity(), GoodsManageActivity.class);
                            } else {
                                if (verifyStatus == -1) {
                                    AppUtils.toActivity(getActivity(), OpenShopActivity.class);
                                } else {
                                    AppUtils.toActivity(getActivity(), InputStatusActivity.class);
                                }
                            }

                            break;
                        case 2:
                            if (verifyStatus == 1) {
                                AppUtils.toActivity(getActivity(), MyExtensionActivity.class);
                            } else {
                                T.showShort(getActivity(), "您的商家身份未验证");
                            }

                            break;
                        case 3:
                            if (verifyStatus == 1) {
                                AppUtils.toActivity(getActivity(), SellerRedPackActivity.class);
                            } else {
                                T.showShort(getActivity(), "您的商家身份未验证");
                            }


                            break;
                        case 0:
                            //旧版本的位置管理模块
                            /*if(verifyStatus == 1) {
                                AppUtils.toActivity(getActivity(), SellerAddressActivity.class);
                            }else {
                                T.showShort(getActivity(),"您的商家身份未验证");
                            }*/

                            if (verifyStatus == 1) {
                                AppUtils.toActivity(getActivity(), StoreSettingActivity.class);
                                /*Bundle bundle = new Bundle();
                                bundle.putString(StoreWebActivity.KEY_LOAD_URL, sellerUrl);
                                AppUtils.toActivity(getActivity(),StoreWebActivity.class, bundle);*/
                            } else {
                                AppUtils.toActivity(getActivity(), ShopsPreviewActivity.class);
                            }

                            break;
                        case 5:
                            //新手指引
                            Bundle bundle = new Bundle();
                            bundle.putString(CommodityWebActivity.KEY_LOAD_URL, directionUrl);
                            AppUtils.toActivity(getActivity(), CommodityWebActivity.class, bundle);
                            break;
                    }
                }
            });
            return convertView;
        }

    };

    /**
     * 开店预览店铺适配器
     */
    private BaseAdapter storeBaseAdapter = new BaseAdapter() {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final int Localposition = position;
            if (convertView == null) {
                convertView = getActivity()
                        .getLayoutInflater()
                        .inflate(R.layout.item_user_personal_center, parent, false);
            }
            ImageView imageview = (ImageView) convertView
                    .findViewById(R.id.imageview_item_user_center);
            TextView textView = (TextView) convertView
                    .findViewById(R.id.textview_item_grid_user_center);
            imageview.setImageResource(datas.get(position).getImageId()); // 图片
            textView.setText(datas.get(position).getText());
            RelativeLayout lItem = (RelativeLayout) convertView
                    .findViewById(R.id.ll_item);// 线性布局
            /*lItem.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    switch (datas.get(position).getId()) {
                        case 0:
                            AppUtils.toActivity(getActivity(), ShopsPreviewActivity.class);
                            break;
                        case 1:
                            if (verifyStatus == -1) {
                                AppUtils.toActivity(getActivity(), OpenShopActivity.class);
                            } else {
                                AppUtils.toActivity(getActivity(), InputStatusActivity.class);
                            }

                            break;
                    }
                }
            });*/
            return convertView;
        }

    };

    /**
     * 添加data数据
     */
    private void addData() {
        for (int i = 0; i < userString.length; i++) {
            ImageAndText it = new ImageAndText();
            it.setId(i);
            it.setText(userString[i]);
            it.setImageId(drawint[i]);
            data.add(it);
        }
    }

    private void addData2() {
        for (int i = 0; i < storeString.length; i++) {
            ImageAndText it = new ImageAndText();
            it.setId(i);
            it.setText(storeString[i]);
            it.setImageId(draws[i]);
            data.add(it);
        }
    }

    private void addStoreData() {
        for (int i = 0; i < storeString.length; i++) {
            ImageAndText it = new ImageAndText();
            it.setId(i);
            it.setText(storeString[i]);
            it.setImageId(draws[i]);
            datas.add(it);
        }
    }

    private void addStoreData2() {
        for (int i = 0; i < userString.length; i++) {
            ImageAndText it = new ImageAndText();
            it.setId(i);
            it.setText(userString[i]);
            it.setImageId(drawint[i]);
            datas.add(it);
        }
    }
}
