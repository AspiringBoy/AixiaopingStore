package com.axp.axpseller.fragments.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.managers.pay.PayMessage;
import com.axp.axpseller.model.widget.ScoreRechargeOnlineView;
import com.axp.axpseller.models.bean.RechargeScoreOnlineModel;
import com.axp.axpseller.presenter.RechargeOnlineFgPresenter;
import com.axp.axpseller.utils.DialogUtil;
import com.axp.axpseller.view.IShowScoreRechargeFg;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by YY on 2018/1/9.
 */
public class ScoreRechargeOnlineFragment extends BaseFragment implements IShowScoreRechargeFg {
    private View fgView;
    private RechargeOnlineFgPresenter presenter;
    private ScoreRechargeOnlineView scoreRechargeOnlineView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgView = inflater.inflate(R.layout.fragment_score_recharge_online, container, false);
        EventBus.getDefault().register(this);
        presenter = new RechargeOnlineFgPresenter(this);
        presenter.showFgView(getActivity());
        return fgView;
    }

    @Override
    public void oprateView(RechargeScoreOnlineModel rechargeScoreOnlineModel) {
        scoreRechargeOnlineView.bindView(rechargeScoreOnlineModel);
    }

    @Override
    public void initView() {
        scoreRechargeOnlineView = new ScoreRechargeOnlineView(getActivity(), fgView);
    }

    @Override
    public void initData() {
        scoreRechargeOnlineView.initData();
    }

    @Subscribe
    public void onEvent(PayMessage message){
        if (message != null) {
            if (message.getResult() == 0) {//微信支付成功
                scoreRechargeOnlineView.goToIntent();
            } else if (message.getResult() == -1) {//微信支付失败
                DialogUtil.showNoticDialog(getActivity(),"微信支付失败!");
            } else if (message.getResult() == -2) {//微信支付取消
                DialogUtil.showNoticDialog(getActivity(),"微信支付已取消!");
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        scoreRechargeOnlineView.unRegisterPayecoPayBroadcastReceiver();
        EventBus.getDefault().unregister(this);
    }
}
