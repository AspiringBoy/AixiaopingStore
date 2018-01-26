package com.axp.axpseller.views.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.axp.axpseller.R;
import com.axp.axpseller.core.RecyclerViewModel;
import com.axp.axpseller.models.Goods;
import com.axp.axpseller.models.SpecialLocalProduct;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.views.Banner;
import com.axp.axpseller.views.adapters.viewholder.BaseViewHolder;
import com.axp.axpseller.views.widget.SquareImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/7/19.
 * 特产速递适配器
 */
public class SpecialLocalProductAdapter extends SuperRecyclerViewAdapter<RecyclerViewModel, BaseViewHolder> {

    public static final int VIEW_TYPE_BANNER = 1;
    public static final int VIEW_TYPE_FILTER_TYPE = 4;
    public static final int VIEW_TYPE_GOODS_ITEM = 5;

    public SpecialLocalProductAdapter(Context context, DataList<RecyclerViewModel> dataList) {
        super(context, dataList);
    }

    @Override
    public BaseViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_BANNER:
                viewHolder = new SpecialLocalProductBannerViewHolder(mContext, parent);
                break;
            case VIEW_TYPE_FILTER_TYPE:
                viewHolder = new SpecialLocalProductFilterType(mContext, parent);
                break;
            case VIEW_TYPE_GOODS_ITEM:
                viewHolder = new SpecialLocalProductGoodsItem(mContext, parent);
                break;
        }
        return viewHolder;
    }

    /**
     * 判断是否是商品列表项
     */
    public boolean isGoods(int position) {
        return getItemViewType(position) == VIEW_TYPE_GOODS_ITEM;
    }

    @Override
    public int getItemViewType(int position) {
        int superType = super.getItemViewType(position);
        return superType == 0 ? mList.get(position).getItemType() : superType;
    }


    @Override
    public void onSuperBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(mList.get(position));
    }

    /**
     * banner
     */
    class SpecialLocalProductBannerViewHolder extends BaseViewHolder<RecyclerViewModel> {

        @BindView(R.id.banner)
        Banner banner;

        public SpecialLocalProductBannerViewHolder(Context context, ViewGroup parent) {
            super(context, parent, R.layout.preferential99_banner);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(RecyclerViewModel recyclerViewModel) {
            banner.setImageTexts(((SpecialLocalProduct) recyclerViewModel.getData()).getHeadBanners());
        }
    }

    /**
     * 商品类型过滤
     */
    class SpecialLocalProductFilterType extends BaseViewHolder<RecyclerViewModel> {

        public SpecialLocalProductFilterType(Context context, ViewGroup parent) {
            super(context, parent, R.layout.preferential99_filter_type);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(RecyclerViewModel recyclerViewModel) {

        }

        @OnClick({R.id.tv_all_classifi, R.id.tv_sales_volume, R.id.tv_value})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_all_classifi:
                    break;
                case R.id.tv_sales_volume:
                    break;
                case R.id.tv_value:
                    break;
            }
        }
    }

    /**
     * 商品过滤
     */
    class SpecialLocalProductGoodsItem extends BaseViewHolder<RecyclerViewModel> {

        @BindView(R.id.iv_goods_image)
        SquareImageView ivGoodsImage;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.tv_express_tactics)
        TextView tvExpressTactics;

        Goods mGoods;

        public SpecialLocalProductGoodsItem(Context context, ViewGroup parent) {
            super(context, parent, R.layout.preferential99_goods_item);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(RecyclerViewModel recyclerViewModel) {
            Goods goods = (Goods) recyclerViewModel.getData();
            mGoods = goods;

            Glide.with(getContext()).load(goods.getCoverPic()).into(ivGoodsImage);
            tvGoodsName.setText(goods.getName());
            tvGoodsPrice.setText(goods.getValue());
            tvExpressTactics.setText(goods.getExpressTactics());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppUtils.toGoods(getContext(), goods.getGoodsId());
                }
            });

        }


    }

}
