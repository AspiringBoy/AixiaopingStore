package com.malinskiy.superrecyclerview.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.R;

import butterknife.OnClick;

/**
 * Created by xu on 2016/7/2.
 * 数据为空时显示
 */
public class EmptyViewHolder extends DifferentSituationViewHolder {

    public View view;
    public ImageView ivImage;
    public TextView tvText;
    private View.OnClickListener onClickListener;

    public EmptyViewHolder(Context context, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_empty, parent, false);

        ivImage = (ImageView) view.findViewById(R.id.iv_empty_image);
        tvText = (TextView) view.findViewById(R.id.tv_empty_text);

    }


    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;

        view.findViewById(R.id.btn_empty_reload).setOnClickListener(getOnClickListener());
    }


    @Override
    public View getView() {
        return view;
    }
}
