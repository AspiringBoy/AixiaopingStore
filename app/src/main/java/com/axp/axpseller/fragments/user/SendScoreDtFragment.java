package com.axp.axpseller.fragments.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.activitys.user.ScoreRechargeSuccessActivity;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.bean.SendScoreDtListModel;
import com.axp.axpseller.models.bean.SendScoreDtResModel;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.DialogUtil;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.views.custom.CustomDialog;
import com.axp.axpseller.views.custom.CustomToolbar;
import com.axp.axpseller.views.custom.SendScoreReceiverItem;
import com.axp.axpseller.views.dialogs.LoadingDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YY on 2018/1/11.
 */
public class SendScoreDtFragment extends BaseFragment implements SendScoreReceiverItem.ISelectChange {
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.receiver_container)
    LinearLayout receiverContainer;
    @BindView(R.id.score_left_tv)
    TextView scoreLeftTv;
    @BindView(R.id.score_num_edt)
    EditText scoreNumEdt;
    private View fgView;
    private String receiverName;
    private int curSelectPos = 0;
    private List<SendScoreDtListModel> usersInfos;
    private SendScoreDtResModel mModel;
    private CustomDialog sendNoticeDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgView = inflater.inflate(R.layout.fragment_send_score_dt, container, false);
        ButterKnife.bind(this, fgView);
        initData();
        return fgView;
    }

    private void initData() {
        toolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                getActivity().finish();
            }

            @Override
            public void onRightImgClick() {

            }
        });
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            mModel = ((SendScoreDtResModel) bundle.getSerializable("receiverName"));
        }
//        askNet();
        bindData(mModel);
    }

    private void askNet() {
        Request<SendScoreDtResModel> request = new Request<>();
        SendScoreDtResModel model = new SendScoreDtResModel();
        model.setPresenterName(receiverName);
        request.setData(model);
        RXUtils.request(getActivity(), request, "getPresenterInfo", new SupportSubscriber<Response<SendScoreDtResModel>>() {
            @Override
            public void onNext(Response<SendScoreDtResModel> sendScoreDtResModelResponse) {
                bindData(sendScoreDtResModelResponse.getData());
            }
        });
    }

    private void bindData(SendScoreDtResModel data) {
        scoreLeftTv.setText(data.getScoreBanlance()+"");
        usersInfos = data.getUsersInfos();
        if (usersInfos != null && usersInfos.size() > 0) {
            for (int i = 0; i < usersInfos.size(); i++) {
                SendScoreReceiverItem item = new SendScoreReceiverItem(getActivity());
                item.setPos(i);
                item.bindView(usersInfos.get(i));
                if (i == 0) {
                    item.changeSelect(true);
                } else {
                    item.changeSelect(false);
                }
                item.setSelectChangeListenner(this);
                receiverContainer.addView(item);
            }
        }
    }

    @OnClick(R.id.btn_send)
    public void onClick() {
        if (StringUtils.isBlank(scoreNumEdt.getText().toString())) {
            DialogUtil.showNoticDialog(getActivity(),"请填写赠送数量!");
            return;
        }
        int sendNum = Integer.parseInt(scoreNumEdt.getText().toString());
        int scoreLeft = Integer.parseInt(scoreLeftTv.getText().toString());
        if (sendNum > scoreLeft) {
            DialogUtil.showNoticDialog(getActivity(), "积分余额不足!");
            return;
        }
        sendNoticeDialog = DialogUtil.showCustomDialog(getActivity(), R.style.customDialogStyle, R.layout.send_score_dialog, 245, 212, scoreNumEdt.getText().toString() + "积分", R.id.score_num, "赠送给:" + usersInfos.get(curSelectPos).getPresenterNickName(), R.id.send_desc, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.cal_btn:
                        sendNoticeDialog.dismiss();
                        break;
                    case R.id.sure_btn:
                        sendNoticeDialog.dismiss();
                        sendScore();
                        break;
                }
            }
        },R.id.cal_btn,R.id.sure_btn);
    }

    private void sendScore() {
        Request<SendScoreDtListModel> request = new Request<>();
        SendScoreDtListModel model = new SendScoreDtListModel();
        model.setPresenterId(usersInfos.get(curSelectPos).getPresenterId());
        model.setScoreNum(scoreNumEdt.getText().toString());
        model.setPresenterType(usersInfos.get(curSelectPos).getPresenterType());
        request.setData(model);
        RXUtils.request(getActivity(), request, "sendScore", new SupportSubscriber<Response>() {

            private LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog(getActivity());
                }
                loadingDialog.show();
            }

            @Override
            public void onNext(Response response) {
                loadingDialog.dismiss();
                if (response.getStatus() == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putString("score",scoreNumEdt.getText().toString());
                    bundle.putString("title","赠送积分");
                    AppUtils.toActivity(getActivity(), ScoreRechargeSuccessActivity.class,bundle);
                }else {
                    DialogUtil.showNoticDialog(getActivity(), response.getMessage());
                }
            }

            @Override
            public void onResponseError(Response response) {
                loadingDialog.dismiss();
                DialogUtil.showNoticDialog(getActivity(), response.getMessage());
            }

            @Override
            public void onError(Throwable e) {
                loadingDialog.dismiss();
                DialogUtil.showNoticDialog(getActivity(), "赠送积分失败,请稍后再试!");
            }
        });
    }

    @Override
    public void selectChanged(int pos) {
        int childCount = receiverContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            SendScoreReceiverItem item = (SendScoreReceiverItem) receiverContainer.getChildAt(i);
            if (i == pos) {
                curSelectPos = pos;
                item.changeSelect(true);
            } else {
                item.changeSelect(false);
            }
        }
    }
}
