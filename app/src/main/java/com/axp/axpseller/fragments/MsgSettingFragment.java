package com.axp.axpseller.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.utils.T;
import com.suke.widget.SwitchButton;

/**
 * Created by YY on 2017/5/10.
 */
public class MsgSettingFragment extends BaseFragment {
    private View view;
    private SwitchButton switch_btn_new_msg_notice;
    private SwitchButton switch_btn_msg_voice;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_msg_setting, container, false);
        initView();
        initData();
        return view;
    }

    private void initData() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        switch_btn_new_msg_notice.setChecked(ContextParameter.getLocalConfig().isOpenPush());
        switch_btn_msg_voice.setChecked(ContextParameter.getLocalConfig().isOpenVoice());
        switch_btn_new_msg_notice.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    ContextParameter.getLocalConfig().setOpenPush(true);
                    ContextParameter.setLocalConfig(ContextParameter.getLocalConfig());
                    Toast.makeText(MsgSettingFragment.this.getActivity(), "开启新消息提示", Toast.LENGTH_SHORT).show();
                } else {
                    ContextParameter.getLocalConfig().setOpenPush(false);
                    ContextParameter.setLocalConfig(ContextParameter.getLocalConfig());
                    Toast.makeText(MsgSettingFragment.this.getActivity(), "关闭新消息提示", Toast.LENGTH_SHORT).show();
                }
            }
        });
        switch_btn_msg_voice.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    T.showShort(getActivity(), "开启声音");
                    ContextParameter.getLocalConfig().setOpenVoice(true);
                    ContextParameter.setLocalConfig(ContextParameter.getLocalConfig());
                } else {
                    T.showShort(getActivity(), "关闭声音");
                    ContextParameter.getLocalConfig().setOpenVoice(false);
                    ContextParameter.setLocalConfig(ContextParameter.getLocalConfig());
                }
            }
        });
    }

    private void initView() {
        switch_btn_new_msg_notice = ((SwitchButton) view.findViewById(R.id.switch_btn_new_msg_notice));
        switch_btn_msg_voice = ((SwitchButton) view.findViewById(R.id.switch_btn_msg_voice));
        toolbar = ((Toolbar) view.findViewById(R.id.msg_setting_toolbar));
    }
}
