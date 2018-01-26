package com.axp.axpseller.views.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.axp.axpseller.Constants;
import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.activitys.sellerinfo.AllCityExtensionActivity;
import com.axp.axpseller.activitys.sellerinfo.ExchangeExtensionActivity;
import com.axp.axpseller.activitys.sellerinfo.NormalCouponExtensionActivity;
import com.axp.axpseller.activitys.sellerinfo.PtExtensionActivity;
import com.axp.axpseller.activitys.sellerinfo.ScoreExtensionActivity;
import com.axp.axpseller.activitys.sellerinfo.SecKillExtensionActivity;
import com.axp.axpseller.activitys.user.ActiveCouponExtensionActivity;
import com.axp.axpseller.models.bean.GoodsManageModel;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.DataUtil;
import com.axp.axpseller.utils.DialogUtil;
import com.axp.axpseller.view.IBaseListenner;
import com.axp.axpseller.views.custom.CustomDialog;
import com.axp.axpseller.views.custom.RoundImgView;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by YY on 2017/12/5.
 */
public class GoodCheckLvAdapter extends BaseAdapter {

    private Context context;
    private List<GoodsManageModel> list;
    private LayoutInflater inflater;

    public void setUpdateListenner(IBaseListenner updateListenner) {
        this.updateListenner = updateListenner;
    }

    private IBaseListenner updateListenner;
    private CustomDialog customDialog;

    public GoodCheckLvAdapter(Context context, List<GoodsManageModel> list) {
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
            view = inflater.inflate(R.layout.good_check_fg_lv_item, viewGroup, false);
            holder.goodItemView = (RelativeLayout) view.findViewById(R.id.good_item_rll);
            holder.goodIcon = (RoundImgView) view.findViewById(R.id.good_pic);
            holder.goodDesc = (TextView) view.findViewById(R.id.good_desc);
            holder.goodPrice = (TextView) view.findViewById(R.id.good_price);
            holder.goodStock = (TextView) view.findViewById(R.id.good_left_num);
            holder.goodSoldNum = (TextView) view.findViewById(R.id.good_sold_num);
            holder.price_type = (TextView) view.findViewById(R.id.price_type);
            holder.good_type = (TextView) view.findViewById(R.id.good_type);
            holder.good_type_price = (TextView) view.findViewById(R.id.good_type_price);
            holder.deleteItemView = (LinearLayout) view.findViewById(R.id.delete_item_ll);
            holder.deleteItemView = (LinearLayout) view.findViewById(R.id.delete_item_ll);
            holder.editItemView = (LinearLayout) view.findViewById(R.id.edit_item_ll);
            holder.check_faild_question = (RelativeLayout) view.findViewById(R.id.check_faild_question);
            view.setTag(holder);
        } else holder = (MyHolder) view.getTag();

        GoodsManageModel model = list.get(i);
        Glide.with(context).load(model.getGoodsIcon()).into(holder.goodIcon);
        holder.goodDesc.setText(model.getGoodsName());
        holder.goodStock.setText("库存:  " + model.getStock() + "件");
        int statusId = model.getStatusId();
        if (statusId == 1000) {
            holder.goodSoldNum.setText("状态:出售中");
            holder.check_faild_question.setVisibility(View.GONE);
        } else if (statusId == 1001) {
            holder.goodSoldNum.setText("状态:审核中");
            holder.check_faild_question.setVisibility(View.GONE);
        } else if (statusId == 1002) {
            holder.goodSoldNum.setText("状态:审核失败");
            holder.check_faild_question.setVisibility(View.VISIBLE);
        } else if (statusId == 1003) {
            holder.goodSoldNum.setText("状态:待处理");
            holder.check_faild_question.setVisibility(View.GONE);
        }
        holder.check_faild_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.showNoticDialog(context,model.getCheckFailedMsg());
            }
        });
        int typeId = model.getTypeId();
        if (typeId == 1 || typeId == 2) {//全国特产、周边店铺
            holder.goodPrice.setText("￥" + model.getPrice());
            holder.price_type.setVisibility(View.GONE);
            holder.good_type.setVisibility(View.VISIBLE);
            holder.good_type_price.setVisibility(View.GONE);
            holder.good_type.setText(model.getAreaName());
        } else if (typeId == 3) {//积分商品
            holder.goodPrice.setText(model.getScore() + "");
            holder.price_type.setVisibility(View.VISIBLE);
            holder.good_type.setVisibility(View.VISIBLE);
            holder.good_type_price.setVisibility(View.VISIBLE);
            holder.price_type.setText("积分:");
            holder.good_type.setText("价格:");
            holder.good_type_price.setText(model.getCashPrice());
        } else if (typeId == 4) {//普通优惠券
            holder.goodPrice.setText("￥" + model.getDeductPrice());
            holder.price_type.setVisibility(View.VISIBLE);
            holder.good_type.setVisibility(View.VISIBLE);
            holder.good_type_price.setVisibility(View.GONE);
            holder.price_type.setText("券后价:");
            holder.good_type.setText("普通优惠券");
        } else if (typeId == 5) {//活动优惠券
            holder.goodPrice.setText("￥" + model.getDeductPrice());
            holder.price_type.setVisibility(View.VISIBLE);
            holder.good_type.setVisibility(View.VISIBLE);
            holder.good_type_price.setVisibility(View.GONE);
            holder.price_type.setText("券后价:");
            holder.good_type.setText("活动优惠券");
        } else if (typeId == 6) {//秒杀
            holder.goodPrice.setText("￥" + model.getRushPrice());
            holder.price_type.setVisibility(View.VISIBLE);
            holder.good_type.setVisibility(View.VISIBLE);
            holder.good_type_price.setVisibility(View.GONE);
            holder.price_type.setText("秒杀价:");
            holder.good_type.setText(model.getRushTimeSlot());
        } else if (typeId == 7) {//拼团
            holder.goodPrice.setText("￥" + model.getPrice());
            holder.price_type.setVisibility(View.VISIBLE);
            holder.good_type.setVisibility(View.VISIBLE);
            holder.good_type_price.setVisibility(View.GONE);
            holder.price_type.setText("拼团价:");
            holder.good_type.setText("拼团人数:" + model.getPtPeopleNumber() + "人");
        } else if (typeId == 8) {//换货
            holder.goodPrice.setText("￥" + model.getPrice());
            holder.price_type.setVisibility(View.VISIBLE);
            holder.good_type.setVisibility(View.VISIBLE);
            holder.good_type_price.setVisibility(View.GONE);
            holder.price_type.setText("商品价:");
            holder.good_type.setText("换货商品");
        }
        //删除
        holder.deleteItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog = DialogUtil.showCustomDialog(context, R.style.customDialogStyle, R.layout.remove_good_dialog_item, 270, 130, "是否要删除该商品?", R.id.dialog_msg_tv, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.btn_cal:
                                customDialog.dismiss();
                                break;
                            case R.id.btn_sure:
                                customDialog.dismiss();
                                DataUtil.deleteGood(context, Constants.GOOD_MANAGE_CHECK_STATUS, model.getTypeId() + "", model.getBaseGoodsId(), model.getGoodsOrder(), updateListenner);
                                break;
                        }
                    }
                }, R.id.btn_cal, R.id.btn_sure);
            }
        });
        //编辑
        holder.editItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (statusId != 1001) {
                    switch (typeId) {
                        //全国
                        case 1:
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("model", model);
                            AppUtils.toActivity(context, AllCityExtensionActivity.class, bundle);
                            break;
                        //周边
                        case 2:
                            DataUtil.toEditActivity(context, model.getBaseGoodsId(), model.getGoodsOrder());
                            break;
                        //积分
                        case 3:
                            Bundle bundle1 = new Bundle();
                            bundle1.putSerializable("model", model);
                            AppUtils.toActivity(context, ScoreExtensionActivity.class, bundle1);
                            break;
                        //普通优惠券
                        case 4:
                            Bundle bundle2 = new Bundle();
                            bundle2.putSerializable("model", model);
                            bundle2.putBoolean("hasPermission", model.getPermission().get(0).isPermission());
                            AppUtils.toActivity(context, NormalCouponExtensionActivity.class, bundle2);
                            break;
                        //活动优惠券
                        case 5:
                            Bundle bundle3 = new Bundle();
                            bundle3.putString(ActiveCouponExtensionActivity.KEY_LOAD_URL, Constants.ACTIVE_COUPON_EXTENSION_WEB_URL + "gid=" + model.getGoodsOrder() + "&sid=1" + "&admin_id="+ ContextParameter.getUserInfo().getAdminuserId() + "&fromPlatform=Android");
                            AppUtils.toActivity(context, ActiveCouponExtensionActivity.class, bundle3);
                            break;
                        //秒杀
                        case 6:
                            Bundle bundle4 = new Bundle();
                            bundle4.putSerializable("model", model);
                            AppUtils.toActivity(context, SecKillExtensionActivity.class, bundle4);
                            break;
                        //拼团
                        case 7:
                            Bundle bundle5 = new Bundle();
                            bundle5.putSerializable("model", model);
                            AppUtils.toActivity(context, PtExtensionActivity.class, bundle5);
                            break;
                        //换货
                        case 8:
                            Bundle bundle6 = new Bundle();
                            bundle6.putSerializable("model", model);
                            AppUtils.toActivity(context, ExchangeExtensionActivity.class, bundle6);
                            break;
                    }
                } else {
                    customDialog = DialogUtil.showCustomDialog(context, R.style.customDialogStyle, R.layout.dialog_notice_item, 260, 130, "审核中的商品无法编辑", R.id.dialog_notice_msg, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.btn_cal:
                                    customDialog.dismiss();
                                    break;
                                case R.id.btn_sure:
                                    customDialog.dismiss();
                                    break;
                            }
                        }
                    }, R.id.btn_sure);
                }
            }
        });
        return view;
    }

    class MyHolder {
        RelativeLayout goodItemView;
        RoundImgView goodIcon;
        TextView goodDesc, goodPrice, goodStock, goodSoldNum, price_type, good_type, good_type_price;
        LinearLayout deleteItemView, editItemView;
        RelativeLayout check_faild_question;
    }
}
