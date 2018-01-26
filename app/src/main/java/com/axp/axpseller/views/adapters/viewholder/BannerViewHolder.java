package com.axp.axpseller.views.adapters.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.axp.axpseller.R;
import com.axp.axpseller.views.Banner;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/6/14.
 *
 * 通用的BannerViewHolder
 */
public class BannerViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.banner)
    public Banner banner;

    public BannerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
