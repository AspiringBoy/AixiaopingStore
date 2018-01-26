package com.axp.axpseller.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.AssetsMsgDtModel;
import com.axp.axpseller.models.AssetsMsgDtObModel;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by YY on 2017/5/9.
 */
public class AssetsMsgDtFragment extends BaseFragment {
    private View view;
    private ImageView recharge_result_iv;
    private TextView recharge_result_tv;
    private TextView recharge_money;
    private TextView recharge_content;
    private TextView recharge_msg_dt_date;
    private Toolbar toolbar;
    private int msgId;
    private int typeId;
    private TextView tips_tv;
    private TextView title;
    private LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recharge_msg_dt, container, false);
        initView();
        initData();
        return view;
    }

    private void initData() {
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.show();
        getBundleData();
        getAssetsMsgDtData(msgId + "", typeId + "");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    private void getAssetsMsgDtData(String msgId, String typeId) {
        Request<AssetsMsgDtModel> request = new Request<>();
        AssetsMsgDtModel model = new AssetsMsgDtModel();
        model.setUserId(ContextParameter.getUserInfo().getUserId());
        model.setTypeId(typeId);
        model.setMsgId(msgId);
        request.setData(model);
        RXUtils.request(getActivity(), request, "getMsgDetailAssets", new SupportSubscriber<Response<AssetsMsgDtModel>>() {
            @Override
            public void onNext(Response<AssetsMsgDtModel> assetsMsgDtModelResponse) {
                AssetsMsgDtObModel assetsMsgDtObModel = assetsMsgDtModelResponse.getData().getMsgDetail();
                Glide.with(getActivity()).load(assetsMsgDtObModel.getIcon()).into(recharge_result_iv);
                String nav_title = assetsMsgDtObModel.getNav_title();
                title.setText(nav_title);
                recharge_result_tv.setText(assetsMsgDtObModel.getTitle());
                recharge_money.setText(assetsMsgDtObModel.getMoney());
                recharge_msg_dt_date.setText(assetsMsgDtObModel.getTime());
                String tips = assetsMsgDtObModel.getTips();
                if (tips != null && tips.length() > 0) {
                    tips_tv.setVisibility(View.VISIBLE);
                    tips_tv.setText(tips);
                } else {
                    tips_tv.setVisibility(View.GONE);
                }
                List<String> list = assetsMsgDtObModel.getContent();
                StringBuilder stringBuilder = new StringBuilder();
                for (String s : list) {
                    stringBuilder.append(s);
                }
                recharge_content.setText(stringBuilder.toString());
                loadingDialog.dismiss();
            }
        });
    }

    private void initView() {
        toolbar = ((Toolbar) view.findViewById(R.id.recharge_msg_dt_toolbar));
        recharge_result_iv = ((ImageView) view.findViewById(R.id.recharge_result_iv));
        recharge_result_tv = ((TextView) view.findViewById(R.id.recharge_result_tv));
        recharge_money = ((TextView) view.findViewById(R.id.recharge_money));
        recharge_content = ((TextView) view.findViewById(R.id.recharge_content));
        recharge_msg_dt_date = ((TextView) view.findViewById(R.id.recharge_msg_dt_date));
        tips_tv = ((TextView) view.findViewById(R.id.assets_msg_dt_tips));
        title = ((TextView) view.findViewById(R.id.assets_msg_dt_title));
    }

    private void getBundleData() {
        msgId = getActivity().getIntent().getIntExtra("msgId", 1);
        typeId = getActivity().getIntent().getIntExtra("typeId", 1);
    }
}
