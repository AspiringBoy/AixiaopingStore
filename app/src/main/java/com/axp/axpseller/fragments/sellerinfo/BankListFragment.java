package com.axp.axpseller.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.activitys.sellerinfo.AddBankActivity;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.Bank;
import com.axp.axpseller.models.bean.BankBean;
import com.axp.axpseller.network.DataList;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.views.adapters.MyBankAdapter;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.ThemeManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/12/12.
 */
public class BankListFragment extends BaseFragment {

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.list)
    ListView list;
    BankBean bankBean = new BankBean();
    MyBankAdapter adapter;
    List<Bank> apply = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_bank_list, container, false);

        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    @OnClick(R.id.rl_add_bank_list)
    public void onClick() {
        AppUtils.toActivity(getActivity(), AddBankActivity.class);
    }

    private void init() {

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
/*        adapter = new MyBankAdapter(getActivity(),apply,BankListFragment.this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        list.setLayoutManager(manager);

        list.setAdapter(adapter);*/
        list.setAdapter(myAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setDefultBank(apply.get(i).getBankId());
            }
        });
    }

    private void reLoadData() {
        apply.clear();
        bankBean.setPageIndex(0);
        getBank();
    }

    @Override
    public void onResume() {
        super.onResume();
        reLoadData();
    }

    private void getBank() {
        Request<BankBean> request = new Request<>();
        bankBean.setPageIndex(bankBean.getPageIndex() + 1);
        request.setData(bankBean);
        RXUtils.request(getActivity(), request, "getBank", new SupportSubscriber<Response<DataList<Bank>>>() {

            @Override
            public void onNext(Response<DataList<Bank>> bankDataList) {
                  setAllData(bankDataList.getData().getDataList());
            }
        });

        /*    @Override
            public void onSuccess(Response<DataList<Bank>> dataListResponse) {
                adapter.addDataListNotifyDataSetChanged(dataListResponse.getData());
            }
        });*/

    }

    private void setAllData(List<Bank> data){
        apply.clear();
        for (int i = 0; i < data.size(); i++){
             Bank bank = new Bank();
             bank.setBankId(data.get(i).getBankId());
             bank.setIsDefault(data.get(i).getIsDefault());
             bank.setCardNo(data.get(i).getCardNo());
             bank.setBankName(data.get(i).getBankName());
             bank.setCounterFee(data.get(i).getCounterFee());
            apply.add(bank);
        }
       myAdapter.notifyDataSetChanged();
    }

    public void setDefultBank(String bankId) {
        Request<Bank> request = new Request<>();
        Bank bank = new Bank();
        bank.setBankId(bankId);
        request.setData(bank);
        RXUtils.request(getActivity(), request, "updateBankById", new SupportSubscriber<Response>() {
            @Override
            public void onNext(Response o) {
                reLoadData();
            }
        });

    }

    public void deletBank(String bankId) {
        Request<Bank> request = new Request<>();
        Bank bank = new Bank();
        bank.setBankId(bankId);
        request.setData(bank);
        RXUtils.request(getActivity(), request, "delBankById", new SupportSubscriber<Response>() {
            @Override
            public void onNext(Response o) {
                reLoadData();
            }
        });

    }

    public void show(String bankId) {
        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        SimpleDialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                super.onPositiveActionClicked(fragment);
                //删除
                deletBank(bankId);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };


        builder.message("是否删除银行卡~~")
                .positiveAction("确定").negativeAction("取消");

        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getActivity().getSupportFragmentManager(), null);
    }

    private BaseAdapter myAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return apply.size();
        }

        @Override
        public Object getItem(int position) {
            return apply.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_my_bank, null);
            }
            TextView name = (TextView) convertView.findViewById(R.id.tv_name);
            TextView free = (TextView) convertView.findViewById(R.id.tv_free);
            CheckBox select = (CheckBox) convertView.findViewById(R.id.cb_select);
            ImageView delet = (ImageView) convertView.findViewById(R.id.iv_delet);
            name.setText(apply.get(position).getBankName() + "(" + apply.get(position).getCardNo() + ")");
            free.setText("提现到" + apply.get(position).getBankName() + "，手续费" + apply.get(position).getCounterFee() + "%");
            select.setChecked(Boolean.parseBoolean(apply.get(position).getIsDefault()));
            select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                  //  setDefultBank(apply.get(position).getBankId());
                }
            });
            delet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    show(apply.get(position).getBankId());
                }
            });
            return convertView;
        }
    };



}
