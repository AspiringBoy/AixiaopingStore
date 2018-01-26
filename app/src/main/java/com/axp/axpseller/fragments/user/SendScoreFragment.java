package com.axp.axpseller.fragments.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.axp.axpseller.R;
import com.axp.axpseller.activitys.user.SendScoreDtActivity;
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
import com.axp.axpseller.views.custom.CustomToolbar;
import com.axp.axpseller.views.dialogs.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YY on 2018/1/11.
 */
public class SendScoreFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.receiver_name_edt)
    EditText receiverNameEdt;
    private View fgView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgView = inflater.inflate(R.layout.fragment_send_score, container, false);
        ButterKnife.bind(this, fgView);
        toolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                getActivity().finish();
            }

            @Override
            public void onRightImgClick() {

            }
        });
        return fgView;
    }

    @OnClick(R.id.nxt_btn)
    public void onClick() {
        if (StringUtils.isBlank(receiverNameEdt.getText().toString())) {
            DialogUtil.showNoticDialog(getActivity(), "请输入收取者账号名称!");
            return;
        }
        checkName();
    }

    private void checkName() {
        Request<SendScoreDtListModel> request = new Request();
        SendScoreDtListModel model = new SendScoreDtListModel();
        model.setPresenterName(receiverNameEdt.getText().toString());
        request.setData(model);
        RXUtils.request(getActivity(), request, "checkPresenters", new SupportSubscriber<Response<SendScoreDtResModel>>() {
            private LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog(getActivity());
                }
                loadingDialog.show();
            }

            @Override
            public void onNext(Response<SendScoreDtResModel> response) {
                loadingDialog.dismiss();
                if (response.getStatus() == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("receiverName", response.getData());
                    AppUtils.toActivity(getActivity(), SendScoreDtActivity.class, bundle);
                } else {
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
                DialogUtil.showNoticDialog(getActivity(), "身份验证失败,请稍后再试!");
            }
        });
    }
}
