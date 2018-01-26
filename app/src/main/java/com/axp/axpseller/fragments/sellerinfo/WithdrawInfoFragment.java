package com.axp.axpseller.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.Apply;
import com.axp.axpseller.models.Withdraws;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.utils.T;
import com.gc.materialdesign.views.ButtonRectangle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/12/12.
 * 提现资料审核
 */
public class WithdrawInfoFragment extends BaseFragment {

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_code)
    EditText edtCode;
    Bundle bundle;
    String isChecked;
    @BindView(R.id.btn_withdraw)
    ButtonRectangle btnWithdraw;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_withdraw_info, container, false);

        ButterKnife.bind(this, mView);

        bundle = getArguments();
      /*  if(bundle!= null) {
            isChecked = bundle.getString("isChecked");
            if (isChecked.equals("0")) {
                btnWithdraw.setText("提交资料");
            } else {
                btnWithdraw.setText("修改资料");
            }
        }*/
        getwithdrawls();
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return mView;
    }

    @OnClick(R.id.btn_withdraw)
    public void onClick() {
        if (!StringUtils.isBlank(edtName.getText().toString()) && !StringUtils.isBlank(edtPhone.getText().toString()) && !StringUtils.isBlank(edtCode.getText().toString())) {
            apply();
        } else {
            T.showShort(getActivity(), "请完善您的资料");
        }

    }

    private void apply() {
        Request<Withdraws> request = new Request<>();
        Withdraws withdraws = new Withdraws();
        withdraws.setName(edtName.getText().toString());
        withdraws.setPhone(edtPhone.getText().toString());
        withdraws.setCode(edtCode.getText().toString());
        request.setData(withdraws);
        RXUtils.request(getActivity(), request, "apply", new SupportSubscriber<Response>() {
            @Override
            public void onNext(Response respone) {
                T.showShort(getActivity(), respone.getMessage());
                getActivity().finish();
            }
        });
    }
    private void getwithdrawls(){
        RXUtils.request(getActivity(), new Request(), "getwithdrawals", new SupportSubscriber<Response<DataList<Apply>>>() {
            @Override
            public void onNext(Response<DataList<Apply>> o) {
                if(o!=null) {
                    edtName.setText(o.getData().getDataList().get(0).getName());
                    edtCode.setText(o.getData().getDataList().get(0).getCode());
                    edtPhone.setText(o.getData().getDataList().get(0).getPhone());
                }
            }
        });
    }
}
