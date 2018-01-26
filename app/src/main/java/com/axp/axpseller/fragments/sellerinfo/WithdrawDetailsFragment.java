package com.axp.axpseller.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.activitys.sellerinfo.BankListActivity;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.Bank;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/12/12.
 * 提现详情
 */
public class WithdrawDetailsFragment extends BaseFragment {

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_shouxufei)
    TextView tvShouxufei;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.edt_money)
    EditText edtMoney;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private String bankId;
    private String miniMoney;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_withdraw_details, container, false);

        ButterKnife.bind(this, mView);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getWithdrawl();
    }

    @OnClick({R.id.ll_bank, R.id.ll_addbank, R.id.btn_withdraw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_bank:
                AppUtils.toActivity(getActivity(), BankListActivity.class);
                break;
            case R.id.ll_addbank:
                AppUtils.toActivity(getActivity(), BankListActivity.class);
                break;
            case R.id.btn_withdraw:
                if(!StringUtils.isBlank(bankId)&&!StringUtils.isBlank(edtMoney.getText().toString())){
                    if(Double.parseDouble(edtMoney.getText().toString())>=Double.parseDouble(miniMoney)) {
                        withdrawalApply();
                    }else {
                        T.showShort(getActivity(),"提现金额最低"+miniMoney+"元");
                    }
                }else{
                    T.showShort(getActivity(),"请完善您的提现信息");
                }

                break;
        }
    }

    private void getWithdrawl(){
      RXUtils.request(getActivity(), new Request(), "WithdrawApplyInfo", new SupportSubscriber<Response<DataList<Bank>>>() {
          @Override
          public void onNext(Response<DataList<Bank>> o) {

              if (o.getData() != null) {
                  tvTime.setText(o.getData().getMessage());
                  money.setText(o.getData().getTotalMoneys());
                  miniMoney = o.getData().getMinMoney();
                  if (o != null && o.getData().getDataList().size() > 0) {
                      bankId = o.getData().getDataList().get(0).getBankId();
                      tvBankName.setText(o.getData().getDataList().get(0).getBankName() + "(" + o.getData().getDataList().get(0).getCardNo() + ")");
                      tvShouxufei.setText("提现到" + o.getData().getDataList().get(0).getBankName() + "，手续费" + o.getData().getDataList().get(0).getCounterFee() + "%");
                  }else{
                      tvBankName.setText("请选择银行卡");
                  }
              }
          }
      });
    }

    private void withdrawalApply(){
        Request<Bank> request = new Request<>();
        Bank bank = new Bank();
        bank.setBankId(bankId);
        bank.setMoney(edtMoney.getText().toString());
        request.setData(bank);
        RXUtils.request(getActivity(), request, "withdrawalApply", new SupportSubscriber<Response>() {
            @Override
            public void onNext(Response o) {
                T.showShort(getActivity(),o.getMessage());
                getActivity().finish();
            }
        });
    }
}
