package com.axp.axpseller.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.axp.axpseller.Constants;
import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.activitys.TaoBaoActivity;
import com.axp.axpseller.activitys.sellerinfo.AllCityExtensionActivity;
import com.axp.axpseller.activitys.sellerinfo.ExchangeExtensionActivity;
import com.axp.axpseller.activitys.sellerinfo.NormalCouponExtensionActivity;
import com.axp.axpseller.activitys.sellerinfo.PtExtensionActivity;
import com.axp.axpseller.activitys.sellerinfo.ScoreExtensionActivity;
import com.axp.axpseller.activitys.sellerinfo.SecKillExtensionActivity;
import com.axp.axpseller.activitys.user.ActiveCouponExtensionActivity;
import com.axp.axpseller.models.bean.GoodsManageModel;
import com.axp.axpseller.models.bean.PermissionModel;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.DataUtil;
import com.axp.axpseller.utils.DialogUtil;
import com.axp.axpseller.utils.ScreenConfigUtil;
import com.axp.axpseller.view.IBaseListenner;
import com.axp.axpseller.views.custom.CustomDialog;
import com.axp.axpseller.views.custom.RoundImgView;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by YY on 2017/12/5.
 */
public class SellingGoodLvAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private String goodDtUrl = "http://seller.aixiaoping.com/share/Index/previewGoods?goodsId=";
    private List<GoodsManageModel> list;
    private LayoutInflater inflater;
    private TextView dialogTitle, dialogMsg;
    private View delDialogView, soldOutDialogView;
    private Button btn_cal, btn_sure, cal, sure;
    private CustomDialog customDialog, soldOutDialog;
    private IBaseListenner updateDataListenner;
    private PopupWindow pw;
    private View pwView;
    private LinearLayout allCityExtension, normalCouponExtension, activeCouponExtension, scoreExtension, secExtension, ptExtension,exchangeExtension;
    private Button calBtn;
    private List<PermissionModel> mPermissionModelList;
    private GoodsManageModel mManageModel;
    private ImageView allCityImg,normalImg,activeImg,scoreImg,sekImg, ptImg,exchangeImg;

    public void setUpdateDataListenner(IBaseListenner updateDataListenner) {
        this.updateDataListenner = updateDataListenner;
    }

    public SellingGoodLvAdapter(Context context, List<GoodsManageModel> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyHolder holder;
        if (view == null) {
            holder = new MyHolder();
            view = inflater.inflate(R.layout.selling_fg_lv_item, viewGroup, false);
            holder.goodItemView = (RelativeLayout) view.findViewById(R.id.good_item_rll);
            holder.goodIcon = (RoundImgView) view.findViewById(R.id.good_pic);
            holder.goodDesc = (TextView) view.findViewById(R.id.good_desc);
            holder.goodPrice = (TextView) view.findViewById(R.id.good_price);
            holder.goodStock = (TextView) view.findViewById(R.id.good_left_num);
            holder.good_type_tv = (TextView) view.findViewById(R.id.good_type_tv);
            holder.goodSoldNum = (TextView) view.findViewById(R.id.good_sold_num);
            holder.price_type = (TextView) view.findViewById(R.id.price_type);
            holder.good_type = (TextView) view.findViewById(R.id.good_type);
            holder.extensionTv = (TextView) view.findViewById(R.id.extension_tv);
            holder.extensionIv = (ImageView) view.findViewById(R.id.extension_iv);
            holder.good_type_price = (TextView) view.findViewById(R.id.good_type_price);
            holder.previewItemView = (LinearLayout) view.findViewById(R.id.preview_item_ll);
            holder.extensionItemView = (LinearLayout) view.findViewById(R.id.extend_item_ll);
            holder.stockItemView = (LinearLayout) view.findViewById(R.id.stock_item_ll);
            holder.soldOutItemView = (LinearLayout) view.findViewById(R.id.sold_out_item_ll);
            view.setTag(holder);
        } else holder = (MyHolder) view.getTag();

        GoodsManageModel model = list.get(i);
        Glide.with(context).load(model.getGoodsIcon()).into(holder.goodIcon);
        holder.goodDesc.setText(model.getGoodsName());
        holder.goodSoldNum.setText("已售:  " + model.getSoldNumber() + "件");
        int typeId = model.getTypeId();
        if (typeId == 1 || typeId == 2) {//全国特产、周边店铺
            holder.goodPrice.setText("￥" + model.getPrice());
            holder.good_type_tv.setText(model.getAreaName());
            holder.goodStock.setText("库存:  " + model.getStock() + "件");
        } else if (typeId == 3) {//积分商品
            holder.goodPrice.setText("积分:" + model.getScore());
            holder.good_type_tv.setText("价格:"+model.getCashPrice());
            holder.goodStock.setText("库存:  " + model.getStock() + "件");
        } else if (typeId == 4 || typeId == 5) {//普通优惠券/活动优惠券
            holder.goodPrice.setText("券后价:" + model.getDeductPrice());
            holder.good_type_tv.setText(model.getTicketsName());
            holder.goodStock.setText("券量:  " + model.getTicketsNumber());
        } else if (typeId == 6) {//秒杀
            holder.goodPrice.setText("秒杀价:" + model.getRushPrice());
            holder.good_type_tv.setText(model.getRushTimeSlot());
            holder.goodStock.setText("秒杀数:  " + model.getRushNumber());
        } else if (typeId == 7) {//拼团
            holder.goodPrice.setText("拼团价:" + model.getPrice());
            holder.good_type_tv.setText("拼团人数:"+model.getPtPeopleNumber());
            holder.goodStock.setText("库存:  " + model.getStock() + "件");
        } else if (typeId == 8) {//换货
            holder.goodPrice.setText("商品价格:" + model.getPrice());
            holder.good_type_tv.setText(model.getAreaName());
            holder.goodStock.setText("库存:  " + model.getStock() + "件");
            holder.goodSoldNum.setText("已换:  " + model.getSoldNumber() + "件");
        }
        if (typeId == 2) {
            holder.stockItemView.setVisibility(View.VISIBLE);
            holder.soldOutItemView.setVisibility(View.VISIBLE);
            holder.extensionIv.setImageResource(R.drawable.icon_extension);
            holder.extensionTv.setText("推广");
        } else {
            holder.stockItemView.setVisibility(View.GONE);
            holder.soldOutItemView.setVisibility(View.GONE);
            holder.extensionIv.setImageResource(R.drawable.icon_shanchu);
            holder.extensionTv.setText("删除");
        }
        //预览
        holder.previewItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webIntent = new Intent(context, TaoBaoActivity.class);
                webIntent.putExtra("URL", goodDtUrl + model.getGoodsOrder());
                webIntent.putExtra("toolbar_title", "商品详情");
                context.startActivity(webIntent);
            }
        });
        holder.extensionItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeId == 2) {//推广
                    showPw(view, model, model.getPermission());
                } else {//删除
                    customDialog = DialogUtil.showCustomDialog(context, R.style.customDialogStyle, R.layout.remove_good_dialog_item, 270, 130, "是否要删除该商品?", R.id.dialog_msg_tv, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.btn_cal:
                                    customDialog.dismiss();
                                    break;
                                case R.id.btn_sure:
                                    customDialog.dismiss();
                                    DataUtil.deleteGood(context, Constants.GOOD_MANAGE_SELLING_STATUS, model.getTypeId() + "",model.getBaseGoodsId(), model.getGoodsOrder(), updateDataListenner);
                                    break;
                            }
                        }
                    }, R.id.btn_cal, R.id.btn_sure);
                }
            }
        });
        holder.stockItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //有规格
                if (model.isHasSpecStr()) {
                    DataUtil.toAddStock(context, model.isHasSpecStr(), model.getSpecifications(), model.getBaseGoodsId(), model.getGoodsOrder());
                } else {
                    customDialog = DialogUtil.showCustomDialog(context, R.style.customDialogStyle, R.layout.dialog_edit_item, 260, 130, "" + model.getStock(), R.id.dialog_edit, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.btn_cal:
                                    customDialog.dismiss();
                                    break;
                                case R.id.btn_sure:
                                    customDialog.dismiss();
                                    DataUtil.postStock(context, model.isHasSpecStr(), Constants.GOOD_MANAGE_SELLING_STATUS, model.getBaseGoodsId(), model.getGoodsOrder(), customDialog.getText(R.id.dialog_edit),updateDataListenner);
                                    break;
                            }
                        }
                    }, R.id.btn_cal, R.id.btn_sure);
                }
            }
        });
        //下架
        holder.soldOutItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog = DialogUtil.showCustomDialog(context, R.style.customDialogStyle, R.layout.remove_active_good_dialog_item, 270, 130, "是否要下架该商品?", R.id.dialog_title, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.btn_cal:
                                customDialog.dismiss();
                                break;
                            case R.id.btn_sure:
                                customDialog.dismiss();
                                DataUtil.soldOutGood(context, Constants.GOOD_MANAGE_SELLING_STATUS, model.getBaseGoodsId(), model.getGoodsOrder(), updateDataListenner);
                                break;
                        }
                    }
                }, R.id.btn_cal, R.id.btn_sure);
            }
        });
        return view;
    }

    private void showPw(View view, GoodsManageModel manageModel, List<PermissionModel> permissionModelList) {
        mPermissionModelList = permissionModelList;
        mManageModel = manageModel;
        ScreenConfigUtil.setBackgroundAlpha(context,0.5f);
        if (pw == null) {
            pwView = LayoutInflater.from(context).inflate(R.layout.extension_pw_item, null);
            allCityExtension = (LinearLayout) pwView.findViewById(R.id.all_city_ll);
            normalCouponExtension = (LinearLayout) pwView.findViewById(R.id.normal_coupon_ll);
            activeCouponExtension = (LinearLayout) pwView.findViewById(R.id.active_coupon_ll);
            scoreExtension = (LinearLayout) pwView.findViewById(R.id.score_extension_ll);
            secExtension = (LinearLayout) pwView.findViewById(R.id.sec_extension_ll);
            ptExtension = (LinearLayout) pwView.findViewById(R.id.pt_extension_ll);
            exchangeExtension = (LinearLayout) pwView.findViewById(R.id.exchange_ll);
            allCityImg = (ImageView) pwView.findViewById(R.id.all_city_img);
            normalImg = (ImageView) pwView.findViewById(R.id.normal_img);
            activeImg = (ImageView) pwView.findViewById(R.id.active_img);
            scoreImg = (ImageView) pwView.findViewById(R.id.score_img);
            sekImg = (ImageView) pwView.findViewById(R.id.sek_img);
            ptImg = (ImageView) pwView.findViewById(R.id.pt_img);
            exchangeImg = (ImageView) pwView.findViewById(R.id.exchange_img);
            calBtn = (Button) pwView.findViewById(R.id.cal_btn);
            calBtn.setOnClickListener(this);
            allCityExtension.setOnClickListener(this);
            normalCouponExtension.setOnClickListener(this);
            activeCouponExtension.setOnClickListener(this);
            scoreExtension.setOnClickListener(this);
            secExtension.setOnClickListener(this);
            exchangeExtension.setOnClickListener(this);
            ptExtension.setOnClickListener(this);
            if (permissionModelList.get(0).isPermission()) {
                allCityImg.setImageResource(R.drawable.icon_specialty);
            }else {
                allCityImg.setImageResource(R.drawable.icon_specialty_bkdj);
            }
            if (permissionModelList.get(1).isPermission()) {
                normalImg.setImageResource(R.drawable.icon_ordinarycoupons);
            }else {
                normalImg.setImageResource(R.drawable.icon_ordinarycoupons_bkdj);
            }
            if (permissionModelList.get(2).isPermission()) {
                activeImg.setImageResource(R.drawable.icon_activecoupons);
            }else {
                activeImg.setImageResource(R.drawable.icon_activecoupons_bkdj);
            }
            if (permissionModelList.get(3).isPermission()) {
                scoreImg.setImageResource(R.drawable.icon_integral);
            }else {
                scoreImg.setImageResource(R.drawable.icon_integral_bkdj);
            }
            if (permissionModelList.get(4).isPermission()) {
                sekImg.setImageResource(R.drawable.icon_seckill);
            }else {
                sekImg.setImageResource(R.drawable.icon_seckill_bkdj);
            }
            if (permissionModelList.get(5).isPermission()) {
                ptImg.setImageResource(R.drawable.icon_puzzle);
            }else {
                ptImg.setImageResource(R.drawable.icon_puzzle_bkdj);
            }
            if (permissionModelList.get(6).isPermission()) {
                exchangeImg.setImageResource(R.drawable.tuiguang);
            }else {
                exchangeImg.setImageResource(R.drawable.tuiguang1);
            }
            pw = new PopupWindow(pwView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            pw.setBackgroundDrawable(new ColorDrawable());
            pw.setFocusable(true);
            pw.setOutsideTouchable(false);
            pw.setAnimationStyle(R.style.PwAnimBottom);
            pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    ScreenConfigUtil.setBackgroundAlpha(context, 1.0f);
                }
            });
        }
        pw.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //取消pw
            case R.id.cal_btn:
                if (pw != null && pw.isShowing()) {
                    pw.dismiss();
                }
                break;
            //全国特产
            case R.id.all_city_ll:
                if (mPermissionModelList.get(0).isPermission()) {
                    if (mManageModel.getPermission2().get(0).isStatus()) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("model", mManageModel);
                        AppUtils.toActivity(context, AllCityExtensionActivity.class, bundle);
                    }else {
                        showNoticDialog(mManageModel.getPermission2().get(0).getMessage());
                    }
                } else {
                    showNoticDialog(mPermissionModelList.get(0).getDesc());
                }
                pw.dismiss();
                break;
            //普通优惠券
            case R.id.normal_coupon_ll:
                if (mPermissionModelList.get(1).isPermission()) {
                    if (mManageModel.getPermission2().get(1).isStatus()) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("model", mManageModel);
                        bundle.putBoolean("hasPermission", mPermissionModelList.get(0).isPermission());
                        AppUtils.toActivity(context, NormalCouponExtensionActivity.class, bundle);
                    }else {
                        showNoticDialog(mManageModel.getPermission2().get(1).getMessage());
                    }
                } else {
                    showNoticDialog(mPermissionModelList.get(1).getDesc());
                }
                pw.dismiss();
                break;
            //活动优惠券
            case R.id.active_coupon_ll:
                if (mPermissionModelList.get(2).isPermission()) {
                    if (mManageModel.getPermission2().get(2).isStatus()) {
                        Bundle bundle = new Bundle();
                        bundle.putString(ActiveCouponExtensionActivity.KEY_LOAD_URL, Constants.ACTIVE_COUPON_EXTENSION_WEB_URL + "gid=" + mManageModel.getGoodsOrder() + "&sid=1" + "&admin_id="+ ContextParameter.getUserInfo().getAdminuserId() + "&fromPlatform=Android");
                        AppUtils.toActivity(context, ActiveCouponExtensionActivity.class, bundle);
                    }else {
                        showNoticDialog(mManageModel.getPermission2().get(2).getMessage());
                    }
                } else {
                    showNoticDialog(mPermissionModelList.get(2).getDesc());
                }
                pw.dismiss();
                break;
            //积分
            case R.id.score_extension_ll:
                if (mPermissionModelList.get(3).isPermission()) {
                    if (mManageModel.getPermission2().get(3).isStatus()) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("model", mManageModel);
                        AppUtils.toActivity(context, ScoreExtensionActivity.class, bundle);
                    }else {
                        showNoticDialog(mManageModel.getPermission2().get(3).getMessage());
                    }
                } else {
                    showNoticDialog(mPermissionModelList.get(3).getDesc());
                }
                pw.dismiss();
                break;
            //秒杀
            case R.id.sec_extension_ll:
                if (mPermissionModelList.get(4).isPermission()) {
                    if (mManageModel.getPermission2().get(4).isStatus()) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("model", mManageModel);
                        AppUtils.toActivity(context, SecKillExtensionActivity.class, bundle);
                    }else {
                        showNoticDialog(mManageModel.getPermission2().get(4).getMessage());
                    }
                } else {
                    showNoticDialog(mPermissionModelList.get(4).getDesc());
                }
                pw.dismiss();
                break;
            //拼团
            case R.id.pt_extension_ll:
                if (mPermissionModelList.get(5).isPermission()) {
                    if (mManageModel.getPermission2().get(5).isStatus()) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("model", mManageModel);
                        AppUtils.toActivity(context, PtExtensionActivity.class, bundle);
                    }else {
                        showNoticDialog(mManageModel.getPermission2().get(5).getMessage());
                    }
                } else {
                    showNoticDialog(mPermissionModelList.get(5).getDesc());
                }
                pw.dismiss();
                break;
            //换货
            case R.id.exchange_ll:
                if (mPermissionModelList.get(6).isPermission()) {
                    if (mManageModel.getPermission2().get(6).isStatus()) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("model", mManageModel);
                        AppUtils.toActivity(context, ExchangeExtensionActivity.class, bundle);
                    }else {
                        showNoticDialog(mManageModel.getPermission2().get(6).getMessage());
                    }
                } else {
                    showNoticDialog(mPermissionModelList.get(6).getDesc());
                }
                pw.dismiss();
                break;
        }
    }

    /**
     * 显示提示无推广权限的dialog
     *
     * @param desc 提示信息
     */
    private void showNoticDialog(String desc) {
        customDialog = DialogUtil.showCustomDialog(context, R.style.customDialogStyle, R.layout.dialog_notice_item, 260, 130, desc, R.id.dialog_notice_msg, new View.OnClickListener() {
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

    class MyHolder {
        RelativeLayout goodItemView;
        RoundImgView goodIcon;
        ImageView extensionIv;
        TextView goodDesc, good_type_tv,goodPrice, goodStock, goodSoldNum, price_type, good_type, good_type_price, extensionTv;
        LinearLayout previewItemView, extensionItemView, stockItemView, soldOutItemView;
    }
}
