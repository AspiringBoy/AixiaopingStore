package com.axp.axpseller.views.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.models.bean.RechargeMoneyModel;

import java.util.List;

/**
 * Created by YY on 2018/1/10.
 */
public class RechargeMoneyItemGvAdapter extends BaseAdapter {

    private Context context;
    private List<RechargeMoneyModel> list;
    private LayoutInflater inflater;
    private int selectPos = 0;

    public RechargeMoneyItemGvAdapter(Context context, List<RechargeMoneyModel> list) {
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
        MyViewHolder holder;
        if (view == null) {
            holder = new MyViewHolder();
            view = inflater.inflate(R.layout.recharge_score_card_item, viewGroup, false);
            holder.rechargeMoneyTv = (TextView) view.findViewById(R.id.recharge_100_money);
            holder.scoreTv = (TextView) view.findViewById(R.id.recharge_120_score);
            holder.scoreTv = (TextView) view.findViewById(R.id.recharge_120_score);
            holder.rechargeRll = (RelativeLayout) view.findViewById(R.id.recharge_100_rll);
            holder.tehuiIv = (ImageView) view.findViewById(R.id.tehui_iv);
            view.setTag(holder);
        } else holder = (MyViewHolder) view.getTag();

        holder.rechargeMoneyTv.setText(list.get(i).getRechargeMoney() + "元");
        holder.scoreTv.setText("获得" + list.get(i).getRechargeScore() + "积分");
        if (i == selectPos) {
            holder.rechargeRll.setBackgroundResource(R.drawable.recharge_online_orange_bg);
            holder.rechargeMoneyTv.setTextColor(Color.parseColor("#f96b47"));
            holder.scoreTv.setTextColor(Color.parseColor("#f96b47"));
            holder.tehuiIv.setImageResource(R.drawable.tehui);
        } else {
            holder.rechargeRll.setBackgroundResource(R.drawable.recharge_online_gray_bg);
            holder.rechargeMoneyTv.setTextColor(Color.parseColor("#969696"));
            holder.scoreTv.setTextColor(Color.parseColor("#969696"));
            holder.tehuiIv.setImageResource(R.drawable.tehui2);
        }
        return view;
    }

    public void changeSelect(int pos) {
        selectPos = pos;
        notifyDataSetChanged();
    }

    class MyViewHolder {
        private TextView rechargeMoneyTv, scoreTv;
        private RelativeLayout rechargeRll;
        private ImageView tehuiIv;
    }
}
