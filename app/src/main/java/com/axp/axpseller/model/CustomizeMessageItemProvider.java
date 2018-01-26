package com.axp.axpseller.model;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.axp.axpseller.activitys.TaoBaoActivity;
import com.bumptech.glide.Glide;

import net.aixiaoping.library.R;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;


/**
 * Created by YY on 2017/11/9.
 */
@ProviderTag(messageContent = CustomizeMessage.class)
public class CustomizeMessageItemProvider extends IContainerItemProvider.MessageProvider<CustomizeMessage> {

    private Context context;

    @Override
    public void bindView(View view, int i, CustomizeMessage customizeMessage, UIMessage uiMessage) {
        MyViewHolder holder = (MyViewHolder) view.getTag();
        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {
            holder.msg_bg.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_right);
        }else {
            holder.msg_bg.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_left);
        }
        holder.good_desc.setText(customizeMessage.getContent());
        Glide.with(context).load(customizeMessage.getGoodPic()).into(holder.good_pic);
//        AndroidEmoji.ensure((Spannable) holder.good_desc.getText());//显示消息中的 Emoji 表情.

        /*holder.msg_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TaoBaoActivity.class);
                intent.putExtra("URL",customizeMessage.getLinkUrl());
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public Spannable getContentSummary(CustomizeMessage customizeMessage) {
        return new SpannableString(customizeMessage.getContent());
    }

    @Override
    public void onItemClick(View view, int i, CustomizeMessage customizeMessage, UIMessage uiMessage) {
        Intent intent = new Intent(context, TaoBaoActivity.class);
        intent.putExtra("URL",customizeMessage.getLinkUrl());
        context.startActivity(intent);
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        this.context = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.rong_msg_provider, viewGroup, false);
        MyViewHolder holder = new MyViewHolder();
        holder.good_desc = (TextView) inflate.findViewById(R.id.good_name);
        holder.good_pic = (ImageView) inflate.findViewById(R.id.goods_iv);
        holder.msg_bg = (RelativeLayout) inflate.findViewById(R.id.msg_bg);
        inflate.setTag(holder);
        return inflate;
    }

    class MyViewHolder{
        TextView good_desc;
        ImageView good_pic;
        RelativeLayout msg_bg;
    }
}
