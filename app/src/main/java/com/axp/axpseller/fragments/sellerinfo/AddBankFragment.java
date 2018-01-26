package com.axp.axpseller.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.Apply;
import com.axp.axpseller.models.Bank;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/12/12.
 */
public class AddBankFragment extends BaseFragment {
    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.edt_address)
    EditText edtAddress;
    @BindView(R.id.cb_select)
    CheckBox cbSelect;
    @BindView(R.id.tv_agree)
    TextView tvAgree;
    @BindView(R.id.edt_bankcode)
    EditText edtBankcode;
    @BindView(R.id.bankname)
    EditText bankname;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_add_bank, container, false);

        ButterKnife.bind(this, mView);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        getwithdrawls();
        return mView;
    }

    private void getwithdrawls(){
          RXUtils.request(getActivity(), new Request(), "getwithdrawals", new SupportSubscriber<Response<DataList<Apply>>>() {
              @Override
              public void onNext(Response<DataList<Apply>> o) {
                  if(o!=null) {
                      name.setText(o.getData().getDataList().get(0).getName());

                  }
              }
          });
    }

    @OnClick(R.id.btn_withdraw)
    public void onClick() {
        if (!StringUtils.isBlank(edtAddress.getText().toString()) && !StringUtils.isBlank(bankname.getText().toString()) && !StringUtils.isBlank(edtBankcode.getText().toString()))
        {
            if(cbSelect.isChecked()==true){
                addBank();
            }else{
                T.showShort(getActivity(),"请勾选协议");
            }
            }else {
            T.showShort(getActivity(),"请完善银行卡信息");
        }
    }

    private void addBank() {
        Request<Bank> request = new Request<>();
        Bank bank = new Bank();
        bank.setBankAddress(edtAddress.getText().toString());
        bank.setBankName(bankname.getText().toString());
        bank.setCardNo(edtBankcode.getText().toString());
        request.setData(bank);

        RXUtils.request(getActivity(), request, "commitBank", new SupportSubscriber<Response>() {
            @Override
            public void onNext(Response o) {
                getActivity().finish();
            }
        });
    }
}
