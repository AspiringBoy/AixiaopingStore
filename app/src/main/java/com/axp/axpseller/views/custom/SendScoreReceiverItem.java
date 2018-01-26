package com.axp.axpseller.views.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.models.bean.SendScoreDtListModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YY on 2018/1/11.
 */
public class SendScoreReceiverItem extends FrameLayout {
    @BindView(R.id.receiver_type)
    TextView receiverType;
    @BindView(R.id.select_iv)
    ImageView selectIv;
    @BindView(R.id.receiver_phone_tv)
    TextView receiverPhoneTv;
    @BindView(R.id.receiver_name_tv)
    TextView receiverNameTv;
    @BindView(R.id.score_duration_tv)
    TextView scoreDurationTv;
    private int pos;
    private ISelectChange selectChangeListenner;

    public void setSelectChangeListenner(ISelectChange selectChangeListenner) {
        this.selectChangeListenner = selectChangeListenner;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public SendScoreReceiverItem(Context context) {
        this(context, null);
    }

    public SendScoreReceiverItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SendScoreReceiverItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.send_score_receiver_item, this, true);
        ButterKnife.bind(this);
    }

    public void bindView(SendScoreDtListModel model) {
        String type = model.getPresenterType();
        if ("1".equals(type)) {
            receiverType.setText("用户账号");
        } else if ("2".equals(type)) {
            receiverType.setText("商家账号");
        }
        receiverNameTv.setText(model.getPresenterNickName());
        receiverPhoneTv.setText(model.getPresenterPhone());
        scoreDurationTv.setText(model.getScoreDuration());
    }

    public void changeSelect(boolean select) {
        if (select) {
            selectIv.setImageResource(R.drawable.xuanzhong);
        } else selectIv.setImageResource(R.drawable.weixuanzhong);
    }

    @OnClick({R.id.receiver_item})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.receiver_item:
                if (selectChangeListenner != null) {
                    selectChangeListenner.selectChanged(pos);
                }
                break;
        }
    }

    public interface ISelectChange {
        void selectChanged(int pos);
    }
}
