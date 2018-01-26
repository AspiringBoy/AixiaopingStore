package com.axp.axpseller.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.activitys.sellerinfo.SellerRedPackActivity;
import com.axp.axpseller.activitys.sellerinfo.WithdrawActivity;
import com.axp.axpseller.activitys.user.MyScoreActivity;
import com.axp.axpseller.core.RecyclerViewModel;
import com.axp.axpseller.models.Assets;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.utils.AppUtils;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/12/8.
 * 资产管理适配器
 */
public class AssetManagementAdapter extends SuperRecyclerViewAdapter<RecyclerViewModel, RecyclerView.ViewHolder> {

    /**
     * 头部轮播图
     */
    public static final int TYPE_HEAD = 0;
    /**
     * 商品分类
     */
    public static final int TYPE_ASSETS_CLASSIFYS = 1;

    public AssetManagementAdapter(Context context, DataList<RecyclerViewModel> dataList) {
        super(context, dataList);
    }

    @Override
    public RecyclerView.ViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_HEAD:
                viewHolder = new TotleMoneyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_asset_managehead, parent, false));

                break;
            case TYPE_ASSETS_CLASSIFYS:

                viewHolder = new AssetsListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_asset_managelist, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onSuperBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEAD:
                List<String> data = (List<String>) mList.get(position).getData();
                ((TotleMoneyViewHolder) holder).money.setText(data.get(0));
                ((TotleMoneyViewHolder) holder).noAffirmMoney.setText("未确认：" + data.get(1));
                ((TotleMoneyViewHolder) holder).noAffirmMoney.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // AppUtils.toActivity(mContext,SellerUnConfirmedActivity.class);
                    }
                });//跳转未确认
                ((TotleMoneyViewHolder) holder).withdraw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppUtils.toActivity(mContext, WithdrawActivity.class);
                    }
                });//跳转到提现

                ((TotleMoneyViewHolder) holder).redpackget.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppUtils.toActivity(mContext, SellerRedPackActivity.class);
                    }
                });//跳转到红包
                ((TotleMoneyViewHolder) holder).adv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppUtils.toActivity(mContext, MyScoreActivity.class);
                    }
                });//积分
                break;
            case TYPE_ASSETS_CLASSIFYS:
                Assets assets = (Assets) mList.get(position).getData();
                ((AssetsListViewHolder) holder).name.setText(assets.getRemark());
                ((AssetsListViewHolder) holder).date.setText(assets.getCreatetime());
                if (assets.getMoney().charAt(0) == '-') {
                    ((AssetsListViewHolder) holder).money.setTextColor(mContext.getResources().getColor(R.color.red));
                } else {
                    ((AssetsListViewHolder) holder).money.setTextColor(mContext.getResources().getColor(R.color.mediumseagreen));
                }
                ((AssetsListViewHolder) holder).money.setText(assets.getMoney());
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
        @BindView(R.id.tv_noaffirm_money)
        TextView noAffirmMoney;
        @BindView(R.id.rl_withdraw)
        RelativeLayout withdraw;
        @BindView(R.id.rl_redpackget)
        RelativeLayout redpackget;
        @BindView(R.id.rl_adv)
        RelativeLayout adv;

        public TotleMoneyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    class AssetsListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView name;
        @BindView(R.id.tv_date)
        TextView date;
        @BindView(R.id.tv_money)
        TextView money;

        public AssetsListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
