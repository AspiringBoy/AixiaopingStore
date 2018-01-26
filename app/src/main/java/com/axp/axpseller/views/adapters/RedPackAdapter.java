package com.axp.axpseller.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.activitys.sellerinfo.SellerEnvelopeActivity;
import com.axp.axpseller.activitys.sellerinfo.SellerRedPackActivity;
import com.axp.axpseller.activitys.sellerinfo.SendRedPackActivity;
import com.axp.axpseller.core.RecyclerViewModel;
import com.axp.axpseller.models.Assets;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.utils.AppUtils;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/12/8.
 * 资产管理适配器
 */
public class RedPackAdapter extends SuperRecyclerViewAdapter<RecyclerViewModel,RecyclerView.ViewHolder>{

    /**
     * 头部轮播图
     */
    public static final int TYPE_HEAD = 0;
    /**
     * 商品分类
     */
    public static final int TYPE_ASSETS_CLASSIFYS = 1;
    public RedPackAdapter(Context context, DataList<RecyclerViewModel> dataList) {
        super(context, dataList);
    }

    @Override
    public RecyclerView.ViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_HEAD:
                viewHolder = new TotleMoneyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_seller_redpack, parent, false));

                break;
            case TYPE_ASSETS_CLASSIFYS:

                viewHolder = new AssetsListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_seller_redpacketlist,parent,false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onSuperBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEAD:
                ((TotleMoneyViewHolder)holder).money.setText(mList.get(position).getData().toString());
                ((TotleMoneyViewHolder)holder).sendredpack.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {
                    AppUtils.toActivity(mContext,SendRedPackActivity.class);
                }});//发红包

                ((TotleMoneyViewHolder)holder).recharge.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {
                    AppUtils.toActivity(mContext,SellerEnvelopeActivity.class);}});//跳转到红包充值

                break;
            case TYPE_ASSETS_CLASSIFYS:
                Assets assets = (Assets) mList.get(position).getData();
                if(assets.getType() == 10){
                    ((AssetsListViewHolder)holder).name.setText("拼手气红包");
                }else if(assets.getType() == 50){
                    ((AssetsListViewHolder)holder).name.setText("普通红包");
                }

                ((AssetsListViewHolder)holder).date.setText(assets.getCreatetime());
                ((AssetsListViewHolder)holder).money.setText(assets.getTotalMoney()+"元");
                ((AssetsListViewHolder)holder).num.setText(assets.getReceiveQuantity()+"/"+assets.getTotalQuantity());
                break;
        }

    }
    @Override
    public int getItemViewType(int position) {
        int superType = super.getItemViewType(position);
        return superType == 0 ? mList.get(position).getItemType() : superType;
    }
    class TotleMoneyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_totle_money)
        TextView money;
        @BindView(R.id.tv_recharge)
        TextView recharge;
        @BindView(R.id.tv_send_redpack)
        TextView sendredpack;

       public TotleMoneyViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
       }

    }
    class AssetsListViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView name;
        @BindView(R.id.tv_date)
        TextView date;
        @BindView(R.id.tv_money)
        TextView money;
        @BindView(R.id.tv_num)
        TextView num;
        public AssetsListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
