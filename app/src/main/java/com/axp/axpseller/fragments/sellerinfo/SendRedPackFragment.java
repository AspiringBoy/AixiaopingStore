package com.axp.axpseller.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.RedPackget;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.L;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/12/10.
 */
public class SendRedPackFragment extends BaseFragment {
    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.edt_num)
    EditText edtNum;
    @BindView(R.id.edt_money)
    EditText edtMoney;
    @BindView(R.id.edt_message)
    EditText edtMessage;
    @BindView(R.id.tv_type_hint)
    TextView tvTypeHint;
    @BindView(R.id.rb_type)
    CheckBox rbType;
    String type = "10";
    @BindView(R.id.simple_money)
    TextView simpleMoney;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_send_redpack, container, false);

        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        rbType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == false) {
                    tvTypeHint.setText("最低面值0.08元，当前作为普通红包，");
                    rbType.setText("改为拼手气红包");
                    type = "50";
                    simpleMoney.setText("单个红包金额");
                } else {
                    tvTypeHint.setText("每人抽到的金额随机，");
                    rbType.setText("改为普通红包");
                    type = "10";
                    simpleMoney.setText("红包总金额");
                }
            }
        });
    }

    @OnClick(R.id.btn_logout)
    public void onClick() {

        if (!StringUtils.isBlank(edtNum.getText().toString()) && !StringUtils.isBlank(edtMoney.getText().toString())) {
            sendRedPackget();
        } else {
            T.showShort(getActivity(), "请填写完红包信息");
        }


    }

    private void sendRedPackget() {
        Request<RedPackget> request = new Request<>();
        RedPackget redPackget = new RedPackget();
        redPackget.setType(type);
        redPackget.setMun(edtNum.getText().toString());
        redPackget.setMoney(edtMoney.getText().toString());
        redPackget.setMessage(edtMessage.getText().toString());
        request.setData(redPackget);

        RXUtils.request(getActivity(), request, "sendRedpacket", new SupportSubscriber<Response>() {

            @Override
            public void onNext(Response response) {
                if (response.getStatus() == 1) {
                    T.showShort(getActivity(), "发送成功");
                    getActivity().finish();
                }
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);

                    T.showShort(getActivity(), response.getMessage());
            }

        });
    }
}
