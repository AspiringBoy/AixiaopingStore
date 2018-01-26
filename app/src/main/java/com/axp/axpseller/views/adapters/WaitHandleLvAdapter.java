package com.axp.axpseller.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.axp.axpseller.Constants;
import com.axp.axpseller.R;
import com.axp.axpseller.models.bean.GoodsManageModel;
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
public class WaitHandleLvAdapter extends BaseAdapter {

    private Context context;
    private List<GoodsManageModel> list;
    private LayoutInflater inflater;

    public void setUpdateListenner(IBaseListenner updateListenner) {
        this.updateListenner = updateListenner;
    }

    private IBaseListenner updateListenner;
    private CustomDialog customDialog;
    private int pos;

    public WaitHandleLvAdapter(Context context, List<GoodsManageModel> list) {
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
            view = inflater.inflate(R.layout.wait_handle_fg_lv_item, viewGroup, false);
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
            holder.editItemView = (LinearLayout) view.findViewById(R.id.edit_item_ll);
            holder.putawayItemView = (LinearLayout) view.findViewById(R.id.putaway_item_ll);
            view.setTag(holder);
        } else holder = (MyHolder) view.getTag();

        pos = i;
        GoodsManageModel model = list.get(i);
        Glide.with(context).load(model.getGoodsIcon()).into(holder.goodIcon);
        holder.goodDesc.setText(model.getGoodsName());
        holder.goodStock.setText("库存:  " + model.getStock() + "件");
        int statusId = model.getStatusId();
        if (statusId == 1000) {
            holder.goodSoldNum.setText("状态:出售中");
        } else if (statusId == 1001) {
            holder.goodSoldNum.setText("状态:审核中");
        } else if (statusId == 1002) {
            holder.goodSoldNum.setText("状态:审核失败");
        } else if (statusId == 1003) {
            holder.goodSoldNum.setText("状态:待处理");
        }
        int typeId = model.getTypeId();
        if (typeId == 1 || typeId == 2) {//全国特产、周边店铺
            holder.goodPrice.setText("￥" + model.getPrice() + "");
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
            holder.goodPrice.setText("￥" + model.getDeductPrice() + "");
            holder.price_type.setVisibility(View.VISIBLE);
            holder.good_type.setVisibility(View.VISIBLE);
            holder.good_type_price.setVisibility(View.GONE);
            holder.price_type.setText("券后价:");
            holder.good_type.setText("普通优惠券");
        } else if (typeId == 5) {//活动优惠券
            holder.goodPrice.setText("￥" + model.getDeductPrice() + "");
            holder.price_type.setVisibility(View.VISIBLE);
            holder.good_type.setVisibility(View.VISIBLE);
            holder.good_type_price.setVisibility(View.GONE);
            holder.price_type.setText("券后价:");
            holder.good_type.setText("活动优惠券");
        } else if (typeId == 6) {//秒杀
            holder.goodPrice.setText("￥" + model.getRushPrice() + "");
            holder.price_type.setVisibility(View.VISIBLE);
            holder.good_type.setVisibility(View.VISIBLE);
            holder.good_type_price.setVisibility(View.GONE);
            holder.price_type.setText("秒杀价:");
            holder.good_type.setText(model.getRushTimeSlot());
        } else if (typeId == 7) {//拼团
            holder.goodPrice.setText("￥" + model.getPutTogetherPrice() + "");
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
        holder.goodItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataUtil.toEditActivity(context, model.getBaseGoodsId(), model.getGoodsOrder());
            }
        });

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
                                DataUtil.deleteGood(context, Constants.GOOD_MANAGE_WAIT_HANDLE_STATUS, model.getTypeId() + "",model.getBaseGoodsId(), model.getGoodsOrder(), updateListenner);
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
                DataUtil.toEditActivity(context, model.getBaseGoodsId(), model.getGoodsOrder());
            }
        });
        //上架
        holder.putawayItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog = DialogUtil.showCustomDialog(context, R.style.customDialogStyle, R.layout.remove_good_dialog_item, 270, 130, "是否要上架该商品?", R.id.dialog_msg_tv, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.btn_cal:
                                customDialog.dismiss();
                                break;
                            case R.id.btn_sure:
                                customDialog.dismiss();
                                DataUtil.putOnGood(context, Constants.GOOD_MANAGE_WAIT_HANDLE_STATUS, model.getBaseGoodsId(), model.getGoodsOrder(), updateListenner);
                                break;
                        }
                    }
                }, R.id.btn_cal, R.id.btn_sure);
            }
        });
        return view;
    }

    class MyHolder {
        RelativeLayout goodItemView;
        RoundImgView goodIcon;
        TextView goodDesc, goodPrice, goodStock, goodSoldNum, price_type, good_type, good_type_price;
        LinearLayout deleteItemView, editItemView, putawayItemView;
    }
}
