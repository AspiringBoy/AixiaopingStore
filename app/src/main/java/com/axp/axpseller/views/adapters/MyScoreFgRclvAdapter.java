package com.axp.axpseller.views.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.activitys.user.ScanToSendActivity;
import com.axp.axpseller.activitys.user.ScoreRechargeOffLineActivity;
import com.axp.axpseller.activitys.user.ScoreRechargeOnlineActivity;
import com.axp.axpseller.activitys.user.SendScoreActivity;
import com.axp.axpseller.core.RecyclerViewModel;
import com.axp.axpseller.models.bean.MyScoreListModel;
import com.axp.axpseller.models.bean.RechargeScoreIsPartnerModel;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.ScreenConfigUtil;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;

/**
 * Created by YY on 2018/1/10.
 */
public class MyScoreFgRclvAdapter extends SuperRecyclerViewAdapter<RecyclerViewModel, RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private int showType;
    private int headerCount = 1;
    private boolean isCareerPartner;
    private String desc;

    public MyScoreFgRclvAdapter(Context context, DataList<RecyclerViewModel> dataList) {
        super(context, dataList);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            //headerView
            case 0:
                holder = new HeaderViewHolder(inflater.inflate(R.layout.my_score_rclv_header, parent, false));
                break;
            //积分列表
            case 1:
                holder = new ScoreListViewHolder(inflater.inflate(R.layout.my_score_list_item, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onSuperBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            //headerview
            case 0:
                RechargeScoreIsPartnerModel partnerModel = (RechargeScoreIsPartnerModel) mList.get(position).getData();
                ((HeaderViewHolder) holder).totalScoreTv.setText(partnerModel.getTotalScore());
                isCareerPartner = partnerModel.isCareerPartner();
                desc = partnerModel.getDesc();
                break;
            //积分列表
            case 1:
                ScoreListViewHolder viewHolder = (ScoreListViewHolder) holder;
                viewHolder.scoreTv.setText(((MyScoreListModel) mList.get(position).getData()).getDetailScore() + "");
                viewHolder.dateTv.setText(((MyScoreListModel) mList.get(position).getData()).getDetailTime());
                viewHolder.remarkTv.setText(((MyScoreListModel) mList.get(position).getData()).getDatailRemark());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int superType = super.getItemViewType(position);
        return superType == 0 ? mList.get(position).getItemType() : superType;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final View mView;
        private TextView totalScoreTv;
        private Button rechargeBtn, sendBtn;
        private View popView;
        private PopupWindow pop;
        private TextView rechargeOnline, rechargeCard, canTv, titleType;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            totalScoreTv = (TextView) itemView.findViewById(R.id.tv_total_score);
            rechargeBtn = (Button) itemView.findViewById(R.id.recharge_btn);
            sendBtn = (Button) itemView.findViewById(R.id.send_btn);
            rechargeBtn.setOnClickListener(this);
            sendBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                //充值
                case R.id.recharge_btn:
                    showType = 1;
                    showPop();
                    break;
                //赠送
                case R.id.send_btn:
                    showType = 2;
                    showPop();
                    break;
                case R.id.recharge_online:
                    if (showType == 1) {//在线充值
                        pop.dismiss();
                        AppUtils.toActivity(mContext, ScoreRechargeOnlineActivity.class);
                    } else if (showType == 2) {//账号赠送
                        pop.dismiss();
                        AppUtils.toActivity(mContext, SendScoreActivity.class);
                    }
                    break;
                case R.id.recharge_card:
                    if (showType == 1) {//实卡充值
                        pop.dismiss();
                        AppUtils.toActivity(mContext, ScoreRechargeOffLineActivity.class);
                    } else if (showType == 2) {//面对面扫码赠送
                        pop.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putString("score", ((RechargeScoreIsPartnerModel) mList.get(0).getData()).getTotalScore());
                        AppUtils.toActivity(mContext, ScanToSendActivity.class, bundle);
                    }
                    break;
                //取消
                case R.id.cal_tv:
                    pop.dismiss();
                    break;
            }
        }

        private void showPop() {
            if (pop == null) {
                popView = inflater.inflate(R.layout.recharge_pop_item, null);
                rechargeOnline = ((TextView) popView.findViewById(R.id.recharge_online));
                rechargeCard = ((TextView) popView.findViewById(R.id.recharge_card));
                titleType = ((TextView) popView.findViewById(R.id.title_type));
                canTv = ((TextView) popView.findViewById(R.id.cal_tv));
                rechargeOnline.setOnClickListener(this);
                rechargeCard.setOnClickListener(this);
                canTv.setOnClickListener(this);
                pop = new PopupWindow(popView, WindowManager.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                pop.setBackgroundDrawable(new ColorDrawable());
                pop.setOutsideTouchable(false);
                pop.setFocusable(true);
                pop.setAnimationStyle(R.style.PwAnimBottom);
                pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        ScreenConfigUtil.setBackgroundAlpha(mContext, 1);
                    }
                });
            }
            if (showType == 1) {//充值
                titleType.setText("请选择充值方式");
                rechargeOnline.setText("在线充值");
                rechargeCard.setText("实卡充值");
                if (isCareerPartner) {
                    rechargeOnline.setVisibility(View.VISIBLE);
                }else {
                    rechargeOnline.setVisibility(View.GONE);
                }
            } else if (showType == 2) {//赠送
                titleType.setText("请选择赠送方式");
                rechargeOnline.setText("账号赠送");
                rechargeCard.setText("面对面扫码赠送");
                rechargeOnline.setVisibility(View.VISIBLE);
            }
            ScreenConfigUtil.setBackgroundAlpha(mContext, 0.5f);
            pop.showAtLocation(mView, Gravity.BOTTOM, 0, 0);
        }

    }

    class ScoreListViewHolder extends RecyclerView.ViewHolder {

        private TextView remarkTv, dateTv, scoreTv;

        public ScoreListViewHolder(View itemView) {
            super(itemView);
            remarkTv = (TextView) itemView.findViewById(R.id.tv_name);
            dateTv = (TextView) itemView.findViewById(R.id.tv_date);
            scoreTv = (TextView) itemView.findViewById(R.id.tv_score);
        }
    }
}
