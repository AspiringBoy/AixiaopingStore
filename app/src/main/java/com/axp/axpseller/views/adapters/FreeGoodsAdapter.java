package com.axp.axpseller.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.axp.axpseller.R;
import com.axp.axpseller.activitys.mall.BecomeVipActivity;
import com.axp.axpseller.core.RecyclerViewModel;
import com.axp.axpseller.models.FreeMall;
import com.axp.axpseller.models.Goods;
import com.axp.axpseller.models.ImageText;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.Utils;
import com.axp.axpseller.views.Banner;
import com.axp.axpseller.views.widget.SquareImageView;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/13.
 * 免单券商品列表适配器
 */
public class FreeGoodsAdapter extends SuperRecyclerViewAdapter<RecyclerViewModel, RecyclerView.ViewHolder> {

    /**
     * 头部轮播图
     */
    public static final int TYPE_BECOM_VIP_BANNERS = 1;
    /**
     * 商品
     */
    public static final int TYPE_GOODS_ITEM = 2;

    public FreeGoodsAdapter(Context context, DataList<RecyclerViewModel> dataList) {
        super(context, dataList);
    }

    @Override
    public RecyclerView.ViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case TYPE_BECOM_VIP_BANNERS:
                viewHolder = new FreeGoodsBecomeVipBannerViewHolder(mContext, parent);
                break;
            case TYPE_GOODS_ITEM:
                viewHolder = new FreeGoodsItem(mContext, parent);
                break;
        }

        return viewHolder;
    }

    /**
     * 判断是否是商品列表项
     */
    public boolean isGoods(int position) {
        return getItemViewType(position) == TYPE_GOODS_ITEM;
    }

    @Override
    public void onSuperBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_BECOM_VIP_BANNERS:
                ((FreeGoodsBecomeVipBannerViewHolder)holder).bindView((FreeMall) mList.get(position).getData());
                break;
            case TYPE_GOODS_ITEM:
                ((FreeGoodsItem)holder).bindView((Goods) mList.get(position).getData());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int superType = super.getItemViewType(position);
        return superType == 0 ? mList.get(position).getItemType() : superType;
    }
}


class FreeGoodsBecomeVipBannerViewHolder extends RecyclerView.ViewHolder {

    Context mContxt;
    @BindView(R.id.banner)
    Banner banner;

    public FreeGoodsBecomeVipBannerViewHolder(Context context, ViewGroup parent) {
        super(LayoutInflater.from(context).inflate(R.layout.free_goods_banner, parent, false));
        mContxt = context;
        ButterKnife.bind(this, itemView);
    }

    public void bindView(FreeMall freeMall) {
        banner.setImageTexts(freeMall.getBecomeVipBanners());
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void onClick(ImageText data, int position) {
                AppUtils.toActivity(mContxt, BecomeVipActivity.class);
            }
        });
    }
}

class FreeGoodsItem extends RecyclerView.ViewHolder {

    Goods mGoods;
    Context mContxt;
    @BindView(R.id.iv_goods_image)
    SquareImageView ivGoodsImage;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_goods_costPrice)
    TextView tvGoodsCostPrice;

    public FreeGoodsItem(Context context, ViewGroup parent) {
        super(LayoutInflater.from(context).inflate(R.layout.free_goods_view_item_goods, parent, false));
        mContxt = context;
        ButterKnife.bind(this, itemView);
    }

    public void bindView(Goods goods) {
        mGoods = goods;

        Glide.with(mContxt).load(mGoods.getCoverPic()).into(ivGoodsImage);
        tvGoodsName.setText(mGoods.getName());
        tvGoodsCostPrice.setText("价值：￥"+mGoods.getCostPrice());
        Utils.strikethrough(tvGoodsCostPrice);
        itemView.setOnClickListener(view -> AppUtils.toGoods(mContxt, mGoods.getGoodsId()));

    }
}